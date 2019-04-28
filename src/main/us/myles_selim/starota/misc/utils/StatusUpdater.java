package us.myles_selim.starota.misc.utils;

import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.StatusType;
import us.myles_selim.starota.Starota;

public class StatusUpdater extends Thread {

	private final IDiscordClient client;
	private final List<PresenceData> presences = new ArrayList<>();
	private int statusIndex = 0;

	public StatusUpdater(IDiscordClient client) {
		super("StatusUpdater-" + client.getApplicationName());
		this.client = client;
	}

	public void addPresence(PresenceData presence) {
		if (presence != null)
			this.presences.add(presence);
	}

	@Override
	public void run() {
		while (true) {
			if (statusIndex >= this.presences.size())
				statusIndex = 0;
			PresenceData presence = this.presences.get(statusIndex++);
			this.client.changePresence(presence.getStatus(), presence.getActivity(), presence.getText());
			if (Starota.IS_DEV)
				System.out.println("updating presence: " + presence.getStatus() + ", "
						+ presence.getActivity() + ", " + presence.getText());
			try {
				Thread.sleep(300000); // 5 mins
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static class PresenceData {

		private final StatusType status;
		private final ActivityType activity;
		private final String text;

		public PresenceData(StatusType status, ActivityType activity, String text) {
			this.status = status;
			this.activity = activity;
			this.text = text;
		}

		public StatusType getStatus() {
			return this.status;
		}

		public ActivityType getActivity() {
			return this.activity;
		}

		public String getText() {
			return this.text;
		}

	}

}
