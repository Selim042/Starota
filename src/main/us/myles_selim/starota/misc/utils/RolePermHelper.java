package us.myles_selim.starota.misc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.User;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumDonorPerm;

public class RolePermHelper {

	public static boolean canUseLua(Guild server) {
		if (server == null)
			return false;
		return getDonorPerms(server).contains(EnumDonorPerm.LUA);
	}

	public static int getMaxLeaderboards(Guild server) {
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

	public static Role getDonorRole(User user) {
		Guild supportServer = Starota.getSupportServer();
		if (!supportServer.getMembers().collectList().block().contains(user))
			return null;
		List<Role> supportRoles = supportServer.getRoles().collectList().block();
		List<Role> patronRoles = new ArrayList<>();
		boolean inRange = false;
		for (Role r : supportRoles) {
			if (r.getName().equals("MARKER")) {
				inRange = !inRange;
				continue;
			}
			if (inRange)
				patronRoles.add(r);
		}
		patronRoles.retainAll(
				user.asMember(supportServer.getId()).block().getRoles().collectList().block());
		if (patronRoles.size() > 0)
			return patronRoles.get(0);
		return null;
	}

	public static List<EnumDonorPerm> getDonorPerms(Guild server) {
		if (server == null)
			return Collections.emptyList();
		Member owner = server.getOwner().block().asMember(StarotaConstants.SUPPORT_SERVER).block();
		Guild supportServer = Starota.getSupportServer();
		if (!supportServer.getMembers().collectList().block().contains(owner))
			return Collections.emptyList();
		if (owner.getId().equals(StarotaConstants.SELIM_USER_ID))
			return Arrays.asList(EnumDonorPerm.values());
		List<EnumDonorPerm> perms = new ArrayList<>();
		List<Role> roles = owner.getRoles().collectList().block();
		for (EnumDonorPerm p : EnumDonorPerm.values())
			if (roles.contains(p.getRole()))
				perms.add(p);
		return Collections.unmodifiableList(perms);
	}

}
