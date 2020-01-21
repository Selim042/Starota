package us.myles_selim.starota.misc.utils;

import discord4j.core.object.util.Snowflake;

public class StarotaConstants {

	/** Starota version */
	public final static String VERSION = "3.2.3";

	/** Starota user ID */
	public static final Snowflake STAROTA_ID = Snowflake.of(489245655710040099L);
	/** Starota dev user ID */
	public static final Snowflake STAROTA_DEV_ID = Snowflake.of(504088307148521475L);
	/** Selim user ID */
	public static final Snowflake SELIM_USER_ID = Snowflake.of(134855940938661889L);
	/** Support guild ID */
	public static final Snowflake SUPPORT_SERVER = Snowflake.of(436614503606779914L);
	/** Beta test guild ID */
	public static final Snowflake BETA_TEST_SERVER = Snowflake.of(613550318101331973L);

	/** Editor role on the support server */
	public static final Snowflake EDITOR_ROLE_ID = Snowflake.of(572498311525695498L);
	/** Editor channel on the support server */
	public static final Snowflake EDITOR_CHANNEL_ID = Snowflake.of(572498506359635968L);
	/** Tutorial channel on the support server */
	public static final Snowflake TUTORIAL_CHANNEL_ID = Snowflake.of(569938166988013588L);
	/** Channel on support server to post when votes come in */
	public static final Snowflake VOTE_NOTIFY_CHANNEL_ID = Snowflake.of(436614504277737472L);

	/** Permament invite link to the support server */
	public static final String SUPPORT_SERVER_LINK = "https://discord.gg/NxverNw";
	/** User agent for all HTTP requests */
	public static final String HTTP_USER_AGENT = "Starota/" + VERSION;

	public static class Settings {

		/** News publishing channel */
		public static final String NEWS_CHANNEL = "newsChannel";
		/** Changelog logging channel */
		public static final String CHANGES_CHANNEL = "changesChannel";
		/** Profile nickname setting */
		public static final String PROFILE_NICKNAME = "profileNickname";
		/** Server timezone */
		public static final String TIMEZONE = "timezone";
		/** Server weather API token */
		public static final String WEATHER_API_TOKEN = "weatherToken";
		/** Community coords */
		public static final String COORDS = "coords";
		/** "10 points for instinct!" */
		public static final String EASY_HOUSE_CUP_ENTRY = "easyHouseCupEntry";
		/** 24 hour clock */
		public static final String CLOCK_24H = "24hrClock";

	}

}
