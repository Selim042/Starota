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
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.message.MessageDeleteEvent;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import discord4j.core.event.domain.message.ReactionAddEvent;
import discord4j.core.event.domain.message.ReactionRemoveEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandCredits;
import us.myles_selim.starota.commands.CommandInvite;
import us.myles_selim.starota.commands.CommandSupportBot;
import us.myles_selim.starota.commands.CommandVote;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.pokedex.CommandPokedex;
import us.myles_selim.starota.reaction_messages.ReactionMessageRegistry;

public class PokedexBot {

	public static DiscordClient POKEDEX_CLIENT;
	public static PrimaryCommandHandler COMMAND_HANDLER;
	public static ReactionMessageRegistry REACTION_MESSAGES_REGISTRY;

	public static final String BOT_NAME = "Pokedex";
	public static final PermissionSet USED_PERMISSIONS = PermissionSet.of(Permission.SEND_MESSAGES,
			Permission.VIEW_CHANNEL, Permission.MANAGE_MESSAGES, Permission.USE_EXTERNAL_EMOJIS,
			Permission.ADD_REACTIONS);

	private static Properties PROPERTIES = new Properties();
	private static DiscordBotListAPI BOT_LIST;
	private static boolean started = false;

	public static void start() {
		if (started || Starota.IS_DEV)
			return;
		started = true;

		DiscordClientBuilder builder = new DiscordClientBuilder(PROPERTIES.getProperty("pokedex_bot"));
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		POKEDEX_CLIENT = builder.build();
		POKEDEX_CLIENT.login();
		try {
			while (!POKEDEX_CLIENT.isConnected())
				Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		COMMAND_HANDLER = new PrimaryCommandHandler(POKEDEX_CLIENT, (ch) -> {
			if (!(ch instanceof TextChannel))
				return false;
			if (ch instanceof PrivateChannel
					|| ((TextChannel) ch).getEffectivePermissions(StarotaConstants.STAROTA_ID).block()
							.contains(Permission.VIEW_CHANNEL)
					|| ((TextChannel) ch).getEffectivePermissions(StarotaConstants.STAROTA_DEV_ID)
							.block().contains(Permission.VIEW_CHANNEL))
				return false;
			return true;
		});
		REACTION_MESSAGES_REGISTRY = new ReactionMessageRegistry(POKEDEX_CLIENT);
		Thread statusUpdater = new Thread("PokedexStatusUpdater") {

			@Override
			public void run() {
				while (true) {
					POKEDEX_CLIENT.updatePresence(Presence.online(Activity.playing("v"
							+ StarotaConstants.VERSION + (Starota.DEBUG || Starota.IS_DEV ? "d" : ""))));
					try {
						Thread.sleep(3600000); // 1 hour
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		statusUpdater.start();

		JavaCommandHandler jCmdHandler = new JavaCommandHandler();
		COMMAND_HANDLER.registerCommandHandler(jCmdHandler);
		jCmdHandler.registerDefaultCommands();

		jCmdHandler.registerCommand(new CommandChangelog());
		jCmdHandler.registerCommand(new CommandCredits());
		jCmdHandler.registerCommand(
				new CommandSupportBot(BOT_NAME, POKEDEX_CLIENT.getSelf().block().getId()));
		jCmdHandler.registerCommand(new CommandInvite(BOT_NAME, POKEDEX_CLIENT.getSelf().block().getId(),
				MiscUtils.generatePermissionNumber(USED_PERMISSIONS)));
		jCmdHandler.registerCommand(new CommandVote(Starota.BOT_NAME, StarotaConstants.STAROTA_ID));

		// TODO: add this ability back in for Pokedex
		// jCmdHandler.registerCommand("Administrative", new
		// CommandChangelogChannel());

		jCmdHandler.registerCommand("Pokedex", new CommandPokedex());

		EventDispatcher dispatcher = POKEDEX_CLIENT.getEventDispatcher();
		dispatcher.on(COMMAND_HANDLER.getEventType()).subscribe(COMMAND_HANDLER::execute);

		dispatcher.on(ReactionAddEvent.class).subscribe(REACTION_MESSAGES_REGISTRY::onReactAdd);
		dispatcher.on(ReactionRemoveEvent.class).subscribe(REACTION_MESSAGES_REGISTRY::onReactRemove);
		dispatcher.on(MessageDeleteEvent.class).subscribe(REACTION_MESSAGES_REGISTRY::onDelete);
		dispatcher.on(MessageUpdateEvent.class).subscribe(REACTION_MESSAGES_REGISTRY::onEdit);

		dispatcher.on(GuildCreateEvent.class).subscribe(PokedexEventHandler::onGuildCreate);
	}

	public static User getSelf() {
		if (!started)
			return null;
		return POKEDEX_CLIENT.getSelf().block();
	}

	public static String getOurName(Guild guild) {
		if (POKEDEX_CLIENT == null)
			return null;
		if (guild == null)
			return getSelf().getUsername();
		return getSelf().asMember(guild.getId()).block().getDisplayName();
	}

	public static DiscordBotListAPI getBotListAPI() {
		if (BOT_LIST == null && PROPERTIES.containsKey("dex_bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder()
					.token(PROPERTIES.getProperty("dex_bot_list_token"))
					.botId(POKEDEX_CLIENT.getSelf().block().getId().asString()).build();
		return BOT_LIST;
	}

	public static void updateOwners() {
		if (Starota.IS_DEV)
			return;
		Role ownerRole = POKEDEX_CLIENT
				.getRoleById(StarotaConstants.SUPPORT_SERVER, Snowflake.of(567718302491607050L)).block();
		List<User> currentOwners = new ArrayList<>();
		for (Guild g : POKEDEX_CLIENT.getGuilds().collectList().block()) {
			Member owner = POKEDEX_CLIENT.getGuildById(StarotaConstants.SUPPORT_SERVER).block()
					.getMemberById(g.getOwnerId()).block();
			if (owner == null)
				continue;
			if (!owner.getRoles().any((r) -> r.equals(ownerRole)).block())
				owner.addRole(ownerRole.getId());
			currentOwners.add(owner);
		}
		for (Member u : POKEDEX_CLIENT.getGuildById(StarotaConstants.SUPPORT_SERVER).block().getMembers()
				.filter((m) -> m.getRoles().any((r) -> r.equals(ownerRole)).block()).collectList()
				.block())
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole.getId());
	}

}
