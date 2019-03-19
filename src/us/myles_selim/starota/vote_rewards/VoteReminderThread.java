package us.myles_selim.starota.vote_rewards;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimerTask;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.util.RequestBuffer;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumDonorPerm;
import us.myles_selim.starota.wrappers.StarotaServer;

public class VoteReminderThread extends TimerTask {

	private static final Map<Long, Long> VOTE_PERK_CUTOFF = new HashMap<>();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM d");
	private static final String WARNING_TEMPLATE = "Your server %1$s is over your vote point limit. "
			+ "The server has earned %2$d points, but is using %3$d. "
			+ "On %4$sth, (midnight GMT) if your vote allocation is still above the number of votes "
			+ "you have earned, all perks will be disabled.";
	private static final String DISABLED_TEMPLATE = "Your server %1$s is still over your vote point limit and all vote perks have been disabled.";

	@Override
	public void run() {
		List<Long> toRemove = new ArrayList<>();
		long currentTime = System.currentTimeMillis();
		for (Entry<Long, Long> e : VOTE_PERK_CUTOFF.entrySet()) {
			if (currentTime < e.getValue())
				continue;
			IGuild guild = Starota.getGuild(e.getKey());
			StarotaServer server = StarotaServer.getServer(guild);
			int earned = server.getEarnedVotePoints();
			int used = server.getUsedVotePoints();
			if (earned < used) {
				for (EnumDonorPerm p : EnumDonorPerm.values())
					server.removeVoteReward(p);
				IPrivateChannel pm = guild.getOwner().getOrCreatePMChannel();
				RequestBuffer.request(() -> pm
						.sendMessage(String.format(DISABLED_TEMPLATE, guild.getName(), earned, used)));
			}
			toRemove.add(e.getKey());
		}
		for (long l : toRemove)
			VOTE_PERK_CUTOFF.remove(l);
		for (IGuild g : Starota.getClient().getGuilds()) {
			StarotaServer server = StarotaServer.getServer(g);
			int earned = server.getEarnedVotePoints();
			int used = server.getUsedVotePoints();
			if (earned < used) {
				// plus 3 days
				long cutoff = currentTime + 259200000;
				System.out.println(new Date(cutoff));
				// minus 0.5 days for a bit of wiggle-room
				VOTE_PERK_CUTOFF.put(g.getLongID(), (long) (cutoff - 4.32e+7));
				IPrivateChannel pm = g.getOwner().getOrCreatePMChannel();
				RequestBuffer.request(() -> pm.sendMessage(String.format(WARNING_TEMPLATE, g.getName(),
						earned, used, DATE_FORMAT.format(cutoff))));
			}
		}
	}

}
