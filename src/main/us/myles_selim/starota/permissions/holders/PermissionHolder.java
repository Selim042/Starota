package us.myles_selim.starota.permissions.holders;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.Snowflake;
import us.myles_selim.starota.permissions.EnumPermissionModifier;
import us.myles_selim.starota.permissions.PermissionErrorException;

public abstract class PermissionHolder {

	protected final Map<String, EnumPermissionModifier> SERVER_PERMISSIONS = new ConcurrentHashMap<>();
	protected final Map<Snowflake, Map<String, EnumPermissionModifier>> CHANNEL_PERMISSIONS = new ConcurrentHashMap<>();

	public boolean hasPermission(MessageChannel ch, String perm) {
		if (ch == null) {
			Map<String, EnumPermissionModifier> serverPerms = getServerPermissions();
			EnumPermissionModifier modifier = serverPerms.get(perm);
			if (modifier == null) {
				// check perm up one level
				String upperPerm = getPermUp(perm);
				if (upperPerm == null)
					return true;
				return hasPermission(ch, upperPerm);
			} else if (modifier == EnumPermissionModifier.REVOKE)
				return false;
			return true;
		} else {
			Map<String, EnumPermissionModifier> channelPerms = getChannelPermissions(ch);
			EnumPermissionModifier modifier = channelPerms.get(perm);
			if (modifier == null) {
				// check perm up one level
				String upperPerm = getPermUp(perm);
				if (upperPerm == null)
					return true;
				return hasPermission(ch, upperPerm);
			} else if (modifier == EnumPermissionModifier.REVOKE)
				return false;
			return true;
		}
	}

	private static final String getPermUp(String perm) {
		if (perm.indexOf(".") == perm.lastIndexOf("."))
			return null;
		perm = perm.replaceAll("\\.\\*", "");
		String upperPerm = perm.substring(0, perm.lastIndexOf(".") + 1);
		return upperPerm + "*";
	}

	public abstract Map<String, EnumPermissionModifier> getServerPermissions();

	public Map<Snowflake, Map<String, EnumPermissionModifier>> getRawChannelPermissions() {
		return Collections.unmodifiableMap(CHANNEL_PERMISSIONS);
	}

	public Map<String, EnumPermissionModifier> getChannelPermissions(MessageChannel ch) {
		return getChannelPermissions(ch.getId());
	}

	public abstract Map<String, EnumPermissionModifier> getChannelPermissions(Snowflake ch);

	public void grantPermission(Snowflake channel, String permission) {
		if (channel == null)
			SERVER_PERMISSIONS.put(permission, EnumPermissionModifier.GRANT);
		else {
			Map<String, EnumPermissionModifier> perms = CHANNEL_PERMISSIONS.get(channel);
			if (perms == null) {
				perms = new ConcurrentHashMap<>();
				CHANNEL_PERMISSIONS.put(channel, perms);
			}
			perms.put(permission, EnumPermissionModifier.GRANT);
		}
		consolidatePermissions(channel);
	}

	public void revokePermission(Snowflake channel, String permission) {
		if (channel == null)
			SERVER_PERMISSIONS.put(permission, EnumPermissionModifier.REVOKE);
		else {
			Map<String, EnumPermissionModifier> perms = CHANNEL_PERMISSIONS.get(channel);
			if (perms == null) {
				perms = new ConcurrentHashMap<>();
				CHANNEL_PERMISSIONS.put(channel, perms);
			}
			perms.put(permission, EnumPermissionModifier.REVOKE);
		}
		consolidatePermissions(channel);
	}

	private void consolidatePermissions(Snowflake channel) {
		if (channel == null) {
			for (Entry<String, EnumPermissionModifier> entry : getServerPermissions().entrySet()) {

			}
		} else {
			for (Entry<String, EnumPermissionModifier> entry : getChannelPermissions(channel)
					.entrySet()) {

			}
		}
	}

	public void clearPermission(Snowflake channel, String permission) {
		if (channel == null)
			SERVER_PERMISSIONS.remove(permission);
		else {
			Map<String, EnumPermissionModifier> perms = CHANNEL_PERMISSIONS.get(channel);
			if (perms == null)
				return;
			perms.remove(permission);
		}
	}

	public abstract String toHolderString();

