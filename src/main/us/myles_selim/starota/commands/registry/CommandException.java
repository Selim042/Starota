package us.myles_selim.starota.commands.registry;

import us.myles_selim.starota.misc.data_types.BotServer;

public class CommandException extends RuntimeException {

	private static final long serialVersionUID = -205637253059375235L;

	private ICommand cmd;
	private BotServer server;
	private String[] params;

	public CommandException(ICommand cmd, BotServer server, String... params) {
		this.cmd = cmd;
		this.server = server;
		this.params = params;
	}

	public ICommand getCommand() {
		return this.cmd;
	}

	public BotServer getServer() {
		return this.server;
	}

	public String[] getParams() {
		return this.params;
	}

}
