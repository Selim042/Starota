package us.myles_selim.starota.commands.registry;

import java.util.List;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;

public interface ICommand extends Comparable<ICommand> {

	public default String getStarotaPermission() {
		StringBuilder builder = new StringBuilder("command.");
		if (getCategory() != null)
			builder.append(getCategory() + ".");
		builder.append(getName());
		return builder.toString();
	}

	void setCategory(String category);

	public String getCategory();

	public String getDescription();

	public Permission requiredUsePermission();

	public PermissionSet getCommandPermission();

	/**
	 * @deprecated Override, don't call. Use
	 *             {@link ICommand#hasRequiredRole(Guild, Member)} instead.
	 */
	@Deprecated
	public Role requiredRole(Guild guild);

	public default boolean hasRequiredRole(Guild guild, User user) {
		if (guild == null || user == null)
			return true;
		Member member = user.asMember(guild.getId()).block();
		if (member == null)
			return false;
		Role reqRole = requiredRole(guild);
		if (reqRole == null)
			return true;
		return member.getRoleIds().contains(reqRole.getId());
	}

	/**
	 * @deprecated Override, don't call. Use
	 *             {@link ICommand#isRequiredChannel(Guild, TextChannel)}
	 *             instead.
	 */
	@Deprecated
	public TextChannel requiredChannel(Guild guild);

	public default boolean isRequiredChannel(Guild guild, MessageChannel ch) {
		TextChannel reqCh = requiredChannel(guild);
		return reqCh == null || reqCh.equals(ch);
	}

	public String getName();

	public List<String> getAliases();

	public String getGeneralUsage();

	public String getAdminUsage();

	public void execute(String[] args, Message message, Guild guild, MessageChannel channel)
			throws CommandException;

	public void setCommandHandler(ICommandHandler handler);

	public ICommandHandler getCommandHandler();

}
