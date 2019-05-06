package us.myles_selim.starota.misc.utils;

import discord4j.core.object.util.Snowflake;

public class StarotaConstants {

	/** Starota version */
	public final static String VERSION = "2.9.0";

	/** Starota user ID */
	public static final Snowflake STAROTA_ID = Snowflake.of(489245655710040099L);
	/** Starota dev user ID */
	public static final Snowflake STAROTA_DEV_ID = Snowflake.of(504088307148521475L);
	/** Selim user ID */
	public static final Snowflake SELIM_USER_ID = Snowflake.of(134855940938661889L);
	/** Support guild ID */
	public static final Snowflake SUPPORT_SERVER = Snowflake.of(436614503606779914L);

	/** Editor role on the support server */
	public static final Snowflake EDITOR_ROLE_ID = Snowflake.of(572498311525695498L);
	/** Editor channel on the support server */
	public static final Snowflake EDITOR_CHANNEL_ID = Snowflake.of(572498506359635968L);

	/** Permament invite link to the support server */
	public static final String SUPPORT_SERVER_LINK = "https://discord.gg/NxverNw";
	/** User agent for all HTTP requests */
	public static final String HTTP_USER_AGENT = "Mozilla/5.0; Starota/" + VERSION;

	public static class Settings {

		/** News publishing channel */
		public static final String NEWS_CHANNEL = "newsChannel";
		/** Changelog logging channel */
		public static final String CHANGES_CHANNEL = "changesChannel";

	}

}
