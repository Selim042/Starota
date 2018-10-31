package us.myles_selim.starota.commands.registry.channel_management;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.EBStorage;
import us.myles_selim.ebs.data_types.DataTypeEBList;
import us.myles_selim.ebs.data_types.DataTypeString;
import us.myles_selim.starota.ServerOptions;

public class ChannelCommandManager {

	private static final String WHITELIST_KEY = "commandChannelWhitelist";

	@SuppressWarnings({ "unchecked" })
	public static boolean isAllowedHere(IGuild server, String category, IChannel channel) {
		EBStorage stor = (EBStorage) ServerOptions.getValue(server, WHITELIST_KEY);
		if (stor == null)
			return true;
		EBList<String> whitelist = stor.get(category, EBList.class);
		if (whitelist == null)
			return true;
		return whitelist.isEmpty() || whitelist.containsWrapped(channel.getStringID());
	}

	@SuppressWarnings("unchecked")
	public static void addWhitelist(IGuild server, String category, IChannel channel) {
		EBStorage stor;
		if (ServerOptions.hasKey(server, WHITELIST_KEY))
			stor = (EBStorage) ServerOptions.getValue(server, WHITELIST_KEY);
		else {
			stor = new EBStorage().registerPrimitives();
			ServerOptions.setValue(server, WHITELIST_KEY, stor);
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
		if (!whitelist.containsWrapped(channel.getStringID()))
			whitelist.addWrapped(channel.getStringID());
		ServerOptions.flush(server);
	}

	@SuppressWarnings("unchecked")
	public static boolean removeWhitelist(IGuild server, String category, IChannel channel) {
		EBStorage stor;
		if (ServerOptions.hasKey(server, WHITELIST_KEY))
			stor = (EBStorage) ServerOptions.getValue(server, WHITELIST_KEY);
		else
			return false;
		EBList<String> whitelist;
		if (stor.containsKey(category))
			whitelist = stor.get(category, EBList.class);
		else
			return false;
		boolean status = whitelist.removeWrapped(channel.getStringID());
		ServerOptions.flush(server);
		return status;
	}

	@SuppressWarnings("unchecked")
	public static List<IChannel> getWhitelist(IGuild server, String category) {
		EBStorage stor;
		if (ServerOptions.hasKey(server, WHITELIST_KEY))
			stor = (EBStorage) ServerOptions.getValue(server, WHITELIST_KEY);
		else
			return Collections.emptyList();
		EBList<String> whitelist = null;
		if (stor.containsKey(category))
			whitelist = stor.get(category, EBList.class);
		if (whitelist == null)
			return Collections.emptyList();
		List<IChannel> ret = new LinkedList<>();
		for (String c : whitelist.values())
			ret.add(server.getChannelByID(Long.parseLong(c)));
		return Collections.unmodifiableList(ret);
	}

}