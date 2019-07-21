package us.myles_selim.starota.assistants.pokedex;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.discordbots.api.client.DiscordBotListAPI;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.EventDispatcher;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.assistants.CommandBots;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandCredits;
import us.myles_selim.starota.commands.CommandInvite;
import us.myles_selim.starota.commands.CommandSupportBot;
import us.myles_selim.starota.commands.CommandVote;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.pokedex.CommandPokedex;
import us.myles_selim.starota.reaction_messages.ReactionMessageRegistry;
import us.myles_selim.starota.wrappers.StarotaServer;

public class PokedexBot {

	public static DiscordClient CLIENT;
	public static PrimaryCommandHandler COMMAND_HANDLER;
	public static ReactionMessageRegistry REACTION_MESSAGES_REGISTRY;

	public static final String BOT_NAME = "Pokedex";
	public static final PermissionSet USED_PERMISSIONS = PermissionSet.of(Permission.SEND_MESSAGES,
			Permission.VIEW_CHANNEL, Permission.MANAGE_MESSAGES, Permission.USE_EXTERNAL_EMOJIS,
			Permission.ADD_REACTIONS);

	private static Properties PROPERTIES = new Properties();
	private static DiscordBotListAPI BOT_LIST;
	private static boolean started = false;

	@SuppressWarnings("deprecation")
	public static void start() {
		if (started || Starota.IS_DEV)
			return;
		started = true;

		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		DiscordClientBuilder builder = new DiscordClientBuilder(PROPERTIES.getProperty("pokedex_bot"));
		CLIENT = builder.build();
		Thread botRun = new Thread() {

			@Override
			public void run() {
				CLIENT.login().block();
			}
		};
		botRun.start();

		BotServer.registerServerType(CLIENT, StarotaServer.class);
		COMMAND_HANDLER = new PrimaryCommandHandler(CLIENT, (Channel ch) -> {
			if (!(ch instanceof TextChannel))
				return false;
			User starota = CLIENT.getUserById(StarotaConstants.STAROTA_ID).block();
			User starotaDev = CLIENT.getUserById(StarotaConstants.STAROTA_DEV_ID).block();
			List<Member> users = MiscUtils.getMembersHere((GuildChannel) ch);
			if (users.contains(starota) || users.contains(starotaDev))
				return false;
			return true;
		});
		REACTION_MESSAGES_REGISTRY = new ReactionMessageRegistry(CLIENT);
		Thread statusUpdater = new Thread("PokedexStatusUpdater") {

			@Override
			public void run() {
				while (true) {
					CLIENT.updatePresence(Presence.online(Activity.playing("v" + StarotaConstants.VERSION
							+ (Starota.DEBUG || Starota.IS_DEV ? "d" : ""))));
					try {
						Thread.sleep(3600000); // 1 hour
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		statusUpdater.start();

		JavaCommandHandler jCmdHandler = new JavaCommandHandler(CLIENT);
		COMMAND_HANDLER.registerCommandHandler(jCmdHandler);
		jCmdHandler.registerDefaultCommands();

		Snowflake ourId = getOurUser().getId();
		jCmdHandler.registerCommand(new CommandChangelog());
		jCmdHandler.registerCommand(new CommandCredits());
		jCmdHandler.registerCommand(new CommandSupportBot(BOT_NAME, ourId));
		jCmdHandler.registerCommand(new CommandInvite(BOT_NAME, ourId,
				MiscUtils.generatePermissionNumber(USED_PERMISSIONS)));
		jCmdHandler.registerCommand(new CommandVote(BOT_NAME, ourId));

		// TODO: add this ability back in for Pokedex
		// jCmdHandler.registerCommand("Administrative", new
		// CommandChangelogChannel());

		jCmdHandler.registerCommand("Pokedex", new CommandPokedex());

		jCmdHandler.registerCommand("Misc", new CommandBots());

		EventDispatcher dispatcher = CLIENT.getEventDispatcher();
		COMMAND_HANDLER.setup(dispatcher);
		REACTION_MESSAGES_REGISTRY.setup(dispatcher);
		new PokedexEventHandler().setup(dispatcher);
	}

	public static User getOurUser() {
		if (!started)
			return null;
		return CLIENT.getSelf().block();
	}

	public static String getOurName(Guild guild) {
		if (CLIENT == null)
			return null;
		if (guild == null)
			return getOurUser().getUsername();
		return getOurUser().asMember(guild.getId()).block().getDisplayName();
	}

	public static DiscordBotListAPI getBotListAPI() {
		if (BOT_LIST == null && PROPERTIES.containsKey("dex_bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder()
					.token(PROPERTIES.getProperty("dex_bot_list_token"))
					.botId(CLIENT.getSelfId().get().asString()).build();
		return BOT_LIST;
	}

	public static void updateOwners() {
		if (Starota.IS_DEV)
			return;
		// pokedex owner role
		Snowflake ownerRole = Snowflake.of(567718302491607050L);
		Guild supportServer = CLIENT.getGuildById(StarotaConstants.SUPPORT_SERVER).block();
		List<User> currentOwners = new ArrayList<>();
		for (Guild g : CLIENT.getGuilds().collectList().block()) {
			Member owner = supportServer.getMemberById(g.getOwnerId()).block();
			if (owner == null)
				continue;
			if (!MiscUtils.hasRole(owner, ownerRole))
				owner.addRole(ownerRole);
			currentOwners.add(owner);
		}
		for (Member u : MiscUtils.getMembersByRole(
				CLIENT.getGuildById(StarotaConstants.SUPPORT_SERVER).block(), ownerRole))
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole);
	}

}
