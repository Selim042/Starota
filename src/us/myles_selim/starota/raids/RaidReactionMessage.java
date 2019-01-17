package us.myles_selim.starota.raids;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.EmojiServerHelper;
import us.myles_selim.starota.ImageHelper;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.Starota.BaseModules;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.pokedex.Counter;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.silph_road.SilphRoadData.RaidBoss;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class RaidReactionMessage extends ReactionMessage {

	private static final String EX_RAID_EMOJI = "ex_raid";
	private static final String RAID_EMOJI = "raid";
	private static final String[] EMOJI_NAMES = new String[] { "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "here" };

	private int tier;
	private String time;
	private String location;
	private RaidBoss boss;
	private EnumPokemon pokemon;
	private Form form;
	private final Map<IUser, ReactionEmoji> attending = new HashMap<>();;
	private final Map<IUser, ReactionEmoji> here = new HashMap<>();

	public RaidReactionMessage(int tier, String time, String location) {
		this.tier = tier;
		this.time = time;
		this.location = location;
	}

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		msg.removeReaction(user, react);
		if (!react.getUserReacted(Starota.getOurUser()))
			return;
		if (pokemon == null) {
			String[] parts = react.getEmoji().getName().split("_");
			pokemon = EnumPokemon.getPokemon(parts[0]);
			form = parts.length > 1 && pokemon.getFormSet() != null
					? pokemon.getFormSet().getForm(parts[1])
					: null;
			boss = SilphRoadData.getBoss(pokemon, form);
			RequestBuffer.request(() -> msg.removeAllReactions()).get();
			for (int i = 0; i < EMOJI_NAMES.length - 1; i++) {
				int iF = i;
				RequestBuffer.request(() -> {
					if (iF < 5)
						msg.addReaction(ReactionEmoji.of(EMOJI_NAMES[iF]));
				}).get();
			}
			RequestBuffer.request(() -> msg.addReaction(EmojiServerHelper.getEmoji(EMOJI_NAMES[5])));
			if (!GoHubDatabase.isEntryLoaded(pokemon)) {
				msg.edit(GoHubDatabase.LOADING_EMBED);
				GoHubDatabase.getEntry(pokemon, form == null ? null : form.toString());
			}
		} else {
			if (!react.getEmoji().getName().equals(EMOJI_NAMES[5])) {
				if (here.containsKey(user))
					here.remove(user);
				attending.put(user, react.getEmoji());
			} else {
				ReactionEmoji emoji;
				if (attending.containsKey(user))
					emoji = attending.get(user);
				else
					emoji = ReactionEmoji.of(EMOJI_NAMES[0]);
				here.put(user, emoji);
				if (attending.containsKey(user))
					attending.remove(user);
			}
		}
		RequestBuffer.request(() -> msg.edit(getEmbed(server)));
	}

	@Override
	protected EmbedObject getEmbed(StarotaServer server) {
		EmbedBuilder builder = new EmbedBuilder();
		PokedexEntry entry = null;
		if (pokemon != null && StarotaModule.isModuleEnabled(server, BaseModules.POKEDEX))
			entry = GoHubDatabase.getEntry(pokemon, form == null ? null : form.toString());
		if (pokemon != null) {
			String titleString = (form == null ? "" : form + " ") + pokemon + " Raid ";
			// if (boss.getTier() == 6)
			// titleString += EmojiServerHelper.getEmoji(EX_RAID_EMOJI);
			// else {
			// IEmoji raidEmoji =
			// EmojiServerHelper.getEmoji(RAID_EMOJI);
			// for (int i = 0; i < boss.getTier(); i++)
			// titleString += raidEmoji;
			// }
			builder.withTitle(titleString);
			builder.withColor(boss.getColor());
			builder.withThumbnail(ImageHelper.getOfficalArtwork(pokemon, form));
			if (entry != null) {
				String boostedString = "";
				for (EnumWeather w : entry.weatherInfluences)
					boostedString += w.getEmoji();
				builder.appendField("Weather Boosts:", boostedString, true);
			}
		}
		builder.appendField("Time:", time, true);
		builder.appendField("Location:", location, true);

		String attendingString = "";
		for (Entry<IUser, ReactionEmoji> e : attending.entrySet())
			attendingString += e.getValue() + " " + e.getKey().getName() + "\n";
		if (!attendingString.isEmpty())
			builder.appendField("Attending:", attendingString, false);

		String hereString = "";
		for (Entry<IUser, ReactionEmoji> e : here.entrySet())
			hereString += e.getValue() + " " + e.getKey().getName() + "\n";
		if (!hereString.isEmpty())
			builder.appendField("Here:", hereString, false);

		if (entry != null) {
			String counterString = "";
			int rank = 1;
			for (Counter c : entry.getTopCounters())
				counterString += String.format("#%d %s", rank++, c);
			if (!counterString.isEmpty())
				builder.appendField("Counters:", counterString, false);
		}
		if (pokemon == null)
			builder.appendField("Reaction Usage:",
					"React with the raid Pokemon to indicate what Pokemon the raid is for.", false);
		else
			builder.appendField("Reaction Usage:",
					"React with a numbered emoji to indicate how many you will be bringing to the raid. "
							+ "React with " + EmojiServerHelper.getEmoji("here")
							+ " to indicate that you and your other people are at the raid location.",
					false);
		return builder.build();
	}

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		for (RaidBoss b : SilphRoadData.getBosses(tier)) {
			String postfix = b.getForm() == null ? "" : "-" + b.getForm();
			RequestBuffer
					.request(() -> msg.addReaction(EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
							ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm()))));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
	}

}
