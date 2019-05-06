package us.myles_selim.starota.commands.registry;

import java.util.List;

import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;

public interface ICommand extends Comparable<ICommand> {

	void setCategory(String category);

	public String getCategory();

	public String getDescription();

	public Permission requiredUsePermission();

	public PermissionSet getCommandPermission();

	/**
	 * @deprecated Override, don't call. Use
	 *             {@link ICommand#hasRequiredRole(Guild, User)} instead.
	 */
	@Deprecated
	public Role requiredRole(Guild guild);

	public default boolean hasRequiredRole(Guild guild, Member member) {
		Role reqRole = requiredRole(guild);
		return reqRole == null || member.getRoles().collectList().block().contains(reqRole);
	}

	/**
	 * @deprecated Override, don't call. Use
	 *             {@link ICommand#isRequiredChannel(Guild, User)} instead.
	 */
	@Deprecated
	public Channel requiredChannel(Guild guild);

	public default boolean isRequiredChannel(Guild guild, Channel ch) {
		Channel reqCh = requiredChannel(guild);
		return reqCh == null || requiredChannel(guild).equals(ch);
	}

	public String getName();

	public List<String> getAliases();

	public String getGeneralUsage();

	public String getAdminUsage();

	public void execute(String[] args, Message message, Guild guild, TextChannel channel)
			throws Exception;

	public void setCommandHandler(ICommandHandler handler);

	public ICommandHandler getCommandHandler();

}
