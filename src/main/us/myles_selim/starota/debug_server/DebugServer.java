package us.myles_selim.starota.debug_server;

import java.util.EnumSet;
import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.EmbedBuilder;

// TODO: Optimize this
public class DebugServer /* extends Thread */ {

	public static final long DEBUG_SERVER_ID = 517546213520965662L;

	private static Guild DEBUG_SERVER;
	private static Consumer<? super EmbedCreateSpec> NOT_READY_EMBED;
	private static final EnumSet<Permission> USED_PERMISSIONS = EnumSet.noneOf(Permission.class);

	// private static final Message STATUS;
	// private static final Message DISCORD_PERMS;
	// private static final Message PATREON_PERMS;
	// private static final Message MODULES;

	public static void init() {
		DEBUG_SERVER = Starota.getGuild(DEBUG_SERVER_ID);
		TextChannel ch;

		if (Starota.IS_DEV)
			ch = (TextChannel) DEBUG_SERVER.getChannelById(Snowflake.of(517546371503357972L)).block();
		else
			ch = (TextChannel) DEBUG_SERVER.getChannelById(Snowflake.of(517548271435120643L)).block();
		if (ch.getMessagesBefore(ch.getLastMessageId().get()).collectList().block().isEmpty())
			new StatusReactionMessage().createMessage(ch);

		if (Starota.IS_DEV)
			ch = (TextChannel) DEBUG_SERVER.getChannelById(Snowflake.of(522806654434017282L)).block();
		else
			ch = (TextChannel) DEBUG_SERVER.getChannelById(Snowflake.of(522806624260194304L)).block();
		if (ch.getMessagesBefore(ch.getLastMessageId().get()).collectList().block().isEmpty())
			new DiscordPermsReactionMessage().createMessage(ch);

		if (Starota.IS_DEV)
			ch = (TextChannel) DEBUG_SERVER.getChannelById(Snowflake.of(517548301932036096L)).block();
		else
			ch = (TextChannel) DEBUG_SERVER.getChannelById(Snowflake.of(517546321595334656L)).block();
		if (ch.getMessagesBefore(ch.getLastMessageId().get()).collectList().block().isEmpty())
			new DonorPermsReactionMessage().createMessage(ch);

		if (Starota.IS_DEV)
			ch = (TextChannel) DEBUG_SERVER.getChannelById(Snowflake.of(518285102703181847L)).block();
		else
			ch = (TextChannel) DEBUG_SERVER.getChannelById(Snowflake.of(518285130108764181L)).block();
		if (ch.getMessagesBefore(ch.getLastMessageId().get()).collectList().block().isEmpty())
			new ModulesReactionMessage().createMessage(ch);
	}

	public static void addPermission(Permission perm) {
		USED_PERMISSIONS.add(perm);
	}

	public static PermissionSet getUsedPermission() {
		return PermissionSet.of(USED_PERMISSIONS.toArray(new Permission[0]));
	}

	public static Consumer<? super EmbedCreateSpec> getNotReadyEmbed() {
		if (NOT_READY_EMBED != null)
			return NOT_READY_EMBED;
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTitle(Starota.BOT_NAME + " is still starting...");
		builder.withColor(0xF0, 0x00, 0x00);
		NOT_READY_EMBED = builder.build();
		return NOT_READY_EMBED;
	}

	// @Override
	// public void run() {
	// while (true) {
	// update();
	// try {
	// Thread.sleep(300000); // 5 mins
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	public static void update() {
		if (!Starota.getClient().isConnected())
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

	// private static EmbedCreateSpec getStatusEmbed() {
	// EmbedBuilder builder = new EmbedBuilder();
	// builder.withTitle("Status:");
	// for (Guild g : Starota.getClient().getGuilds()) {
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
	// private static EmbedCreateSpec getDiscordPermsEmbed() {
	// EmbedBuilder builder = new EmbedBuilder();
	// builder.withTitle("Missing Discord Perms:");
	// IUser ourUser = Starota.getOurUser();
	// for (Guild g : Starota.getClient().getGuilds()) {
	// if (EmojiServerHelper.isEmojiServer(g))
	// continue;
	// if (g.equals(DEBUG_SERVER))
	// continue;
	// String text = "";
	// PermissionSet invertPerms = EnumSet.allOf(Permission.class);
	// invertPerms.removeAll(USED_PERMISSIONS);
	// for (Permission p : invertPerms)
	// text += " - " + p + "\n";
	// builder.appendField(g.getName(), text.isEmpty() ? "All permissions given"
	// : text, true);
	// }
	// builder.withFooterText("Last updated");
	// builder.withTimestamp(System.currentTimeMillis());
	// return builder.build();
	// }
	//
	// private static EmbedCreateSpec getPatreonPermsEmbed() {
	// EmbedBuilder builder = new EmbedBuilder();
	// builder.withTitle("Patreon Perms:");
	// for (Guild g : Starota.getClient().getGuilds()) {
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
	// private static EmbedCreateSpec getModulesEmbed() {
	// EmbedBuilder builder = new EmbedBuilder();
	// builder.withTitle("Enabled Modules:");
	// for (Guild g : Starota.getClient().getGuilds()) {
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
