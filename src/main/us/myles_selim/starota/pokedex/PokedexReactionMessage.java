package us.myles_selim.starota.pokedex;

import java.util.function.Consumer;

import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.object.reaction.ReactionEmoji.Custom;
import discord4j.core.spec.EmbedCreateSpec;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
import us.myles_selim.starota.misc.utils.MiscUtils;
import us.myles_selim.starota.pokedex.PokedexEntry.DexForm;
import us.myles_selim.starota.reaction_messages.ReactionMessage;
import us.myles_selim.starota.wrappers.StarotaServer;

public class PokedexReactionMessage extends ReactionMessage {

	private PokedexEntry entry;
	private String form;
	private boolean sendForms = false;

	public PokedexReactionMessage() {}

	public PokedexReactionMessage(PokedexEntry entry) {
		this(entry, "Normal");
	}

	public PokedexReactionMessage(PokedexEntry entry, String form) {
		this.entry = entry;
		this.form = form;
	}

	@Override
	public void onSend(StarotaServer server, TextChannel channel, Message msg) {
		if (entry.forms.length <= 1)
			return;
		for (int i = 0; i < entry.forms.length; i++) {
			DexForm f = entry.forms[i];
			sendForms = true;
			Custom emoji = f.getEmoji(entry);
			msg.addReaction(emoji).block();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}

	@Override
	public void onEdit(StarotaServer server, TextChannel channel, Message msg) {
		if (!sendForms)
			onSend(server, channel, msg);
	}

	@Override
	public void onReactionAdded(StarotaServer server, TextChannel channel, Message msg, Member user,
			ReactionEmoji react) {
		if (!react.asCustomEmoji().isPresent())
			return;
		String name = MiscUtils.getEmojiName(react);
		if (name.startsWith(entry.name + "_")) {
			msg.removeReaction(react, user.getId()).block();
			String formName = name.substring(name.indexOf("_") + 1);
			if (!isFormValid(formName) || form.equals(formName))
				return;
			form = formName;
			if (!entry.hasEmbedPrepared(formName))
				msg.edit((m) -> m.setEmbed(GoHubDatabase.LOADING_EMBED)).block();
			Consumer<? super EmbedCreateSpec> newEmbed = getEmbed(server);
			msg.edit((m) -> m.setEmbed(newEmbed)).block();
		} else if (this.entry.getPokemon().equals(EnumPokemon.ARCEUS)) {
			msg.removeReaction(react, user.getId()).block();
			String formName = react.asCustomEmoji().get().getName().toUpperCase();
			if (formName.endsWith("TYPE"))
				formName = formName.substring(0, formName.length() - 4);
			try {
				EnumPokemonType.valueOf(formName);
			} catch (IllegalArgumentException e) {
				return;
			}
			formName = formName.substring(0, 1) + formName.substring(1, formName.length()).toLowerCase();
			if (!isFormValid(formName) || form.equals(formName))
				return;
			form = formName;
			if (!entry.hasEmbedPrepared(formName))
				msg.edit((m) -> m.setEmbed(GoHubDatabase.LOADING_EMBED)).block();
			Consumer<? super EmbedCreateSpec> newEmbed = getEmbed(server);
			msg.edit((m) -> m.setEmbed(newEmbed)).block();
		}
	}

	@Override
	public void onReactionRemoved(StarotaServer server, TextChannel channel, Message msg, Member user,
			ReactionEmoji react) {}

	@Override
	protected Consumer<? super EmbedCreateSpec> getEmbed(StarotaServer server) {
		return entry.toEmbed(form);
	}

	private boolean isFormValid(String form) {
		for (DexForm f : entry.forms)
			if (form.equals(f.name))
				return true;
		return false;
	}

	// @Override
	// public void toBytes(Storage stor) {
	// if (this.entry != null)
	// stor.writeInt(this.entry.id);
	// else
	// stor.writeInt(0);
	// stor.writeString(this.form);
	// }
	//
	// @Override
	// public void fromBytes(Storage stor) {
	// int id = stor.readInt();
	// this.form = stor.readString();
	// if (id == 0)
	// this.entry = null;
	// else
	// this.entry = GoHubDatabase.getEntry(EnumPokemon.getPokemon(id),
	// this.form);
	// }

}
