package us.myles_selim.starota.research;

import java.util.Date;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.EmbedBuilder;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.research.CommandSetResearchChannel.EnumResearchChannel;

public class ThreadReport extends Thread {

	// private final long serverId;
	//
	// public ThreadReport(IGuild server) {
	// this(server.getLongID());
	// }
	//
	// public ThreadReport(long serverId) {
	// this.setName("REPORT:" + serverId);
	// this.serverId = serverId;
	// }

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while (true) {
			Date date = new Date();
			if (
			// date.getDay() == 0 &&
			date.getHours() == 9)
				report();
			try {
				Thread.sleep(3600000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void report() {
		Date today = new Date();
		@SuppressWarnings("deprecation")
		int day = today.getDay();
		String dayS = (day == 0 ? "Sunday"
				: day == 1 ? "Monday"
						: day == 2 ? "Tuesday"
								: day == 3 ? "Wednesday"
										: day == 4 ? "Thursday" : day == 5 ? "Friday" : "Saturday");
		System.out.println("REPORTING");
		for (IGuild s : Starota.getClient().getGuilds()) {
			long serverId = s.getLongID();
			IChannel reportChannel = ResearchTracker.getResearchChannel(s, EnumResearchChannel.REPORT);
			reportChannel.sendMessage("Today is " + dayS + ", the day for reports.");
			List<Researcher> top = ResearchTracker.getTopPosters(serverId);
			EmbedBuilder builder = new EmbedBuilder();
			builder.appendDesc("**Top " + top.size() + " Researchers for the Week**:\n");
			for (int i = 0; i < top.size(); i++) {
				Researcher r = top.get(i);
				builder.appendDesc("**" + (i + 1) + ")** "
						+ r.getDiscordUser().getDisplayName(Starota.getGuild(serverId)) + " ("
						+ r.getPosts() + ")\n");
			}
			reportChannel.sendMessage(builder.build());
			ResearchTracker.clearPosters(s);
		}
	}

}
