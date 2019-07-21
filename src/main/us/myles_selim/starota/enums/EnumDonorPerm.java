package us.myles_selim.starota.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.misc.utils.StarotaConstants;

public enum EnumDonorPerm {
	LUA(517172285103407104L, 2),
	HTTP(517172454783844382L, 1),
	LEADERBOARD_5(517810405041504273L, 1),
	LEADERBOARD_10(517810468702912542L, 1, LEADERBOARD_5),
	LEADERBOARD_20(517810679445716993L, 1, LEADERBOARD_5, LEADERBOARD_10),
	LEADERBOARD_100(517810720482787339L, 2, LEADERBOARD_5, LEADERBOARD_10, LEADERBOARD_20),
	WEBHOOKS(546699680369606672L, 2),
	STAROTA_MANAGEMENT(560892614426099712L);

	private static int MAX_POINTS;
	private static final Map<Snowflake, EnumDonorPerm> ROLE_MAP = new HashMap<>();

	static {
		for (EnumDonorPerm perm : values()) {
			MAX_POINTS += perm.pointsRequired;
			ROLE_MAP.put(perm.roleId, perm);
		}
	}

	public static int getMaxPoints() {
		return MAX_POINTS;
	}

	public static EnumDonorPerm getPermForRole(Role role) {
		return ROLE_MAP.get(role.getId());
	}

	private Snowflake roleId;
	private int pointsRequired;
	private EnumDonorPerm[] preReqs;

	EnumDonorPerm(long roleId) {
		this(roleId, -1, new EnumDonorPerm[0]);
	}

	EnumDonorPerm(long roleId, int pointsRequired) {
		this(roleId, pointsRequired, new EnumDonorPerm[0]);
	}

	EnumDonorPerm(long roleId, int pointsRequired, EnumDonorPerm... preReqs) {
		this.roleId = Snowflake.of(roleId);
		this.pointsRequired = pointsRequired;
		this.preReqs = preReqs;
	}

	public Snowflake getRoleId() {
		return this.roleId;
	}

	public Role getRole() {
		return Starota.getClient().getRoleById(StarotaConstants.SUPPORT_SERVER, this.roleId).block();
	}

	public int getPointsRequired() {
		return this.pointsRequired;
	}

	public EnumDonorPerm[] getPreReqs() {
		return Arrays.copyOf(this.preReqs, this.preReqs.length);
	}

}
