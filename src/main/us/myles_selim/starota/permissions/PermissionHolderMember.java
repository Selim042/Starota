package us.myles_selim.starota.permissions;

import java.util.HashSet;
import java.util.Set;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Permission;

public class PermissionHolderMember extends PermissionHolder {

	private final Guild guild;
	private final Member member;

	protected PermissionHolderMember(Member member) {
		this.guild = member.getGuild().block();
		this.member = member;
	}

	@Override
	public Set<String> getPermissions(GuildChannel ch) {
		Set<String> ret = new HashSet<>();
		// get specific user's perms
		// actually do it
		// get user's role perms
//		for (Set<String> perms : member.getRoles().map((r) -> {
//			return PermissionHolder.getHolder(guild, r).getPermissions(ch);
//		}).collectList().block())
//			ret.addAll(perms);
//		// get user's Discord perm perms
//		for (Permission p : ch.getEffectivePermissions(member.getId()).block())
//			ret.addAll(PermissionHolder.getHolder(guild, p).getPermissions(ch));
		return ret;
	}

	@Override
	protected String toHolderString() {
		return "U" + member.getId().toString();
	}

}
