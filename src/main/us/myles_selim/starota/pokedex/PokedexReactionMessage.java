package us.myles_selim.starota.pokedex;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumPokemonType;
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
	public void onSend(StarotaServer server, IChannel channel, IMessage msg) {
		if (entry.forms.length <= 1)
			return;
		for (int i = 0; i < entry.forms.length; i++) {
			DexForm f = entry.forms[i];
			sendForms = true;
			IEmoji emoji = f.getEmoji(entry);
			RequestBuffer.request(() -> msg.addReaction(emoji));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}

	@Override
	public void onEdit(StarotaServer server, IChannel channel, IMessage msg) {
		if (!sendForms)
			onSend(server, channel, msg);
	}

	@Override
	public void onReactionAdded(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {
		String name = react.getEmoji().getName();
		if (name.startsWith(entry.name + "_")) {
			msg.removeReaction(user, react);
			String formName = name.substring(name.indexOf("_") + 1);
			if (!isFormValid(formName) || form.equals(formName))
				return;
			form = formName;
			if (!entry.hasEmbedPrepared(formName))
				RequestBuffer.request(() -> msg.edit(GoHubDatabase.LOADING_EMBED));
			EmbedObject newEmbed = getEmbed(server);
			RequestBuffer.request(() -> msg.edit(newEmbed));
		} else if (this.entry.getPokemon().equals(EnumPokemon.ARCEUS)) {
			msg.removeReaction(user, react);
			String formName = react.getEmoji().getName().toUpperCase();
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
				RequestBuffer.request(() -> msg.edit(GoHubDatabase.LOADING_EMBED));
			EmbedObject newEmbed = getEmbed(server);
			RequestBuffer.request(() -> msg.edit(newEmbed));
		}
	}

	@Override
	public void onReactionRemoved(StarotaServer server, IChannel channel, IMessage msg, IUser user,
			IReaction react) {}

	@Override
	protected EmbedObject getEmbed(StarotaServer server) {
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
