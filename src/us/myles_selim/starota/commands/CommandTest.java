package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.geofence.GeoPoint;
import us.myles_selim.starota.geofence.GeoRegion;
import us.myles_selim.starota.webserver.webhooks.EnumWebhookType;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTest extends StarotaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		// try {
		// RaidReactionMessage rMsg = new RaidReactionMessage(6, "9:30",
		// "Chess Pieces - UW-Platteville");
		// Field bossField = RaidReactionMessage.class.getDeclaredField("boss");
		// bossField.setAccessible(true);
		// bossField.set(rMsg, new RaidBoss(EnumPokemon.KYOGRE, null, 5));
		// Field pokeField =
		// RaidReactionMessage.class.getDeclaredField("pokemon");
		// pokeField.setAccessible(true);
		// pokeField.set(rMsg, EnumPokemon.KYOGRE);
		// Method method =
		// RaidReactionMessage.class.getDeclaredMethod("getEmbed",
		// StarotaServer.class);
		// method.setAccessible(true);
		// EmbedObject embed = (EmbedObject) method.invoke(rMsg, server);
		// embed.image = new EmbedObject.ImageObject(String.format(
		// "https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v11/static/pin-s+%06X(-90.4879800,42.733048)/-90.4879800,42.733048,16.5,0,0/600x300@2x?logo=false&access_token=pk.eyJ1Ijoic2VsaW0wNDIiLCJhIjoiY2pyOXpmM2g1MG16cTQzbndqZXk5dHNndCJ9.vsh20BzsPBgTcBBcKWBqQw",
		// EnumTeam.INSTINCT.getColor()), null, 0, 0);
		// channel.sendMessage(embed);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// channel.sendMessage(TimeZone.getTimeZone(args[1]).toString());

		server.defineRegion("Test Region #1 (Mad)",
				new GeoRegion(new GeoPoint(43.052199, -89.326932), new GeoPoint(43.056464, -89.307105),
						new GeoPoint(43.045927, -89.296033), new GeoPoint(43.032879, -89.311225),
						new GeoPoint(43.038713, -89.332511)));
		server.defineRegion("Test Region #2 (Platt)",
				new GeoRegion(new GeoPoint(42.777584, -90.572391), new GeoPoint(42.803786, -90.463901),
						new GeoPoint(42.778592, -90.328632), new GeoPoint(42.672159, -90.368457),
						new GeoPoint(42.673674, -90.560718)).setHookChannel(EnumWebhookType.RAID,
								538156939868110854L));

		// AssistantRequest.request((client) -> {
		// IChannel ch = client.getChannelByID(channel.getLongID());
		// ch.sendMessage("This is a test assistant request #1");
		// ch.sendMessage("This is a test assistant request #2");
		// });
	}

}
