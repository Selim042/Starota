package us.myles_selim.starota.debug_server;

import java.util.EnumSet;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;

// TODO: Optimize this
public class DebugServer extends Thread {

	public static final long DEBUG_SERVER_ID = 517546213520965662L;
	public static final IGuild DEBUG_SERVER;

	private static EmbedObject NOT_READY_EMBED;
	private static final EnumSet<Permissions> USED_PERMISSIONS = EnumSet.noneOf(Permissions.class);

	// private static final IMessage STATUS;
	// private static final IMessage DISCORD_PERMS;
	// private static final IMessage PATREON_PERMS;
	// private static final IMessage MODULES;

	static {
		DEBUG_SERVER = Starota.getGuild(DEBUG_SERVER_ID);
		IChannel ch;

		if (Starota.IS_DEV)
			ch = DEBUG_SERVER.getChannelByID(517546371503357972L);
		else
			ch = DEBUG_SERVER.getChannelByID(517548271435120643L);
		if (ch.getFullMessageHistory().isEmpty())
			new StatusReactionMessage().sendMessage(ch);

		if (Starota.IS_DEV)
			ch = DEBUG_SERVER.getChannelByID(522806654434017282L);
		else
			ch = DEBUG_SERVER.getChannelByID(522806624260194304L);
		if (ch.getFullMessageHistory().isEmpty())
			new DiscordPermsReactionMessage().sendMessage(ch);

		if (Starota.IS_DEV)
			ch = DEBUG_SERVER.getChannelByID(517548301932036096L);
		else
			ch = DEBUG_SERVER.getChannelByID(517546321595334656L);
		if (ch.getFullMessageHistory().isEmpty())
			new DonorPermsReactionMessage().sendMessage(ch);

		if (Starota.IS_DEV)
			ch = DEBUG_SERVER.getChannelByID(518285102703181847L);
		else
			ch = DEBUG_SERVER.getChannelByID(518285130108764181L);
		if (ch.getFullMessageHistory().isEmpty())
			new ModulesReactionMessage().sendMessage(ch);
	}

	public static void addPermission(Permissions perm) {
		USED_PERMISSIONS.add(perm);
	}

	public static EnumSet<Permissions> getUsedPermissions() {
		return USED_PERMISSIONS.clone();
	}

	public static EmbedObject getNotReadyEmbed() {
		if (NOT_READY_EMBED != null)
			return NOT_READY_EMBED;
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(Starota.BOT_NAME + " is still starting...");
		builder.withColor(0xF0, 0x00, 0x00);
		NOT_READY_EMBED = builder.build();
		return NOT_READY_EMBED;
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
		// RequestBuffer
		// .request(() -> STATUS.edit(Starota.FULLY_STARTED ? getStatusEmbed() :
		// NOT_READY_EMBED));
		// RequestBuffer.request(() -> DISCORD_PERMS
		// .edit(Starota.FULLY_STARTED ? getDiscordPermsEmbed() :
		// NOT_READY_EMBED));
		// RequestBuffer.request(() -> PATREON_PERMS
		// .edit(Starota.FULLY_STARTED ? getPatreonPermsEmbed() :
		// NOT_READY_EMBED));
		// RequestBuffer.request(
		// () -> MODULES.edit(Starota.FULLY_STARTED ? getModulesEmbed() :
		// NOT_READY_EMBED));
	}

	// private static EmbedObject getStatusEmbed() {
	// EmbedBuilder builder = new EmbedBuilder();
	// builder.withTitle("Status:");
	// for (IGuild g : Starota.getClient().getGuilds()) {
	// if (EmojiServerHelper.isEmojiServer(g))
	// continue;
	// if (g.equals(DEBUG_SERVER))
	// continue;
	// StarotaServer server = StarotaServer.getServer(g);
	// String text = "";
	// text += "Owner: " + g.getOwner().getName() + "#" +
	// g.getOwner().getDiscriminator() + "\n";
	// text += "Users: " + g.getUsers().size() + "\n";
	// int tradeId = server.hasKey(StarotaServer.TRADE_ID_KEY)
	// ? (int) server.getValue(StarotaServer.TRADE_ID_KEY)
	// : 0;
	// text += "Trades: " + tradeId + "/9,999\n";
	// text += "Leaderboards: " + server.getLeaderboardCount() + "/" +
	// Starota.getMaxLeaderboards(g)
	// + "\n";
	// text += "Voter Ratio: " + server.getVoterPercent() + "\n";
	// builder.appendField(g.getName(), text, true);
	// }
	// builder.withFooterText("Last updated");
	// builder.withTimestamp(System.currentTimeMillis());
	// return builder.build();
	// }
	//
	// private static EmbedObject getDiscordPermsEmbed() {
	// EmbedBuilder builder = new EmbedBuilder();
	// builder.withTitle("Missing Discord Perms:");
	// IUser ourUser = Starota.getOurUser();
	// for (IGuild g : Starota.getClient().getGuilds()) {
	// if (EmojiServerHelper.isEmojiServer(g))
	// continue;
	// if (g.equals(DEBUG_SERVER))
	// continue;
	// String text = "";
	// EnumSet<Permissions> invertPerms = EnumSet.allOf(Permissions.class);
	// invertPerms.removeAll(USED_PERMISSIONS);
	// for (Permissions p : invertPerms)
	// text += " - " + p + "\n";
	// builder.appendField(g.getName(), text.isEmpty() ? "All permissions given"
	// : text, true);
	// }
	// builder.withFooterText("Last updated");
	// builder.withTimestamp(System.currentTimeMillis());
	// return builder.build();
	// }
	//
	// private static EmbedObject getPatreonPermsEmbed() {
	// EmbedBuilder builder = new EmbedBuilder();
	// builder.withTitle("Patreon Perms:");
	// for (IGuild g : Starota.getClient().getGuilds()) {
	// if (EmojiServerHelper.isEmojiServer(g))
	// continue;
	// if (g.equals(DEBUG_SERVER))
	// continue;
	// List<EnumDonorPerm> perms = Starota.getDonorPerms(g);
	// if (perms == null || perms.isEmpty()) {
	// builder.appendField(g.getName(), " - No Patreon permissions", true);
	// continue;
	// }
	// String text = "";
	// for (EnumDonorPerm p : perms)
	// text += " - " + p + "\n";
	// builder.appendField(g.getName(), text, true);
	// }
	// builder.withFooterText("Last updated");
	// builder.withTimestamp(System.currentTimeMillis());
	// return builder.build();
	// }
	//
	// private static EmbedObject getModulesEmbed() {
	// EmbedBuilder builder = new EmbedBuilder();
	// builder.withTitle("Enabled Modules:");
	// for (IGuild g : Starota.getClient().getGuilds()) {
	// if (EmojiServerHelper.isEmojiServer(g))
	// continue;
	// if (g.equals(DEBUG_SERVER))
	// continue;
	// StarotaServer server = StarotaServer.getServer(g);
	// List<StarotaModule> modules = StarotaModule.getEnabledModules(server);
	// if (modules == null || modules.isEmpty()) {
	// builder.appendField(g.getName(), " - No enabled modules", true);
	// continue;
	// }
	// String text = "";
	// for (StarotaModule m : modules)
	// text += " - " + m.getName() + "\n";
	// builder.appendField(g.getName(), text, true);
	// }
	// builder.withFooterText("Last updated");
	// builder.withTimestamp(System.currentTimeMillis());
	// return builder.build();
	// }

}
