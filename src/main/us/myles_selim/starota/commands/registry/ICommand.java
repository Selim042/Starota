package us.myles_selim.starota.commands.registry;

import java.util.EnumSet;
import java.util.List;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;

public interface ICommand extends Comparable<ICommand> {

	void setCategory(String category);

	public String getCategory();

	public String getDescription();

	public Permissions requiredUsePermission();

	public EnumSet<Permissions> getCommandPermissions();

	/**
	 * @deprecated Override, don't call. Use
	 *             {@link ICommand#hasRequiredRole(IGuild, IUser)} instead.
	 */
	@Deprecated
	public IRole requiredRole(IGuild guild);

	public default boolean hasRequiredRole(IGuild guild, IUser user) {
		IRole reqRole = requiredRole(guild);
		return reqRole == null || guild.getRolesForUser(user).contains(reqRole);
	}

	/**
	 * @deprecated Override, don't call. Use
	 *             {@link ICommand#isRequiredChannel(IGuild, IUser)} instead.
	 */
	@Deprecated
	public IChannel requiredChannel(IGuild guild);

	public default boolean isRequiredChannel(IGuild guild, IChannel ch) {
		IChannel reqCh = requiredChannel(guild);
		return reqCh == null || requiredChannel(guild).equals(ch);
	}

	public String getName();

	public List<String> getAliases();

	public String getGeneralUsage();

	public String getAdminUsage();

	public void execute(String[] args, IMessage message, IGuild guild, IChannel channel)
			throws Exception;

	public void setCommandHandler(ICommandHandler handler);

	public ICommandHandler getCommandHandler();

}
