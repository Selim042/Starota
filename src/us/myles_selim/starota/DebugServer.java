package us.myles_selim.starota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.enums.EnumPatreonPerm;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.wrappers.StarotaServer;

// TODO: Optimize this
public class DebugServer extends Thread {

	public static final long DEBUG_SERVER_ID = 517546213520965662L;

	public static final Permissions[] USED_PERMISSIONS = new Permissions[] { Permissions.SEND_MESSAGES,
			Permissions.READ_MESSAGES, Permissions.MANAGE_ROLES, Permissions.MANAGE_MESSAGES,
			Permissions.USE_EXTERNAL_EMOJIS, Permissions.ADD_REACTIONS };

	private static final IGuild DEBUG_SERVER;
	private static final IMessage STATUS;
	private static final IMessage DISCORD_PERMS;
	private static final IMessage PATREON_PERMS;
	private static final IMessage MODULES;

	private static final EmbedObject NOT_READY_EMBED;

	static {
		DEBUG_SERVER = Starota.getGuild(DEBUG_SERVER_ID);
		if (Starota.IS_DEV) {
			IChannel ch = DEBUG_SERVER.getChannelByID(517546371503357972L);
			if (ch.getFullMessageHistory().isEmpty())
				STATUS = ch.sendMessage(new EmbedObject());
			else
				STATUS = ch.getFullMessageHistory().get(0);

			ch = DEBUG_SERVER.getChannelByID(522806654434017282L);
			if (ch.getFullMessageHistory().isEmpty())
				DISCORD_PERMS = ch.sendMessage(new EmbedObject());
			else
				DISCORD_PERMS = ch.getFullMessageHistory().get(0);

			ch = DEBUG_SERVER.getChannelByID(517548301932036096L);
			if (ch.getFullMessageHistory().isEmpty())
				PATREON_PERMS = ch.sendMessage(new EmbedObject());
			else
				PATREON_PERMS = ch.getFullMessageHistory().get(0);

			ch = DEBUG_SERVER.getChannelByID(518285102703181847L);
			if (ch.getFullMessageHistory().isEmpty())
				MODULES = ch.sendMessage(new EmbedObject());
			else
				MODULES = ch.getFullMessageHistory().get(0);
		} else {
			IChannel ch = DEBUG_SERVER.getChannelByID(517548271435120643L);
			if (ch.getFullMessageHistory().isEmpty())
				STATUS = ch.sendMessage(new EmbedObject());
			else
				STATUS = ch.getFullMessageHistory().get(0);

			ch = DEBUG_SERVER.getChannelByID(522806624260194304L);
			if (ch.getFullMessageHistory().isEmpty())
				DISCORD_PERMS = ch.sendMessage(new EmbedObject());
			else
				DISCORD_PERMS = ch.getFullMessageHistory().get(0);

			ch = DEBUG_SERVER.getChannelByID(517546321595334656L);
			if (ch.getFullMessageHistory().isEmpty())
				PATREON_PERMS = ch.sendMessage(new EmbedObject());
			else
				PATREON_PERMS = ch.getFullMessageHistory().get(0);

			ch = DEBUG_SERVER.getChannelByID(518285130108764181L);
			if (ch.getFullMessageHistory().isEmpty())
				MODULES = ch.sendMessage(new EmbedObject());
			else
				MODULES = ch.getFullMessageHistory().get(0);
		}

		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(Starota.BOT_NAME + " is still starting...");
		builder.withColor(0xF0, 0x00, 0x00);
		NOT_READY_EMBED = builder.build();
	}

