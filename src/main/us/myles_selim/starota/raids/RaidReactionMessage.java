package us.myles_selim.starota.raids;

import java.util.HashMap;
import java.util.List;
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
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.misc.data_types.RaidBoss;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry;
import us.myles_selim.starota.pokedex.PokedexEntry.DexCounter;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class RaidReactionMessage extends ReactionMessage {

	private static final String EX_RAID_EMOJI = "ex_raid";
	private static final String RAID_EMOJI = "raid";
	private static final String[] EMOJI_NAMES = new String[] { "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "here",
			"🚫" };

	private int tier;
	private String time;
	private String location;
	private RaidBoss boss;
	private EnumPokemon pokemon;
	private Form form;
	private final Map<IUser, ReactionEmoji> attending = new HashMap<>();
	private final Map<IUser, ReactionEmoji> here = new HashMap<>();

	public RaidReactionMessage(EnumPokemon pokemon, Form form, int tier, String time, String location) {
		this.boss = new RaidBoss(pokemon, form, tier);
		this.pokemon = pokemon;
		this.form = form;
		this.tier = tier;
		this.time = time;
		this.location = location;
	}

	public RaidReactionMessage(int tier, String time, String location) {
		List<RaidBoss> bosses = SilphRoadData.getBosses(tier);
		if (bosses.size() == 1) {
			this.boss = bosses.get(0);
			this.pokemon = boss.getPokemon();
			this.form = boss.getForm();
		}
		this.tier = tier;
		this.time = time;
		this.location = location;
	}

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		msg.removeReaction(user, react);
		if (pokemon != null && !MiscUtils.arrContains(EMOJI_NAMES, react.getEmoji().getName()))
			return;
		if (pokemon == null) {
			String[] parts = react.getEmoji().getName().split("_");
			pokemon = EnumPokemon.getPokemon(parts[0]);
			form = parts.length > 1 && pokemon.getFormSet() != null
					? pokemon.getFormSet().getForm(parts[1])
					: null;
			boss = SilphRoadData.getBoss(pokemon, form);
			if (boss == null)
				return;
			RequestBuffer.request(() -> msg.removeAllReactions()).get();
			for (int i = 0; i < EMOJI_NAMES.length - 2; i++) {
				int iF = i;
				RequestBuffer.request(() -> {
					msg.addReaction(ReactionEmoji.of(EMOJI_NAMES[iF]));
				}).get();
			}
			RequestBuffer.request(() -> msg.addReaction(EmojiServerHelper.getEmoji(EMOJI_NAMES[5])))
					.get();
			RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(EMOJI_NAMES[6]))).get();
			if (!GoHubDatabase.isEntryLoaded(pokemon)) {
				msg.edit(GoHubDatabase.LOADING_EMBED);
				GoHubDatabase.getEntry(pokemon,
						form == null ? null
								: (form.getSpritePostfix(pokemon) == null ? form.toString()
										: form.getSpritePostfix(pokemon)));
			}
		} else {
			if (react.getEmoji().getName().equals(EMOJI_NAMES[5])) {
				ReactionEmoji emoji;
				if (attending.containsKey(user))
					emoji = attending.get(user);
				else
					emoji = ReactionEmoji.of(EMOJI_NAMES[0]);
				here.put(user, emoji);
				if (attending.containsKey(user))
					attending.remove(user);
			} else if (react.getEmoji().getName().equals(EMOJI_NAMES[6])) {
				if (here.containsKey(user))
					here.remove(user);
				if (attending.containsKey(user))
					attending.remove(user);
			} else {
				if (here.containsKey(user))
					here.remove(user);
				attending.put(user, react.getEmoji());
			}
		}
		RequestBuffer.request(() -> msg.edit(getEmbed(server)));
	}

	@Override
	protected EmbedObject getEmbed(StarotaServer server) {
		EmbedBuilder builder = new EmbedBuilder();
		PokedexEntry entry = null;
		if (pokemon != null && StarotaModule.isModuleEnabled(server, BaseModules.POKEDEX))
			entry = GoHubDatabase.getEntry(pokemon,
					form == null ? null
							: (form.getSpritePostfix(pokemon) == null ? form.toString()
									: form.getSpritePostfix(pokemon)));
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
		for (Entry<IUser, ReactionEmoji> e : attending.entrySet()) {
			String nickname = e.getKey().getNicknameForGuild(server.getDiscordGuild());
			if (nickname == null)
				attendingString += e.getValue() + " " + e.getKey().getName() + "#"
						+ e.getKey().getDiscriminator() + "\n";
			else
				attendingString += e.getValue() + " " + nickname + " (_" + e.getKey().getName() + "#"
						+ e.getKey().getDiscriminator() + "_)\n";
		}
		if (!attendingString.isEmpty())
			builder.appendField("Attending:", attendingString, false);

		String hereString = "";
		for (Entry<IUser, ReactionEmoji> e : here.entrySet())
			hereString += e.getValue() + " " + e.getKey().getName() + "\n";
		if (!hereString.isEmpty())
			builder.appendField("Here:", hereString, false);

		if (entry != null) {
			String cpsString = "";
			cpsString += "**Max CP**: " + entry.CPs.max + "\n";
			cpsString += "**Catch CP**: " + entry.CPs.raidCaptureMin + "-" + entry.CPs.raidCaptureMax
					+ "\n";
			cpsString += "**Boosted Catch CP**: " + entry.CPs.raidCaptureBoostMin + "-"
					+ entry.CPs.raidCaptureBoostMax;
			builder.appendField("Important CPs:", cpsString, false);

			String counterString = "";
			int rank = 1;
			for (DexCounter c : entry.getTopCounters())
				if (c != null)
					counterString += String.format("#%d %s", rank++, c);
			if (!counterString.isEmpty())
				builder.appendField("Counters:", counterString, false);
		}
		if (pokemon == null)
			builder.appendField("Reaction Usage:",
					"React with the raid Pokemon to indicate what Pokemon the raid is for.", false);
		else
			builder.appendField("Reaction Usage:",
					"React with a numbered emoji to indicate how many people you will be bringing to the raid.\n"
							+ "React with " + EmojiServerHelper.getEmoji("here")
							+ " to indicate that you and your other people are at the raid location.\n"
							+ "React with " + ReactionEmoji.of(EMOJI_NAMES[6])
							+ " if you are no longer able to attend.",
					false);
		return builder.build();
	}

	@Override
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		if (boss != null) {
			for (int i = 0; i < EMOJI_NAMES.length - 2; i++) {
				int iF = i;
				RequestBuffer.request(() -> {
					msg.addReaction(ReactionEmoji.of(EMOJI_NAMES[iF]));
				}).get();
			}
			RequestBuffer.request(() -> msg.addReaction(EmojiServerHelper.getEmoji(EMOJI_NAMES[5])))
					.get();
			RequestBuffer.request(() -> msg.addReaction(ReactionEmoji.of(EMOJI_NAMES[6]))).get();
			return;
		}
		List<RaidBoss> bosses = SilphRoadData.getBosses(tier);
		for (RaidBoss b : bosses) {
			String postfix = b.getForm() == null ? ""
					: "_" + b.getForm().getSpritePostfix(b.getPokemon());
			RequestBuffer
					.request(() -> msg.addReaction(EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
							ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm()))));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
	}

}
