package us.myles_selim.starota.commands.registry.channel_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.ebs.EBStorage;

public class ChannelCommandManager {

	private static final Map<Long, Map<String, List<Long>>> WHITELIST_CHANNELS = new HashMap<>();
	private static boolean inited = false;

	public static void init() {
		if (inited)
			return;
		inited = true;

		EBStorage storage = new EBStorage().registerPrimitives();
		for (Entry<Long, Map<String, List<Long>>> e : WHITELIST_CHANNELS.entrySet()) {
			EBStorage nested = new EBStorage().registerPrimitives();
			for (Entry<String, List<Long>> e1 : e.getValue().entrySet()) {
				List<Long> list = e1.getValue();
				long[] arr = new long[list.size()];
				for (int i = 0; i < list.size() && i < arr.length; i++)
					arr[i] = list.get(i);
				nested.set(e1.getKey(), arr);
			}
			storage.set(Long.toString(e.getKey()), nested);
		}
	}

	public static void flush() {}

	public static boolean isAllowedHere(IGuild server, String category, IChannel channel) {
		if (!WHITELIST_CHANNELS.containsKey(server.getLongID()))
			return true;
		Map<String, List<Long>> whitelist = WHITELIST_CHANNELS.get(server.getLongID());
		if (!whitelist.containsKey(category))
			return true;
		return whitelist.get(category).contains(channel.getLongID());
	}

	public static void addWhitelist(IGuild server, String category, IChannel channel) {
		if (!WHITELIST_CHANNELS.containsKey(server.getLongID()))
			WHITELIST_CHANNELS.put(server.getLongID(), new HashMap<>());
		Map<String, List<Long>> whitelist = WHITELIST_CHANNELS.get(server.getLongID());
		if (!whitelist.containsKey(category))
			whitelist.put(category, new ArrayList<>());
		List<Long> channels = whitelist.get(category);
		channels.add(channel.getLongID());
		flush();
	}

}