	private static final Map<Snowflake, Map<String, PermissionHolder>> HOLDERS = new ConcurrentHashMap<>();

	public static PermissionHolder getNewHolderOwner(Guild guild) {
		if (HOLDERS.containsKey(guild.getId())) {
			Map<String, PermissionHolder> holders = HOLDERS.get(guild.getId());
			String rep = "OWNER";
			if (holders.containsKey(rep))
				return holders.get(rep);
		}
		return new PermissionHolderOwner(guild);
	}

	public static PermissionHolder getNewHolderEveryone(Guild guild) {
		if (HOLDERS.containsKey(guild.getId())) {
			Map<String, PermissionHolder> holders = HOLDERS.get(guild.getId());
			String rep = "@everyone";
			if (holders.containsKey(rep))
				return holders.get(rep);
		}
		return new PermissionHolderEveryone(guild);
	}

	public static PermissionHolder getNewHolderDiscord(Guild guild, Permission permission) {
		if (HOLDERS.containsKey(guild.getId())) {
			Map<String, PermissionHolder> holders = HOLDERS.get(guild.getId());
			String rep = String.format("Discord.%s", permission.name());
			if (holders.containsKey(rep))
				return holders.get(rep);
		}
		return new PermissionHolderPermission(guild, permission);
	}

	public static PermissionHolder getNewHolderMember(Guild guild, Member member) {
		if (HOLDERS.containsKey(guild.getId())) {
			Map<String, PermissionHolder> holders = HOLDERS.get(guild.getId());
			String rep = String.format("U%d", member.getId().asLong());
			if (holders.containsKey(rep))
				return holders.get(rep);
		}
		return new PermissionHolderMember(guild, member);
	}

	public static PermissionHolder getNewHolderRole(Guild guild, Snowflake role) {
		if (HOLDERS.containsKey(guild.getId())) {
			Map<String, PermissionHolder> holders = HOLDERS.get(guild.getId());
			String rep = String.format("R%d", role.asLong());
			if (holders.containsKey(rep))
				return holders.get(rep);
		}
		return new PermissionHolderRole(guild, role);
	}

	public static PermissionHolder getNewHolderRole(Guild guild, Role role) {
		if (HOLDERS.containsKey(guild.getId())) {
			Map<String, PermissionHolder> holders = HOLDERS.get(guild.getId());
			String rep = String.format("R%d", role.getId().asLong());
			if (holders.containsKey(rep))
				return holders.get(rep);
		}
		return new PermissionHolderRole(guild, role.getId());
	}

	public static PermissionHolder getNewHolder(Guild guild, String rep) {
		if (HOLDERS.containsKey(guild.getId())) {
			Map<String, PermissionHolder> holders = HOLDERS.get(guild.getId());
			if (holders.containsKey(rep))
				return holders.get(rep);
		}
		PermissionHolder ret = null;
		if (rep.equals("OWNER")) { // owner permission holder
			ret = getNewHolderOwner(guild);
		} else if (rep.equals("@everyone")) { // @everyone permission holder
			ret = getNewHolderEveryone(guild);
		} else if (rep.matches("Discord\\.[A-Z_]+")) { // Discord perms
			String name = rep.substring(rep.indexOf(".") + 1);
			try {
				Permission perm = Permission.valueOf(name);
				ret = getNewHolderDiscord(guild, perm);
			} catch (IllegalArgumentException e) {
				return null;
			}
		} else if (rep.matches("U\\d{18}")) { // user permission holder
			String id = rep.substring(1);
			ret = getNewHolderMember(guild, guild.getMemberById(Snowflake.of(id)).block());
		} else if (rep.matches("R\\d{18}")) { // role permission holder
			String id = rep.substring(1);
			ret = getNewHolderRole(guild, guild.getRoleById(Snowflake.of(id)).block());
		}
		if (ret == null)
			throw new PermissionErrorException("unknown holder " + rep);
		Map<String, PermissionHolder> holders = HOLDERS.get(guild.getId());
		if (holders == null) {
			holders = new ConcurrentHashMap<>();
			HOLDERS.put(guild.getId(), holders);
		}
		holders.put(rep, ret);
		return ret;
	}

	public static void dumpPerms(Guild guild) {
		HOLDERS.remove(guild.getId());
	}

}
