package us.myles_selim.starota.misc.data_types;

import java.util.function.Consumer;

import discord4j.core.spec.EmbedCreateSpec;

/**
 * Only really used to turn JSON from
 * <a href="https://leovoel.github.io/embed-visualizer/">here</a> into embeds
 */
public class EmbedHolder {

	public String content;
	public Consumer<? super EmbedCreateSpec> embed;

	public EmbedHolder() {}

	public EmbedHolder(String content, Consumer<? super EmbedCreateSpec> embed) {
		this.content = content;
		this.embed = embed;
	}

}
