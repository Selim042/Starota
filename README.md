# Starota
Starota is a Discord bot designed for use on Pokemon Go oriented servers.

It currently has a command system as well as player profiles, a WIP research reporting tracker, some basic role management functionality, and a WIP tradeboard system.

If you want to host your own version of Starota, you must create an app at https://discordapp.com/developers/applications/ and make a bot for it.  Starota will get the token from a Java properties file called "starota.properties" and look for a token with the key "token".

To register your own commands, see how I do it in the main Starota class as well as how I implement commands.

Server data is stored in the EBS format, as found here: [Selim042/Extendable-Binary-Storage](https://github.com/Selim042/Extendable-Binary-Storage)
