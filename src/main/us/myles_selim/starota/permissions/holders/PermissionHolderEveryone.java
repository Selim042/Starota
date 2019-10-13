package us.myles_selim.starota.permissions.holders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.permissions.EnumPermissionModifier;

public class PermissionHolderEveryone extends PermissionHolder {

	private final Guild guild;

	protected PermissionHolderEveryone(Guild guild) {
		this.guild = guild;
	}

	@Override
	public Map<String, EnumPermissionModifier> getServerPermissions() {
		return Collections.unmodifiableMap(SERVER_PERMISSIONS);
	}

	@Override
	public Map<String, EnumPermissionModifier> getChannelPermissions(Snowflake ch) {
		Map<String, EnumPermissionModifier> ret = new HashMap<>();
		ret.putAll(getServerPermissions());
		if (CHANNEL_PERMISSIONS.containsKey(ch))
			ret.putAll(CHANNEL_PERMISSIONS.get(ch));
		return Collections.unmodifiableMap(ret);
	}

	@Override
	public String toHolderString() {
		return "@everyone";
	}

}
