package us.myles_selim.starota.raids;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumWeather;
import us.myles_selim.starota.misc.data_types.BotServer;
import us.myles_selim.starota.misc.data_types.RaidBoss;
import us.myles_selim.starota.misc.utils.EmbedBuilder;
import us.myles_selim.starota.misc.utils.EmojiServerHelper;
import us.myles_selim.starota.misc.utils.ImageHelper;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.modules.BaseModules;
import us.myles_selim.starota.modules.StarotaModule;
import us.myles_selim.starota.pokedex.GoHubDatabase;
import us.myles_selim.starota.pokedex.PokedexEntry;
import us.myles_selim.starota.pokedex.PokedexEntry.DexCounter;
import us.myles_selim.starota.reaction_messages.IHelpReactionMessage;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.silph_road.SilphRoadData;
import us.myles_selim.starota.trading.forms.FormSet.Form;
import us.myles_selim.starota.wrappers.StarotaServer;

public class RaidReactionMessage extends ReactionMessage implements IHelpReactionMessage {

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
	private final Map<Member, ReactionEmoji> attending = new HashMap<>();
	private final Map<Member, ReactionEmoji> here = new HashMap<>();

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

	@SuppressWarnings("deprecation")
	@Override
	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member user,
			ReactionEmoji react) {
		msg.removeReaction(react, user.getId()).block();
		String emojiName = MiscUtils.getEmojiName(react);
		if (pokemon != null && !MiscUtils.arrContains(EMOJI_NAMES, emojiName))
			return;
		if (pokemon == null) {
			String[] parts = react.asCustomEmoji().get().getName().split("_");
			pokemon = EnumPokemon.getPokemon(parts[0]);
			form = parts.length > 1 && pokemon.getFormSet() != null
					? pokemon.getFormSet().getForm(parts[1])
					: null;
			boss = SilphRoadData.getBoss(pokemon, form);
			if (boss == null)
				return;
			msg.removeAllReactions().block();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
			this.addHelpButton(this, server);
			for (int i = 0; i < EMOJI_NAMES.length - 2; i++) {
				int iF = i;
				msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[iF])).block();
			}
			msg.addReaction(EmojiServerHelper.getEmoji(EMOJI_NAMES[5])).block();
			msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[6])).block();
			if (!GoHubDatabase.isEntryLoaded(pokemon)) {
				msg.edit((m) -> m.setEmbed(GoHubDatabase.LOADING_EMBED)).block();
				GoHubDatabase.getEntry(pokemon,
						form == null ? null
								: (form.getSpritePostfix(pokemon) == null ? form.toString()
										: form.getSpritePostfix(pokemon)));
			}
		} else {
			if (emojiName.equals(EMOJI_NAMES[5])) {
				ReactionEmoji emoji;
				if (attending.containsKey(user))
					emoji = attending.get(user);
				else
					emoji = ReactionEmoji.unicode(EMOJI_NAMES[0]);
				here.put(user, emoji);
				if (attending.containsKey(user))
					attending.remove(user);
			} else if (emojiName.equals(EMOJI_NAMES[6])) {
				if (here.containsKey(user))
					here.remove(user);
				if (attending.containsKey(user))
					attending.remove(user);
			} else {
				if (here.containsKey(user))
					here.remove(user);
				attending.put(user, react);
			}
		}
		msg.edit((m) -> m.setEmbed(getEmbed(server))).block();
	}

	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
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
			// GuildEmoji raidEmoji =
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
					boostedString += w.getEmoji().asFormat();
				builder.appendField("Weather Boosts:", boostedString, true);
			}
		}
		builder.appendField("Time:", time, true);
		builder.appendField("Location:", location, true);

		String attendingString = "";
		for (Entry<Member, ReactionEmoji> e : attending.entrySet())
			attendingString += MiscUtils.getEmojiName(e.getValue()) + " " + e.getKey().getDisplayName()
					+ "\n";
		if (!attendingString.isEmpty())
			builder.appendField("Attending:", attendingString, false);

		String hereString = "";
		for (Entry<Member, ReactionEmoji> e : here.entrySet())
			hereString += MiscUtils.getEmojiName(e.getValue()) + " " + e.getKey().getDisplayName()
					+ "\n";
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
		return builder.build();
	}

	@Override
	public void onSend(StarotaServer server, TextChannel channel, Message msg) {
		if (boss != null) {
			for (int i = 0; i < EMOJI_NAMES.length - 2; i++)
				msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[i])).block();
			msg.addReaction(EmojiServerHelper.getEmoji(EMOJI_NAMES[5])).block();
			msg.addReaction(ReactionEmoji.unicode(EMOJI_NAMES[6])).block();
			return;
		}
		List<RaidBoss> bosses = SilphRoadData.getBosses(tier);
		for (RaidBoss b : bosses) {
			String postfix = b.getForm() == null ? ""
					: "_" + b.getForm().getSpritePostfix(b.getPokemon());
			msg.addReaction(EmojiServerHelper.getEmoji(b.getPokemon() + postfix,
					ImageHelper.getOfficalArtwork(b.getPokemon(), b.getForm()))).block();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
	}

	@Override
	public Consumer<? super EmbedCreateSpec> getHelpEmbed(BotServer server) {
		EmbedBuilder builder = new EmbedBuilder();
		if (pokemon == null)
			builder.appendDescription("**Reaction Usage**:\n"
					+ "React with the raid Pokemon to indicate what Pokemon the raid is for.");
		else
			builder.appendDescription("**Reaction Usage**:\n"
					+ "React with a numbered emoji to indicate how many people you will be bringing to the raid.\n"
					+ "React with " + MiscUtils.getEmojiDisplay(EmojiServerHelper.getEmoji("here"))
					+ " to indicate that you and your other people are at the raid location.\n"
					+ "React with " + ReactionEmoji.unicode(EMOJI_NAMES[6]).getRaw()
					+ " if you are no longer able to attend.");
		return builder.build();
	}

}
