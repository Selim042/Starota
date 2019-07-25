package us.myles_selim.starota.role_management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.data_types.DataTypeLong;
import us.myles_selim.starota.wrappers.StarotaServer;

public class GroupManager {

	public static final String ROLES_KEY = "assignableRoles";

	@SuppressWarnings("unchecked")
	public static boolean isGroup(StarotaServer server, Role role) {
		if (role == null || !server.hasDataKey(ROLES_KEY))
			return false;
		EBList<Long> roles = (EBList<Long>) server.getDataValue(ROLES_KEY);
		return roles.containsWrapped(role.getId().asLong());
	}

	@SuppressWarnings("unchecked")
	public static List<Role> getGroups(StarotaServer server) {
		List<Role> ret = new ArrayList<>();
		EBList<Long> roles = (EBList<Long>) server.getDataValue(ROLES_KEY);
		if (roles == null)
			return Collections.emptyList();
		for (Long l : roles.values())
			ret.add(server.getDiscordGuild().getRoleById(Snowflake.of(l)).block());
		return Collections.unmodifiableList(ret);
	}

	@SuppressWarnings("unchecked")
	public static boolean setAsGroup(StarotaServer server, Role role, boolean isGroup) {
		EBList<Long> roles;
		if (server.hasDataKey(ROLES_KEY))
			roles = (EBList<Long>) server.getDataValue(ROLES_KEY);
		else {
			roles = new EBList<>(new DataTypeLong());
			server.setDataValue(ROLES_KEY, roles);
		}
		if (isGroup)
			return roles.addWrapped(role.getId().asLong());
		else
			return roles.removeWrapped(role.getId().asLong());
	}

}