	@Override
	public void run() {
		while (true) {
			update();
			try {
				Thread.sleep(300000); // 5 mins
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void update() {
		if (!Starota.getClient().isReady())
			return;
		RequestBuffer
				.request(() -> STATUS.edit(Starota.FULLY_STARTED ? getStatusEmbed() : NOT_READY_EMBED));
		RequestBuffer.request(() -> DISCORD_PERMS
				.edit(Starota.FULLY_STARTED ? getDiscordPermsEmbed() : NOT_READY_EMBED));
		RequestBuffer.request(() -> PATREON_PERMS
				.edit(Starota.FULLY_STARTED ? getPatreonPermsEmbed() : NOT_READY_EMBED));
		RequestBuffer.request(
				() -> MODULES.edit(Starota.FULLY_STARTED ? getModulesEmbed() : NOT_READY_EMBED));
	}

	private static EmbedObject getStatusEmbed() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Status:");
		for (IGuild g : Starota.getClient().getGuilds()) {
			if (EmojiServerHelper.isEmojiServer(g))
				continue;
			StarotaServer server = StarotaServer.getServer(g);
			if (g.equals(DEBUG_SERVER))
				continue;
			String text = "";
			text += "Owner: " + g.getOwner().getName() + "#" + g.getOwner().getDiscriminator() + "\n";
			text += "Users: " + g.getUsers().size() + "\n";
			int tradeId = server.hasKey(StarotaServer.TRADE_ID_KEY)
					? (int) server.getValue(StarotaServer.TRADE_ID_KEY)
					: 0;
			text += "Trades: " + tradeId + "/9,999\n";
			text += "Leaderboards: " + server.getLeaderboardCount() + "/" + Starota.getMaxLeaderboards(g)
					+ "\n";
			builder.appendField(g.getName(), text, true);
		}
		builder.withFooterText("Last updated");
		builder.withTimestamp(System.currentTimeMillis());
		return builder.build();
	}

	private static EmbedObject getDiscordPermsEmbed() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Missing Discord Perms:");
		IUser ourUser = Starota.getOurUser();
		for (IGuild g : Starota.getClient().getGuilds()) {
			if (EmojiServerHelper.isEmojiServer(g))
				continue;
			if (g.equals(DEBUG_SERVER))
				continue;
			String text = "";
			List<Permissions> invertPerms = new ArrayList<>(Arrays.asList(USED_PERMISSIONS));
			invertPerms.removeAll(ourUser.getPermissionsForGuild(g));
			for (Permissions p : invertPerms)
				text += " - " + p + "\n";
			builder.appendField(g.getName(), text.isEmpty() ? "All permissions given" : text, true);
		}
		builder.withFooterText("Last updated");
		builder.withTimestamp(System.currentTimeMillis());
		return builder.build();
	}

	private static EmbedObject getPatreonPermsEmbed() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Patreon Perms:");
		for (IGuild g : Starota.getClient().getGuilds()) {
			if (EmojiServerHelper.isEmojiServer(g))
				continue;
			if (g.equals(DEBUG_SERVER))
				continue;
			List<EnumPatreonPerm> perms = Starota.getPatreonPerms(g);
			if (perms == null || perms.isEmpty()) {
				builder.appendField(g.getName(), " - No Patreon permissions", true);
				continue;
			}
			String text = "";
			for (EnumPatreonPerm p : perms)
				text += " - " + p + "\n";
			builder.appendField(g.getName(), text, true);
		}
		builder.withFooterText("Last updated");
		builder.withTimestamp(System.currentTimeMillis());
		return builder.build();
	}

	private static EmbedObject getModulesEmbed() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle("Enabled Modules:");
		for (IGuild g : Starota.getClient().getGuilds()) {
			if (EmojiServerHelper.isEmojiServer(g))
				continue;
			StarotaServer server = StarotaServer.getServer(g);
			if (g.equals(DEBUG_SERVER))
				continue;
			List<StarotaModule> modules = StarotaModule.getEnabledModules(server);
			if (modules == null || modules.isEmpty()) {
				builder.appendField(g.getName(), " - No enabled modules", true);
				continue;
			}
			String text = "";
			for (StarotaModule m : modules)
				text += " - " + m.getName() + "\n";
			builder.appendField(g.getName(), text, true);
		}
		builder.withFooterText("Last updated");
		builder.withTimestamp(System.currentTimeMillis());
		return builder.build();
	}

}
