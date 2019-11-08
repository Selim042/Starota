package us.myles_selim.starota.commands.registry.channel_management;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.data_types.DataTypeEBList;
import us.myles_selim.ebs.data_types.DataTypeString;
import us.myles_selim.starota.wrappers.StarotaServer;

public class ChannelCommandManager {

	private static final String WHITELIST_KEY = "commandChannelWhitelist";

	@SuppressWarnings({ "unchecked" })
	public static boolean isAllowedHere(StarotaServer server, String category, MessageChannel channel) {
		if (server == null || !(channel instanceof TextChannel))
			return true;
		EBStorage stor = (EBStorage) server.getDataValue(WHITELIST_KEY);
		if (stor == null)
			return true;
		EBList<String> whitelist = stor.get(category, EBList.class);
		if (whitelist == null)
			return true;
		return whitelist.isEmpty() || whitelist.containsWrapped(channel.getId().asString());
	}

	@SuppressWarnings("unchecked")
	public static void addWhitelist(StarotaServer server, String category, MessageChannel channel) {
		if (!(channel instanceof TextChannel))
			return;
		EBStorage stor;
		if (server.hasDataKey(WHITELIST_KEY))
			stor = (EBStorage) server.getDataValue(WHITELIST_KEY);
		else {
			stor = new EBStorage().registerPrimitives();
			server.setDataValue(WHITELIST_KEY, stor);
		}
		EBList<String> whitelist;
		if (stor.containsKey(category))
			whitelist = stor.get(category, EBList.class);
		else {
			whitelist = new EBList<>(new DataTypeString());
			if (!stor.acceptsValue(whitelist))
				stor.registerType(new DataTypeEBList());
			stor.set(category, whitelist);
		}
		if (!whitelist.containsWrapped(channel.getId().asString()))
			whitelist.addWrapped(channel.getId().asString());
	}

	@SuppressWarnings("unchecked")
	public static boolean removeWhitelist(StarotaServer server, String category,
			MessageChannel channel) {
		if (!(channel instanceof TextChannel))
			return false;
		EBStorage stor;
		if (server.hasDataKey(WHITELIST_KEY))
			stor = (EBStorage) server.getDataValue(WHITELIST_KEY);
		else
			return false;
		EBList<String> whitelist;
		if (stor.containsKey(category))
			whitelist = stor.get(category, EBList.class);
		else
			return false;
		return whitelist.removeWrapped(channel.getId().asString());
	}

	@SuppressWarnings("unchecked")
	public static List<TextChannel> getWhitelist(StarotaServer server, String category) {
		EBStorage stor;
		if (server.hasDataKey(WHITELIST_KEY))
			stor = (EBStorage) server.getDataValue(WHITELIST_KEY);
		else
			return Collections.emptyList();
		EBList<String> whitelist = null;
		if (stor.containsKey(category))
			whitelist = stor.get(category, EBList.class);
		if (whitelist == null)
			return Collections.emptyList();
		List<TextChannel> ret = new LinkedList<>();
		for (String c : whitelist.values()) {
			GuildChannel ch = server.getDiscordGuild().getChannelById(Snowflake.of(c)).block();
			if (ch instanceof TextChannel)
				ret.add((TextChannel) ch);
		}
		return Collections.unmodifiableList(ret);
	}

}