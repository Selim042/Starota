package us.myles_selim.starota.permissions.holders;

import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.permissions.EnumPermissionModifier;

public class PermissionHolderRole extends PermissionHolder {

	private final Guild guild;
	private final Snowflake role;

	protected PermissionHolderRole(Guild guild, Snowflake role) {
		this.guild = guild;
		this.role = role;
	}

	@Override
	public Map<String, EnumPermissionModifier> getServerPermissions() {
		Map<String, EnumPermissionModifier> ret = new HashMap<>();
		ret.putAll(PermissionHolder.getNewHolderEveryone(guild).getServerPermissions());

		PermissionSet permissionSet = guild.getRoleById(role).block().getPermissions();
		for (Permission permission : permissionSet)
			ret.putAll(PermissionHolder.getNewHolderDiscord(guild, permission).getServerPermissions());

		ret.putAll(SERVER_PERMISSIONS);
		return ret;
	}

	@Override
	public Map<String, EnumPermissionModifier> getChannelPermissions(Snowflake ch) {
		Map<String, EnumPermissionModifier> ret = new HashMap<>();
		ret.putAll(getServerPermissions());
		ret.putAll(PermissionHolder.getNewHolderEveryone(guild).getChannelPermissions(ch));

		GuildChannel channel = guild.getChannelById(ch).block();
		if (channel instanceof TextChannel) {
			PermissionSet permissionSet = ((TextChannel) channel).getEffectivePermissions(role)
					.onErrorReturn(PermissionSet.none()).block();
			for (Permission permission : permissionSet)
				ret.putAll(PermissionHolder.getNewHolderDiscord(guild, permission)
						.getChannelPermissions(ch));
		}
		if (CHANNEL_PERMISSIONS.containsKey(ch))
			ret.putAll(CHANNEL_PERMISSIONS.get(ch));
		return ret;
	}

	@Override
	public String toHolderString() {
		return "R" + role.asString();
	}

}
