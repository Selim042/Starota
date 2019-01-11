package us.myles_selim.starota.enums;

import sx.blah.discord.handle.obj.IRole;
import us.myles_selim.starota.Starota;

public enum EnumPatreonPerm {
	LUA(517172285103407104L),
	HTTP(517172454783844382L),
	LEADERBOARD_5(517810405041504273L),
	LEADERBOARD_10(517810468702912542L),
	LEADERBOARD_20(517810679445716993L),
	LEADERBOARD_100(517810720482787339L);

	private long roleId;

	EnumPatreonPerm(long roleId) {
		this.roleId = roleId;
	}

	public long getRoleId() {
		return this.roleId;
	}

	public IRole getRole() {
		return Starota.getClient().getRoleByID(this.roleId);
	}
}
