package us.myles_selim.starota.misc.utils;

public class StarotaConstants {

	/** Starota version */
	public final static String VERSION = "2.10.1";

	/** Starota user ID */
	public static final long STAROTA_ID = 489245655710040099L;
	/** Starota dev user ID */
	public static final long STAROTA_DEV_ID = 504088307148521475L;
	/** Selim user ID */
	public static final long SELIM_USER_ID = 134855940938661889L;
	/** Support guild ID */
	public static final long SUPPORT_SERVER = 436614503606779914L;

	/** Editor role on the support server */
	public static final long EDITOR_ROLE_ID = 572498311525695498L;
	/** Editor channel on the support server */
	public static final long EDITOR_CHANNEL_ID = 572498506359635968L;

	/** Permament invite link to the support server */
	public static final String SUPPORT_SERVER_LINK = "https://discord.gg/NxverNw";
	/** User agent for all HTTP requests */
	public static final String HTTP_USER_AGENT = "Mozilla/5.0; Starota/" + VERSION;

	public static class Settings {

		/** News publishing channel */
		public static final String NEWS_CHANNEL = "newsChannel";
		/** Changelog logging channel */
		public static final String CHANGES_CHANNEL = "changesChannel";
		/** Profile nickname setting */
		public static final String PROFILE_NICKNAME = "profileNickname";
		/** Server timezone */
		public static final String TIMEZONE = "timezone";

	}

}
