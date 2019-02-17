package us.myles_selim.starota;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import us.myles_selim.starota.enums.EnumPokemon;

public class MiscUtils {

	public static String fixCharacters(String in) {
		if (in == null)
			return null;
		return in.replaceAll("’", "'").replaceAll("�", "é");
	}

	public static String getTrainerCodeString(long trainerCode) {
		String codeS = String.format("%012d", trainerCode);
		return codeS.substring(0, 4) + " " + codeS.substring(4, 8) + " " + codeS.substring(8, 12);
	}

	public static <T> boolean arrContains(T[] tt, T tv) {
		for (T t : tt)
			if (t != null && t.equals(tv))
				return true;
		return false;
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

}
