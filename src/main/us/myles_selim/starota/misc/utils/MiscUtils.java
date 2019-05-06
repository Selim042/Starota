package us.myles_selim.starota.misc.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.TimeZone;

import discord4j.core.DiscordClient;
import discord4j.core.object.Region;
import discord4j.core.object.entity.Category;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.GuildChannel;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Flux;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumTeam;

public class MiscUtils {

	public static String fixCharacters(String in) {
		if (in == null)
			return null;
		return in.replaceAll("’", "'").replaceAll("�", "é").replaceAll("Ã©", "é");
	}

	public static String getTrainerCodeString(long trainerCode) {
		String codeS = String.format("%012d", trainerCode);
		return codeS.substring(0, 4) + " " + codeS.substring(4, 8) + " " + codeS.substring(8, 12);
	}

	public static boolean arrContains(int[] tt, int tv) {
		for (int t : tt)
			if (t == tv)
				return true;
		return false;
	}

	public static <T> boolean arrContains(T[] tt, T tv) {
		for (T t : tt)
			if (t != null && t.equals(tv))
				return true;
		return false;
	}

	public static Role getTeamRole(Guild guild, EnumTeam team) {
		for (Role r : guild.getRoles().collectList().block()) {
			if (team.name().equalsIgnoreCase(r.getName().replaceAll(" ", "_")))
				return r;
		}
		return null;
	}

	// TODO: update when adding new regions
	public static TimeZone getTimezone(Region region) {
		switch (region.getId()) {
		case "us-central":
			return TimeZone.getTimeZone("US/Central");
		case "us-east":
			return TimeZone.getTimeZone("US/Eastern");
		case "us-west":
			return TimeZone.getTimeZone("US/Pacific");
		default:
			TwitterHelper.sendDirectMessage("Selim_042", "Region " + region.getId() + " not configured");
			if (Starota.IS_DEV)
				for (String id : TimeZone.getAvailableIDs())
					System.out.println("timezone id: " + id);
			return TimeZone.getDefault();
		}
	}

	public static <T> void forEach(Flux<T> flux, ForEachCaller<T> caller) {
		flux.all((t) -> {
			caller.call(t);
			return true;
		});
	}

	public static interface ForEachCaller<T> {

		public void call(T t);

	}

	public static List<GuildChannel> getChannelByName(Guild guild, String name) {
		return guild.getChannels().filter((c) -> c.getName().equalsIgnoreCase(name)).collectList()
				.block();
	}

	public static List<Member> getMemberByName(Guild guild, String name) {
		return guild.getMembers().filter((m) -> m.getUsername().equalsIgnoreCase(name)
				|| m.getDisplayName().equalsIgnoreCase(name)).collectList().block();
	}

	public static List<Member> getMemberByRole(Guild guild, Snowflake id) {
		return guild.getMembers().filter((m) -> m.getRoleIds().contains(id)).collectList().block();
	}

	public static List<User> getUserByName(DiscordClient client, String name) {
		return client.getUsers().filter((u) -> u.getUsername().equalsIgnoreCase(name)).collectList()
				.block();
	}

	public static List<Role> getRoleByName(Guild guild, String name) {
		return guild.getRoles().filter((r) -> r.getName().equalsIgnoreCase(name)).collectList().block();
	}

	public static List<Category> getCategoryByName(Guild guild, String name) {
		return guild.getChannels().ofType(Category.class)
				.filter((c) -> c.getName().equalsIgnoreCase(name)).collectList().block();
	}

	public static <S extends P, P> List<P> listSubClassToParent(List<S> in) {
		List<P> ret = new ArrayList<>();
		for (S s : in)
			ret.add((P) s);
		return ret;
	}

	public static int generatePermissionNumber(EnumSet<Permission> permissions) {
		if (permissions == null)
			permissions = EnumSet.noneOf(Permission.class);
		int number = 0;
		for (Permission permission : permissions)
			number |= (1 << permission.getValue());
		return number;
	}

	public static int generatePermissionNumber(PermissionSet permissions) {
		if (permissions == null)
			permissions = PermissionSet.none();
		int number = 0;
		for (Permission permission : permissions)
			number |= (1 << permission.getValue());
		return number;
	}

	public static EnumPokemon[] getSuggestedPokemon(String input, int count) {
		List<DistancedEnum<EnumPokemon>> suggestions = new ArrayList<>();
		for (EnumPokemon e : EnumPokemon.values()) {
			suggestions.add(new DistancedEnum<>(calculateDistance(e.name(), input), e));
			suggestions.add(new DistancedEnum<>(calculateDistance(e.toString(), input), e));
		}
		suggestions.sort(null);
		EnumPokemon[] out = new EnumPokemon[count];
		int index = 0;
		for (DistancedEnum<EnumPokemon> e : suggestions) {
			if (index >= count)
				break;
			EnumPokemon sug = e.en;
			if (!MiscUtils.arrContains(out, sug) && !sug.isAvailable())
				out[index++] = sug;
		}
		return out;
	}

