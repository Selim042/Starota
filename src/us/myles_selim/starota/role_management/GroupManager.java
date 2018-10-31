package us.myles_selim.starota.role_management;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import us.myles_selim.ebs.EBList;
import us.myles_selim.ebs.data_types.DataTypeLong;
import us.myles_selim.starota.ServerOptions;

public class GroupManager {

	public static final String ROLES_KEY = "assignableRoles";

	@SuppressWarnings("unchecked")
	public static boolean isGroup(IGuild server, IRole role) {
		if (role == null || !ServerOptions.hasKey(server, ROLES_KEY))
			return false;
		EBList<Long> roles = (EBList<Long>) ServerOptions.getValue(server, ROLES_KEY);
		return roles.containsWrapped(role.getLongID());
	}

	@SuppressWarnings("unchecked")
	public static List<IRole> getGroups(IGuild server) {
		List<IRole> ret = new ArrayList<>();
		EBList<Long> roles = (EBList<Long>) ServerOptions.getValue(server, ROLES_KEY);
		if (roles == null)
			return Collections.emptyList();
		for (Long l : roles.values())
			ret.add(server.getRoleByID(l));
		return Collections.unmodifiableList(ret);
	}

	@SuppressWarnings("unchecked")
	public static void setAsGroup(IGuild server, IRole role, boolean isGroup) {
		EBList<Long> roles;
		if (ServerOptions.hasKey(server, ROLES_KEY))
			roles = (EBList<Long>) ServerOptions.getValue(server, ROLES_KEY);
		else {
			roles = new EBList<>(new DataTypeLong());
			ServerOptions.setValue(server, ROLES_KEY, roles);
		}
		if (isGroup)
			roles.addWrapped(role.getLongID());
		else
			roles.removeWrapped(role.getLongID());
	}

}
