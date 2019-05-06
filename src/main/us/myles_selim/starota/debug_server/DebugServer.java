package us.myles_selim.starota.debug_server;

import java.awt.Color;
import java.util.EnumSet;
import java.util.function.Consumer;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.Starota;

// TODO: Optimize this
public class DebugServer /* extends Thread */ {

	public static final long DEBUG_SERVER_ID = 517546213520965662L;
	public static final Consumer<EmbedCreateSpec> NOT_READY_EMBED = (e) -> e
			.setTitle(Starota.BOT_NAME + " is still starting...").setColor(new Color(0xF00000));

	private static Guild DEBUG_SERVER;
	private static final EnumSet<Permission> USED_PERMISSIONS = EnumSet.noneOf(Permission.class);

	// private static final Message STATUS;
	// private static final Message DISCORD_PERMS;
	// private static final Message PATREON_PERMS;
	// private static final Message MODULES;

	public static void init() {
		DEBUG_SERVER = Starota.getGuild(DEBUG_SERVER_ID);
		GuildChannel ch;

		if (Starota.IS_DEV)
			ch = DEBUG_SERVER.getChannelById(Snowflake.of(517546371503357972L)).block();
		else
			ch = DEBUG_SERVER.getChannelById(Snowflake.of(517548271435120643L)).block();
		// if (ch.getFullMessageHistory().isEmpty())
		// new StatusReactionMessage().createMessage(ch);

		if (Starota.IS_DEV)
			ch = DEBUG_SERVER.getChannelById(Snowflake.of(522806654434017282L)).block();
		else
			ch = DEBUG_SERVER.getChannelById(Snowflake.of(522806624260194304L)).block();
		// if (ch.getFullMessageHistory().isEmpty())
		// new DiscordPermsReactionMessage().createMessage(ch);

		if (Starota.IS_DEV)
			ch = DEBUG_SERVER.getChannelById(Snowflake.of(517548301932036096L)).block();
		else
			ch = DEBUG_SERVER.getChannelById(Snowflake.of(517546321595334656L)).block();
		// if (ch.getFullMessageHistory().isEmpty())
		// new DonorPermsReactionMessage().createMessage(ch);

		if (Starota.IS_DEV)
			ch = DEBUG_SERVER.getChannelById(Snowflake.of(518285102703181847L)).block();
		else
			ch = DEBUG_SERVER.getChannelById(Snowflake.of(518285130108764181L)).block();
		// if (ch.getFullMessageHistory().isEmpty())
		// new ModulesReactionMessage().createMessage(ch);
	}

	public static void addPermission(Permission perm) {
		USED_PERMISSIONS.add(perm);
	}

	public static EnumSet<Permission> getUsedPermission() {
		return USED_PERMISSIONS.clone();
	}

}
