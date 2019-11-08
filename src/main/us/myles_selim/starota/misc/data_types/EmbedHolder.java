package us.myles_selim.starota.misc.data_types;

import discord4j.core.object.data.stored.embed.EmbedBean;

/**
 * Only really used to turn JSON from
 * <a href="https://leovoel.github.io/embed-visualizer/">here</a> into embeds
 */
public class EmbedHolder {

	public String content;
	public EmbedBean embed;

	private EmbedHolder() {}

}
