package us.myles_selim.starota.permissions;

import java.util.List;
import java.util.Set;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;

public abstract class PermissionHolder {

	public boolean hasPermission(GuildChannel ch, String perm) {
		return getPermissions(ch).contains(perm);
	}

	abstract public Set<String> getPermissions(GuildChannel ch);

	abstract protected String toHolderString();

	public static PermissionHolder getHolder(Guild guild, User user) {
		return new PermissionHolderMember(user.asMember(guild.getId()).block());
	}

	public static PermissionHolder getHolder(Guild guild, Permission permission) {
		return new PermissionHolderPermission(guild, permission);
	}

//	public static PermissionHolder getHolder(Guild guild, Role role) {
//		return new PermissionHolderRole(guild, role);
//	}

}
