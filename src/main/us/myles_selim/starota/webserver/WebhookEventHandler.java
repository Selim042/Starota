package us.myles_selim.starota.webserver;

import us.myles_selim.starota.misc.utils.EventListener;
import us.myles_selim.starota.webserver.webhooks.WebhookEvent;

public class WebhookEventHandler implements EventListener<WebhookEvent> {

	private static long questTime;
	private static int date;

	public void execute(WebhookEvent event) {
//		Channel channel;
//		if (Starota.IS_DEV)
//			channel = Starota.getChannel(545687895177166871L);
//		else
//			channel = Starota.getChannel(538156939868110854L);
//		StarotaAssistants.setPermissionForChannel(channel);
//		switch (event.getType()) {
//		case RAID:
//			WebhookRaid raidData = (WebhookRaid) event.getWebhookData();
//			StarotaServer raidServer = StarotaServer.getServer(event.getGuild());
//			// RequestBuffer.request(() -> channel.createMessage("Raid in
//			// region: "
//			// + server.getRegion(new GeoPoint(raidData.latitude,
//			// raidData.longitude))));
//			if (Starota.IS_DEV || raidServer.getRegion(raidData.getPoint()) != null) {
//				if (raidData.hasHatched()) {
//					AssistantRequest.request((client) -> {
//						return new WebhookRaidReactionMessage(raidData)
//								.createMessage(client.getChannelById(channel.getId().asLong()));
//					});
//				} else {
//					EmbedObject embed = raidData.toEmbed();
//					AssistantRequest.request((client) -> {
//						return client.getChannelById(channel.getId().asLong()).createMessage(embed);
//					});
//				}
//			}
//			break;
//		case QUEST:
//			// if (System.currentTimeMillis() - questTime < 1800000) // 30 mins
//			// break;
//			// questTime = System.currentTimeMillis();
//			// WebhookQuest taskData = (WebhookQuest) event.getWebhookData();
//			// StarotaServer taskServer =
//			// StarotaServer.getServer(event.getGuild());
//			// // RequestBuffer.request(() -> channel.createMessage("Raid in
//			// // region: "
//			// // + server.getRegion(new GeoPoint(raidData.latitude,
//			// // raidData.longitude))));
//			// if (Starota.IS_DEV || taskServer.getRegion(taskData.getPoint())
//			// != null) {
//			// AssistantRequest.request((client) -> {
//			// return client.getChannelById(channel.getId().asLong())
//			// .createMessage("```json\n" + taskData.data + "\n```");
//			// });
//			// }
//			File file = new File("quests.txt");
//			Date today = new Date();
//			if (today.getDate() != date)
//				file.delete();
//			date = today.getDate();
//			try {
//				FileWriter writer = new FileWriter(file);
//				writer.write("\n" + ((WebhookQuest) event.getWebhookData()).data);
//				writer.flush();
//				writer.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			break;
//		case POKEMON:
//			if (!Starota.IS_DEV)
//				break;
//			WebhookPokemon pokeData = (WebhookPokemon) event.getWebhookData();
//			StarotaServer pokeServer = StarotaServer.getServer(event.getGuild());
//			if (pokeServer.getRegion(new GeoPoint(pokeData.latitude, pokeData.longitude)) != null) {
//				EmbedObject embed = getPokemonEmbed(pokeData);
//				AssistantRequest.request((client) -> {
//					return client.getChannelById(channel.getId().asLong()).createMessage(embed);
//				});
//			}
//			break;
//		default:
//			break;
//		}
	}

//	private static EmbedObject getPokemonEmbed(WebhookPokemon pokemonData) {
//		EmbedBuilder builder = new EmbedBuilder();
//		EnumPokemon pokemon = pokemonData.getPokemon();
//		builder.setTitle(String.format("A wild %s has spawned!", pokemon.getName()));
//		builder.withThumbnail(ImageHelper.getOfficalArtwork(pokemon, pokemonData.form));
//		builder.withColor(pokemon.getType1().getColor());
//
//		builder.appendDesc("CP: " + pokemonData.cp);
//		if (pokemonData.hasIVs())
//			builder.appendDesc(String.format("IV: %.1f, Atk: %n, Def: %n, Sta: %n",
//					pokemonData.getIVPercent(), pokemonData.getAttackIV(), pokemonData.getDefenseIV(),
//					pokemonData.getStaminaIV()));
//
//		builder.addField("Directions:",
//				String.format(
//						"[Google Maps](https://www.google.com/maps/search/?api=1&query=%1$f,%2$f) | "
//								+ "[Apple Maps](http://maps.apple.com/?daddr=%1$f,%2$f)",
//						pokemonData.latitude, pokemonData.longitude),
//				false);
//		builder.withImage(String.format(
//				"https://api.mapbox.com/styles/v1/mapbox/satellite-streets-v11/static/pin-s+%06X(%2$f,%3$f)/%2$f,%3$f,16.5,0,0/600x300@2x?logo=false&access_token=pk.eyJ1Ijoic2VsaW0wNDIiLCJhIjoiY2pyOXpmM2g1MG16cTQzbndqZXk5dHNndCJ9.vsh20BzsPBgTcBBcKWBqQw",
//				pokemon.getType1().getColor(), pokemonData.longitude, pokemonData.latitude));
//		return builder.build();
//	}

	@Override
	public Class<WebhookEvent> getEventType() {
		return WebhookEvent.class;
	}

}
