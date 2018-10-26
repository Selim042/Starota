package us.myles_selim.starota.role_management;

import sx.blah.discord.handle.obj.IRole;

public enum EnumGroup {
	SCANNER(
			335776499863126019L);

	public final long id;

	EnumGroup(long id) {
		this.id = id;
	}

	public static boolean isIncluded(IRole role) {
		for (EnumGroup g : values())
			if (g.id == role.getLongID())
				return true;
		return false;
	}

}
