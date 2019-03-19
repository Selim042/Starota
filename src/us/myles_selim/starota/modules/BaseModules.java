package us.myles_selim.starota.modules;

public class BaseModules {

	private static boolean registered = false;

	public static final StarotaModule PROFILES = new StarotaModule("PlayerProfiles", "Profiles")
			.setDescription(
					"Player profiles are a feature of Starota that allows players to easily share some "
							+ "information about themselves with the community.  "
							+ "Profiles can show such features as your Pokemon Go username, level, and team, "
							+ "as well as optionally your real name, trainer code, and alternate accounts.\n\n"
							+ "Your profile will be default show your team logo, but if you also have a trainer "
							+ "card setup on [TheSilphRoad.com](http://thesilphroad.com/), it will instead show "
							+ "your avatar from your card and a link to your card.  "
							+ "If you are a patron of Starota, it will also show your patron tier.\n\n"
							+ "![Player Profile Sample](http://assets.myles-selim.us/starota/wiki/player_profile.png)");
	public static final StarotaModule GROUPS = new StarotaModule("Groups", "Groups").setDescription(
			"The groups feature lets server owners specify roles that users can \"self-assign\" to "
					+ "themselves and leave at will.");
	public static final StarotaModule TRADEBOARD = new StarotaModule("Tradeboard", "Tradeboard",
			PROFILES).setDescription(
					"The tradeboard allows users to list Pokemon they are either looking for or would like "
							+ "to get in a trade.  When posting a trade, you can specify if the Pokemon is shiny "
							+ "(if released), has legacy moves, or a specific form (Kanotian vs Alolan, sunny vs "
							+ "rainy vs snowy vs normal Castform, etc) as well as genders.\n\n"
							+ "If the poster of the trade has their trainer code on their profile, it will also be displayed on the trade.\n\n"
							+ "![Trade](http://assets.myles-selim.us/starota/wiki/trade.png)");
	public static final StarotaModule LUA = new StarotaModule("Lua", "Lua").setDescription(
			"The Lua module allows server admins to create custom commands and interactions on thier "
					+ "Discord server.  Using `.uploadScript` you can upload both scripts to be "
					+ "executed when a user executes a command as well as a \"event handler\" script "
					+ "that are executed when Discord4J events are fired.\n\n"
					+ "See [luaExamples](https://github.com/Selim042/Starota/tree/master/luaExamples) "
					+ "for some examples and more information at PR [#1](https://github.com/Selim042/Starota/pull/1).");
	public static final StarotaModule LEADERBOARDS = new StarotaModule("Leaderboards", "Leaderboard",
			PROFILES).setDescription(
					"Leaderboards allow server owners to create custom leaderboards that their users "
							+ "can compete on for various things like total XP, level, Pokedex completion, "
							+ "pretty much anything that can be tracked by a number, either incrementing or "
							+ "decrementing.\n\n"
							+ "![Leaderboard](http://assets.myles-selim.us/starota/wiki/leaderboard.png)\n\n"
							+ "![Leaderboard Options](http://assets.myles-selim.us/starota/wiki/leaderboard_options.png)");
	public static final StarotaModule PVP = new StarotaModule("PvP", "PvP", PROFILES).setDescription(
			"Starota has commands that helps coordinate PvP battles by allowing users to "
					+ "list themselves as \"battle ready\".");
	public static final StarotaModule POKEDEX = new StarotaModule("Pokedex", "Pokedex");
	public static final StarotaModule SILPH_ROAD = new StarotaModule("SilphRoad", "Silph Road");
	public static final StarotaModule HTTP = new StarotaModule("HTTP")
			.setDescription("HTTP is a WIP feature that will be available to donors only.\n\n"
					+ "The HTTP module allows users to view Starota information specific to your server online.");
	public static final StarotaModule WEBHOOKS = new StarotaModule("Webhooks", "Webhooks")
			.setDescription("The webhook module allows 3rd party scanners to connect to Starota "
					+ "to display raid, task, and Pokemon spawn information.");
	public static final StarotaModule SEARCH = new StarotaModule("Search", "Search").setDescription(
			"The search module enables searching of data in the Pokedex and other information.");

	public static void registerModules() {
		if (registered)
			return;
		registered = true;

		StarotaModule.registerModule(PROFILES);
		StarotaModule.registerModule(GROUPS);
		StarotaModule.registerModule(TRADEBOARD);
		StarotaModule.registerModule(LUA);
		StarotaModule.registerModule(LEADERBOARDS);
		StarotaModule.registerModule(PVP);
		StarotaModule.registerModule(POKEDEX);
		StarotaModule.registerModule(SILPH_ROAD);
		StarotaModule.registerModule(HTTP);
		StarotaModule.registerModule(WEBHOOKS);
		StarotaModule.registerModule(SEARCH);
	}

}
