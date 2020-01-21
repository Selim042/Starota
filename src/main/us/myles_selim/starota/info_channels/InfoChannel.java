package us.myles_selim.starota.info_channels;

import discord4j.core.object.entity.VoiceChannel;

public class InfoChannel {

	private String name;
	private final VoiceChannel channel;

	public InfoChannel(VoiceChannel channel) {
		this.channel = channel;
		this.name = channel.getName();
	}

	public void setName(String name) {
		if (name == null || name.equals(this.name))
			return;
		this.name = name;
		channel.edit((chSpec) -> chSpec.setName(name).setReason("Updating info channel name")).block();
	}

	public String getName() {
		return this.name;
	}

	public VoiceChannel getChannel() {
		return this.channel;
	}

}
