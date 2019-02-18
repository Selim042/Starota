package us.myles_selim.starota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import us.myles_selim.starota.enums.EnumDonorPerm;

public class RolePermHelper {

	public static boolean canUseLua(IGuild server) {
		if (server == null)
			return false;
		return getDonorPerms(server).contains(EnumDonorPerm.LUA);
	}

	public static int getMaxLeaderboards(IGuild server) {
		int max = 3;
		for (EnumDonorPerm p : RolePermHelper.getDonorPerms(server)) {
			switch (p) {
			case LEADERBOARD_5:
				max = 5;
				break;
			case LEADERBOARD_10:
				max = 10;
				break;
			case LEADERBOARD_20:
				max = 20;
				break;
			case LEADERBOARD_100:
				max = 100;
				break;
			default:
				break;
			}
		}
		return max;
	}

	public static IRole getDonorRole(IUser user) {
		IGuild supportServer = Starota.getSupportServer();
		if (!supportServer.getUsers().contains(user))
			return null;
		List<IRole> supportRoles = supportServer.getRoles();
		List<IRole> patronRoles = new ArrayList<>();
		boolean inRange = false;
		for (IRole r : supportRoles) {
			if (r.getName().equals("MARKER")) {
				inRange = !inRange;
				continue;
			}
			if (inRange)
				patronRoles.add(r);
		}
		patronRoles.retainAll(user.getRolesForGuild(supportServer));
		if (patronRoles.size() > 0)
			return patronRoles.get(0);
		return null;
	}

	public static List<EnumDonorPerm> getDonorPerms(IGuild server) {
		if (server == null)
			return Collections.emptyList();
		IUser owner = server.getOwner();
		IGuild supportServer = Starota.getSupportServer();
		if (!supportServer.getUsers().contains(owner))
			return Collections.emptyList();
		if (owner.getLongID() == Starota.SELIM_USER_ID) {
			System.out.println("b);eh");
			return Arrays.asList(EnumDonorPerm.values());
		}
		List<EnumDonorPerm> perms = new ArrayList<>();
		List<IRole> roles = owner.getRolesForGuild(supportServer);
		for (EnumDonorPerm p : EnumDonorPerm.values())
			if (roles.contains(p.getRole()))
				perms.add(p);
		return Collections.unmodifiableList(perms);
	}

}
