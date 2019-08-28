package us.myles_selim.starota.permissions;

import java.util.Set;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.util.Permission;

public class PermissionHolderPermission extends PermissionHolder {

	private final Guild guild;
	private final Permission perm;

	protected PermissionHolderPermission(Guild guild, Permission perm) {
		this.guild = guild;
		this.perm = perm;
	}

	@Override
	protected String toHolderString() {
		return "Discord." + perm.name();
	}

	@Override
	public Set<String> getPermissions(GuildChannel ch) {
		return null;
	}

}
