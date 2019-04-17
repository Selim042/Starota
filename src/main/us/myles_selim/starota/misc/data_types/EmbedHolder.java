package us.myles_selim.starota.misc.data_types;

import sx.blah.discord.api.internal.json.objects.EmbedObject;

/**
 * Only really used to turn JSON from
 * <a href="https://leovoel.github.io/embed-visualizer/">here</a> into embeds
 */
public class EmbedHolder {

	public String content;
	public EmbedObject embed;

	public EmbedHolder() {}

	public EmbedHolder(String content, EmbedObject embed) {
		this.content = content;
		this.embed = embed;
	}

}
