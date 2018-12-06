# Starota
Starota is a Discord bot designed for use on Pokemon Go oriented servers.

If you want to add Starota to your own server, you can use [this](https://discordapp.com/oauth2/authorize?client_id=489245655710040099&scope=bot&permissions=268445776) link.  If you want to join the support server, you can join [here](https://discord.gg/NxverNw).

It currently has a command system as well as player profiles, a WIP research reporting tracker, some basic role management functionality, a tradeboard system, custom Lua scripting, modules to enable/disable specific features, and a WIP web-sided interface.

I hope to make a simple wiki here soon to document these features in more detail as well as how to use them effectively.

If you want to host your own version of Starota, you must create an app at https://discordapp.com/developers/applications/ and make a bot for it.  Starota will get the token from a Java properties file called "starota.properties" and look for a token with the key "token".  Quite a bit of functionality will not be functional when self-hosting, primarily the web interface and the updating of what Pokemon are available and which can be shiny.  Any of these features may cause other issues or vulnerabilities that I am not responsible for as Starota is not designed to be used in this way.

To register your own commands, see how I do it in the main Starota class as well as how I implement commands.

Server data is stored in the EBS format, as found here: [Selim042/Extendable-Binary-Storage](https://github.com/Selim042/Extendable-Binary-Storage)
