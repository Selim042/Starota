package us.myles_selim.starota.commands.selim_pm;

import java.util.Collections;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.commands.CommandPing;
import us.myles_selim.starota.commands.registry.CommandHelp;
import us.myles_selim.starota.commands.registry.ICommand;
import us.myles_selim.starota.commands.registry.ICommandHandler;
import us.myles_selim.starota.commands.registry.PrimaryCommandHandler;
import us.myles_selim.starota.commands.registry.java.JavaCommand;
import us.myles_selim.starota.commands.registry.java.JavaCommandHandler;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public class SelimPMCommandHandler {

	private static PrimaryCommandHandler INSTANCE;
	private static JavaCommandHandler JAVA_HANDLER;

	private static boolean inited = false;

	public static void init() {
		if (inited && Starota.FULLY_STARTED)
			return;
		inited = true;

		INSTANCE = new PrimaryCommandHandler(Starota.getClient(), (IChannel c) -> isSelimPM(c));
		Starota.getClient().getDispatcher().registerListener(INSTANCE);
		JAVA_HANDLER = new JavaCommandHandler();
		INSTANCE.registerCommandHandler(JAVA_HANDLER);

		registerCommand(new CommandPing());

		registerCommand("Help", new CommandHelp());

		registerCommand("Admin", new CommandCaches());
		registerCommand("Admin", new CommandServerInfo());
		registerCommand("Admin", new CommandChannelInfo());
		registerCommand("Admin", new CommandOwnerMessage());
	}

	// private final List<JavaCommand> COMMANDS = new CopyOnWriteArrayList<>();
	// private final List<String> CATEGORIES = new CopyOnWriteArrayList<>();
	//

	private SelimPMCommandHandler() {}

	@SuppressWarnings("unused")
	private static void registerCommand(JavaCommand cmd) {
		registerCommand(PrimaryCommandHandler.DEFAULT_CATEGORY, cmd);
	}

	private static void registerCommand(String category, JavaCommand cmd) {
		JAVA_HANDLER.registerCommand(category, cmd);
	}

	public static boolean isSelimPM(IChannel channel) {
		if (channel instanceof IPrivateChannel && ((IPrivateChannel) channel).getRecipient()
				.getLongID() == StarotaConstants.SELIM_USER_ID)
			return true;
		return false;
	}

	protected abstract static class SelimPMCommand implements ICommand {

		private final String name;
		private String category;

		public SelimPMCommand(String name) {
			this.name = name;
		}

		@Override
		public final int compareTo(ICommand o) {
			if (this.category.equals(o.getCategory()))
				return name.compareTo(o.getName());
			return this.category.compareTo(o.getCategory());
		}

		@Override
		public final void setCategory(String category) {
			this.category = category;
		}

		@Override
		public final String getCategory() {
			return this.category;
		}

		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public final Permissions requiredUsePermission() {
			return null;
		}

		@Override
		public final IRole requiredRole(IGuild guild) {
			return null;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public List<String> getAliases() {
			return Collections.singletonList(getName());
		}

		@Override
		public String getGeneralUsage() {
			return null;
		}

		@Override
		public String getAdminUsage() {
			return null;
		}

		@Override
		public abstract void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
				throws Exception;

		@Override
		public final void setCommandHandler(ICommandHandler handler) {}

		@Override
		public final ICommandHandler getCommandHandler() {
			return JAVA_HANDLER;
		}

	}

}
