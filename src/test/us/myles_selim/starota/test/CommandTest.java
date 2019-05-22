package us.myles_selim.starota.test;

import sx.blah.discord.handle.obj.IChannel;
import us.myles_selim.starota.Starota;

public class CommandTest {

	@TestTarget
	public static boolean test() {
		IChannel channel = Starota.getChannel(567904084095401985L);
		try {
			new us.myles_selim.starota.commands.CommandTest().execute(new String[] { ".test" },
					channel.sendMessage(".test"), channel.getGuild(), channel);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
