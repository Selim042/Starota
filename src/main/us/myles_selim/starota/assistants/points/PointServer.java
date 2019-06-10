package us.myles_selim.starota.assistants.points;

import java.io.File;

import sx.blah.discord.handle.obj.IGuild;
import us.myles_selim.starota.commands.settings.SettingSet;
import us.myles_selim.starota.misc.data_types.BotServer;

public class PointServer extends BotServer {

	private PointQuest<?, ?> fastQuest;
	private PointQuest<?, ?> slowQuest;

	private PointServer(IGuild server) {
		super(PointBot.CLIENT, server);
	}

	public void setFastQuest(PointQuest<?, ?> quest) {
		fastQuest = quest;
	}

	public PointQuest<?, ?> getFastQuest() {
		return fastQuest;
	}

	public void setSlowQuest(PointQuest<?, ?> quest) {
		slowQuest = quest;
	}

	public PointQuest<?, ?> getSlowQuest() {
		return slowQuest;
	}

	@Override
	protected File getOptionsFile() {
		return new File(PointBot.DATA_FOLDER, "options");
	}

	@Override
	protected SettingSet getDefaultSettings(BotServer server) {
		return new SettingSet();
	}

	@SuppressWarnings("deprecation")
	@Override
	public IGuild copy() {
		return new PointServer(getDiscordGuild());
	}

}
