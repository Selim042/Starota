package us.myles_selim.starota.permissions.holders;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.permissions.EnumPermissionModifier;

public class PermissionHolderMember extends PermissionHolder {

	private final Guild guild;
	private final Member member;

	protected PermissionHolderMember(Guild guild, Member member) {
		this.guild = guild;
		this.member = member;
	}

	@Override
	public Map<String, EnumPermissionModifier> getServerPermissions() {
		Map<String, EnumPermissionModifier> ret = new HashMap<>();
		ret.putAll(PermissionHolder.getNewHolderEveryone(guild).getServerPermissions());

		PermissionSet permissionSet = guild.getMemberById(member.getId()).block().getBasePermissions()
				.onErrorReturn(PermissionSet.none()).block();
		for (Permission permission : permissionSet)
			ret.putAll(PermissionHolder.getNewHolderDiscord(guild, permission).getServerPermissions());

		for (Snowflake role : member.getRoleIds())
			ret.putAll(PermissionHolder.getNewHolderRole(guild, role).getServerPermissions());

		ret.putAll(SERVER_PERMISSIONS);

		// if this member is owner, add owner perms
		if (guild.getOwnerId().equals(member.getId()))
			ret.putAll(PermissionHolder.getNewHolderOwner(guild).getServerPermissions());
		return ret;
	}

	@Override
	public Map<String, EnumPermissionModifier> getChannelPermissions(Snowflake ch) {
		Map<String, EnumPermissionModifier> ret = new HashMap<>();
		ret.putAll(getServerPermissions());
		ret.putAll(PermissionHolder.getNewHolderEveryone(guild).getChannelPermissions(ch));

		GuildChannel channel = guild.getChannelById(ch).block();
		if (channel instanceof TextChannel) {
			PermissionSet permissionSet = ((TextChannel) channel).getEffectivePermissions(member.getId())
					.onErrorReturn(PermissionSet.none()).block();
			for (Permission permission : permissionSet)
				ret.putAll(PermissionHolder.getNewHolderDiscord(guild, permission)
						.getChannelPermissions(ch));
		}

		for (Snowflake role : member.getRoleIds())
			ret.putAll(PermissionHolder.getNewHolderRole(guild, role).getChannelPermissions(ch));
		if (CHANNEL_PERMISSIONS.containsKey(ch))
			ret.putAll(CHANNEL_PERMISSIONS.get(ch));

		// if this member is owner, add owner perms
		if (guild.getOwnerId().equals(member.getId()))
			ret.putAll(PermissionHolder.getNewHolderOwner(guild).getChannelPermissions(ch));
		return ret;
	}

	@Override
	public String toHolderString() {
		return "U" + member.getId().asString();
	}

}