	public static <E extends Enum<E>> E[] getSuggestionsEnum(Class<E> enumClass, String input,
			int count) {
		if (!enumClass.isEnum())
			throw new IllegalArgumentException("enumClass must be a class of an enum");
		List<DistancedEnum<E>> suggestions = new ArrayList<>();
		for (E e : enumClass.getEnumConstants()) {
			suggestions.add(new DistancedEnum<>(calculateDistance(e.name(), input), e));
			suggestions.add(new DistancedEnum<>(calculateDistance(e.toString(), input), e));
		}
		suggestions.sort(null);
		@SuppressWarnings("unchecked")
		E[] out = (E[]) Array.newInstance(enumClass, count);
		int index = 0;
		for (DistancedEnum<E> e : suggestions) {
			if (index >= count)
				break;
			E sug = e.en;
			if (!MiscUtils.arrContains(out, sug))
				out[index++] = sug;
		}
		return out;
	}

	public static String[] getSuggestions(String[] possibleStrings, String input, int count) {
		List<DistancedString> suggestions = new ArrayList<>();
		for (String str : possibleStrings) {
			DistancedString dc = new DistancedString(calculateDistance(str, input), str);
			if (!suggestions.contains(dc))
				suggestions.add(dc);
		}
		suggestions.sort(null);
		String[] out = new String[count];
		for (int i = 0; i < count; i++)
			out[i] = suggestions.get(i).str;
		return out;
	}

	private static class DistancedEnum<E> implements Comparable<DistancedEnum<E>> {

		public int dist;
		public E en;

		public DistancedEnum(int dist, E en) {
			this.dist = dist;
			this.en = en;
		}

		@Override
		public int compareTo(DistancedEnum<E> o) {
			return Integer.compare(dist, o.dist);
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof DistancedEnum))
				return false;
			return en.equals(((DistancedEnum<E>) o).en);
		}
	}

	private static class DistancedString implements Comparable<DistancedString> {

		public int dist;
		public String str;

		public DistancedString(int dist, String str) {
			this.dist = dist;
			this.str = str;
		}

		@Override
		public int compareTo(DistancedString o) {
			return Integer.compare(dist, o.dist);
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof DistancedString))
				return false;
			return str.equals(((DistancedString) o).str);
		}
	}

	private static int calculateDistance(String x, String y) {
		int[][] dp = new int[x.length() + 1][y.length() + 1];
		for (int i = 0; i <= x.length(); i++) {
			for (int j = 0; j <= y.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					dp[i][j] = min(
							dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
							dp[i - 1][j] + 1, dp[i][j - 1] + 1);
				}
			}
		}
		return dp[x.length()][y.length()];
	}

	private static int costOfSubstitution(char a, char b) {
		return a == b ? 0 : 1;
	}

	private static int min(int... numbers) {
		return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
	}

	public static EnumPermissionType getPermissionType(Permission perm) {
		switch (perm) {
		case ADMINISTRATOR:
		case VIEW_AUDIT_LOG:
		case MANAGE_GUILD:
		case MANAGE_ROLES:
		case MANAGE_CHANNELS:
		case KICK_MEMBERS:
		case BAN_MEMBERS:
		case CHANGE_NICKNAME:
		case MANAGE_NICKNAMES:
		case MANAGE_EMOJIS:
		case CONNECT:
		case SPEAK:
		case MUTE_MEMBERS:
		case DEAFEN_MEMBERS:
		case MOVE_MEMBERS:
		case USE_VAD:
			return EnumPermissionType.SERVER;
		case CREATE_INSTANT_INVITE:
			// TODO: Figure out what happened to these
			// case MANAGE_CHANNEL:
			// case MANAGE_PERMISSIONS:
		case MANAGE_WEBHOOKS:
		case VIEW_CHANNEL:
		case SEND_MESSAGES:
		case SEND_TTS_MESSAGES:
		case MANAGE_MESSAGES:
		case EMBED_LINKS:
		case ATTACH_FILES:
		case READ_MESSAGE_HISTORY:
		case MENTION_EVERYONE:
		case USE_EXTERNAL_EMOJIS:
		case ADD_REACTIONS:
			return EnumPermissionType.EITHER;
		// return EnumPermissionType.CHANNEL;
		default:
			throw new IllegalArgumentException("permission " + perm + " not assinged");
		}
	}

	private static enum EnumPermissionType {
		CHANNEL,
		SERVER,
		EITHER;
	}

}
