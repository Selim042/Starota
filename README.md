# Starota
[![](https://discordbots.org/api/widget/489245655710040099.svg)](https://discordbots.org/bot/489245655710040099)
[![](https://discordbots.org/api/widget/541079916234407967.svg)](https://discordbots.org/bot/541079916234407967)
[![](https://discordbots.org/api/widget/541079916234407967.svg)](https://discordbots.org/bot/578326030603780126)

[![](https://img.shields.io/discord/436614503606779914.svg)](https://discord.gg/NxverNw)
[![](https://img.shields.io/twitter/follow/Starota_Bot.svg?label=Follow&style=flat)](https://twitter.com/Starota_Bot)
[![](https://discordbots.org/api/widget/status/489245655710040099.svg)](https://discordbots.org/bot/489245655710040099)

Starota is a Discord bot designed for use on Pokemon Go oriented servers.

If you want to add Starota to your own server, you can invite it from here [this](https://discordbots.org/bot/489245655710040099).  If you want to join the support server, you can join [here](https://discord.gg/NxverNw).  If you are interested in just the Pokedex functionality, you are in luck, I made another bot with just that feature and nothing else and you can invite it from [here](https://discordbots.org/bot/541079916234407967).

Features:
  - Player profiles (featuring level, team, trainer code, real name, link to Silph Road card, and alternate accounts)
  - Basic role management (mark roles as "self-assignable", users can add and remove said roles at any point)
  - Tradeboard (post Pokemon you are looking for/are looking to trade away)
  - Leaderboards (server owners can make unique leaderboards to track anything)
  - Pokedex lookup (using Pokemon Go Hub)
  - PvP coordination commands (mark yourself as "battle-ready")
  - Command to help coordinate and plan raids
  - Show ongoing and upcoming events
  - Raid coordination commands
  - Pokemon search functionality
  - Command to display current Ditto disguises
  - Command to show all current egg hatches
  - Custom Lua scripting for unique features (Patrons only)
  - WIP web-sided interface (Patrons only)

I hope to make a simple wiki here soon to document these features in more detail as well as how to use them effectively.

If you want to host your own version of Starota, you must create an app at <https://discordapp.com/developers/applications/> and make a bot for it.  Starota will get the token from a Java properties file called "starota.properties" and look for a token with the key "token".  Quite a bit of functionality will not be functional when self-hosting, primarily the web interface and the updating of what Pokemon are available and which can be shiny.  Any of these features may cause other issues or vulnerabilities that I am not responsible for as Starota is not designed to be used in this way.

To register your own commands, see how I do it in the main Starota class as well as how I implement commands.

Server data is stored in the EBS format, as found here: [Selim042/Extendable-Binary-Storage](https://github.com/Selim042/Extendable-Binary-Storage)
