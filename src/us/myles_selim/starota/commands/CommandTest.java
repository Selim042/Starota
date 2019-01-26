package us.myles_selim.starota.commands;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class CommandTest extends StarotaCommand {

	public CommandTest() {
		super("test");
	}

	@Override
	public void execute(String[] args, IMessage message, StarotaServer server, IChannel channel) {
		// new ReactionMessage() {
		//
		// @Override
		// protected EmbedObject getEmbed() {
		// return new EmbedBuilder().withDescription("react to test").build();
		// }
		// }.sendMessage(channel);

		// EmbedBuilder builder = new EmbedBuilder();
		// builder.withDescription("loading");
		// IMessage msg = channel.sendMessage(builder.build());
		// builder.withDescription("");
		// for (RaidBoss boss : SilphRoadData.getBosses()) {
		// builder.appendDescription("Tier " + boss.getTier() + ": " +
		// boss.getPokemon()
		// + (boss.getForm() == null ? "\n" : " (" + boss.getForm() + ")\n"));
		// String formName = boss.getForm() == null ? "" :
		// boss.getForm().toString();
		// RequestBuffer.request(() ->
		// msg.addReaction(EmojiServerHelper.getEmoji(
		// boss.getPokemon() + (formName.isEmpty() ? "" : "_" + formName),
		// ImageHelper.getOfficalArtwork(boss.getPokemon(), boss.getForm()))));
		// }
		// msg.edit(builder.build());

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
	}

}
