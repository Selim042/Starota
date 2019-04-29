package us.myles_selim.starota.assistants.pokedex;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

import org.discordbots.api.client.DiscordBotListAPI;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.CommandChangelog;
import us.myles_selim.starota.commands.CommandChangelogChannel;
import us.myles_selim.starota.commands.CommandCredits;
import us.myles_selim.starota.commands.CommandInvite;
import us.myles_selim.starota.commands.CommandSupportBot;
import us.myles_selim.starota.commands.CommandVote;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.misc.utils.StarotaConstants;
import us.myles_selim.starota.pokedex.CommandPokedex;
import us.myles_selim.starota.reaction_messages.ReactionMessageRegistry;

public class PokedexBot {

	public static IDiscordClient POKEDEX_CLIENT;
	public static PrimaryCommandHandler COMMAND_HANDLER;
	public static ReactionMessageRegistry REACTION_MESSAGES_REGISTRY;

	public static final String BOT_NAME = "Pokedex";
	public static final EnumSet<Permissions> USED_PERMISSIONS = EnumSet.of(Permissions.SEND_MESSAGES,
			Permissions.READ_MESSAGES, Permissions.MANAGE_MESSAGES, Permissions.USE_EXTERNAL_EMOJIS,
			Permissions.ADD_REACTIONS);

	private static Properties PROPERTIES = new Properties();
	private static DiscordBotListAPI BOT_LIST;
	private static boolean started = false;

	public static void start() {
		if (started || Starota.IS_DEV)
			return;
		started = true;

		ClientBuilder builder = new ClientBuilder();
		try {
			PROPERTIES.load(new FileInputStream("starota.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		builder.withToken(PROPERTIES.getProperty("pokedex_bot"));
		try {
			POKEDEX_CLIENT = builder.login();
		} catch (DiscordException e) {
			e.printStackTrace();
			System.err.println("failed to start Pokedex bot");
			return;
		}
		try {
			while (!POKEDEX_CLIENT.isReady())
				Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		COMMAND_HANDLER = new PrimaryCommandHandler(POKEDEX_CLIENT, (IChannel ch) -> {
			IUser starota = Starota.getUser(StarotaConstants.STAROTA_ID);
			IUser starotaDev = Starota.getUser(StarotaConstants.STAROTA_DEV_ID);
			List<IUser> users = ch.getUsersHere();
			if (ch instanceof IPrivateChannel || users.contains(starota) || users.contains(starotaDev))
				return false;
			return true;
		});
		REACTION_MESSAGES_REGISTRY = new ReactionMessageRegistry(POKEDEX_CLIENT);
		Thread statusUpdater = new Thread("PokedexStatusUpdater") {

			@Override
			public void run() {
				while (true) {
					POKEDEX_CLIENT.changePresence(StatusType.ONLINE, ActivityType.PLAYING, "v"
							+ StarotaConstants.VERSION + (Starota.DEBUG || Starota.IS_DEV ? "d" : ""));
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
				new CommandSupportBot(BOT_NAME, POKEDEX_CLIENT.getOurUser().getLongID()));
		jCmdHandler.registerCommand(new CommandInvite(BOT_NAME, POKEDEX_CLIENT.getOurUser().getLongID(),
				Permissions.generatePermissionsNumber(USED_PERMISSIONS)));
		jCmdHandler.registerCommand(new CommandVote(Starota.BOT_NAME, StarotaConstants.STAROTA_ID));

		jCmdHandler.registerCommand("Administrative", new CommandChangelogChannel());

		jCmdHandler.registerCommand(new CommandPokedex(REACTION_MESSAGES_REGISTRY));

		EventDispatcher dispatcher = POKEDEX_CLIENT.getDispatcher();
		dispatcher.registerListener(COMMAND_HANDLER);
		dispatcher.registerListener(REACTION_MESSAGES_REGISTRY);
		dispatcher.registerListener(new PokedexEventHandler());
	}

	public static IUser getOurUser() {
		if (!started)
			return null;
		return POKEDEX_CLIENT.getOurUser();
	}

	public static String getOurName(IGuild guild) {
		if (POKEDEX_CLIENT == null)
			return null;
		if (guild == null)
			return getOurUser().getName();
		return getOurUser().getDisplayName(guild);
	}

	public static DiscordBotListAPI getBotListAPI() {
		if (BOT_LIST == null && PROPERTIES.containsKey("dex_bot_list_token"))
			BOT_LIST = new DiscordBotListAPI.Builder()
					.token(PROPERTIES.getProperty("dex_bot_list_token"))
					.botId(POKEDEX_CLIENT.getOurUser().getStringID()).build();
		return BOT_LIST;
	}

	public static void updateOwners() {
		if (Starota.IS_DEV)
			return;
		IRole ownerRole = POKEDEX_CLIENT.getRoleByID(567718302491607050L);
		List<IUser> currentOwners = new ArrayList<>();
		for (IGuild g : POKEDEX_CLIENT.getGuilds()) {
			IUser owner = POKEDEX_CLIENT.getGuildByID(StarotaConstants.SUPPORT_SERVER)
					.getUserByID(g.getOwnerLongID());
			if (owner == null)
				continue;
			if (!owner.hasRole(ownerRole))
				RequestBuffer.request(() -> owner.addRole(ownerRole));
			currentOwners.add(owner);
		}
		for (IUser u : POKEDEX_CLIENT.getGuildByID(StarotaConstants.SUPPORT_SERVER)
				.getUsersByRole(ownerRole))
			if (!currentOwners.contains(u))
				u.removeRole(ownerRole);
	}

}
