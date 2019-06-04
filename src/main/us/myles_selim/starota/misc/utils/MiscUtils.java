package us.myles_selim.starota.misc.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRegion;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import us.myles_selim.starota.Starota;
import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.enums.EnumTeam;

public class MiscUtils {

	public static String fixCharacters(String in) {
		if (in == null)
			return null;
		return in.replaceAll("’", "'").replaceAll("é", "e").replaceAll("—", "-").replaceAll("×", "x");
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

	public static boolean stringArrayContainsIgnoreCase(String[] arr, String val) {
		for (String v : arr)
			if (v == val || v.equalsIgnoreCase(val))
				return true;
		return false;
	}

	public static IRole getTeamRole(IGuild guild, EnumTeam team) {
		for (IRole r : guild.getRoles()) {
			if (team.name().equalsIgnoreCase(r.getName().replaceAll(" ", "_")))
				return r;
		}
		return null;
	}

	// TODO: update when adding new regions
	public static TimeZone getTimezone(IRegion region) {
		switch (region.getID()) {
		case "us-central":
			return TimeZone.getTimeZone("US/Central");
		case "us-east":
			return TimeZone.getTimeZone("US/Eastern");
		case "us-west":
			return TimeZone.getTimeZone("US/Pacific");
		case "sydney":
			return TimeZone.getTimeZone("Australia/Sydney");
		case "eu-central":
			return TimeZone.getTimeZone("Europe/Paris");
		case "eu-west":
			return TimeZone.getTimeZone("Europe/Istanbul");
		case "brazil":
			return TimeZone.getTimeZone("America/Belem");
		default:
			TwitterHelper.sendDirectMessage("Selim_042", "Region " + region.getID() + " not configured");
			if (Starota.IS_DEV)
				for (String id : TimeZone.getAvailableIDs())
					System.out.println("timezone id: " + id);
			return TimeZone.getDefault();
		}
	}

	public static <V> V getValueIgnoreCase(Map<String, V> map, String key) {
		for (String s : map.keySet())
			if (s.equalsIgnoreCase(key))
				return map.get(s);
		return null;
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

	public static EnumPermissionType getPermissionType(Permissions perm) {
		switch (perm) {
		case ADMINISTRATOR:
		case VIEW_AUDIT_LOG:
		case MANAGE_SERVER:
		case MANAGE_ROLES:
		case MANAGE_CHANNELS:
		case KICK:
		case BAN:
		case CHANGE_NICKNAME:
		case MANAGE_NICKNAMES:
		case MANAGE_EMOJIS:
		case VOICE_CONNECT:
		case VOICE_SPEAK:
		case VOICE_MUTE_MEMBERS:
		case VOICE_DEAFEN_MEMBERS:
		case VOICE_MOVE_MEMBERS:
		case VOICE_USE_VAD:
			return EnumPermissionType.SERVER;
		case CREATE_INVITE:
		case MANAGE_CHANNEL:
		case MANAGE_PERMISSIONS:
		case MANAGE_WEBHOOKS:
		case READ_MESSAGES:
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
