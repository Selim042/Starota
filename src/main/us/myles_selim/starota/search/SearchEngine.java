package us.myles_selim.starota.search;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import us.myles_selim.starota.enums.EnumPokemon;
import us.myles_selim.starota.search.SearchOperator.DefaultSearchOperatorWrapper;
import us.myles_selim.starota.search.pokemon.PokemonOperators;

public class SearchEngine {

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> search(Class<T> type, Collection<T> values, String search) {
		List<SearchOperator<T>> ops = new LinkedList<>();
		for (String s : search.split(" ")) {
			SearchOperator<T> op = SearchOperator.getOperator(type, s);
			if (op != null)
				ops.add(op);
		}
		return search(values, ops.toArray(new SearchOperator[0]));
	}

	public static <T extends Enum<T>> Collection<T> search(Class<T> valuesE, String search) {
		return search(valuesE.getEnumConstants(), search);
	}

	@SuppressWarnings("unchecked")
	public static <T> Collection<T> search(T[] valuesA, String search) {
		List<SearchOperator<T>> ops = new LinkedList<>();
		for (String s : search.split(" ")) {
			if (s.isEmpty())
				continue;
			SearchOperator<T> op = (SearchOperator<T>) SearchOperator
					.getOperator(valuesA.getClass().getComponentType(), s);
			if (op != null)
				ops.add(op);
		}
		return search(valuesA, ops.toArray(new SearchOperator[0]));
	}

	@SafeVarargs
	public static <T> Collection<T> search(Collection<T> values, SearchOperator<T>... operators) {
		Set<T> filtered = new HashSet<>();
		for (SearchOperator<T> op : operators) {
			if (op instanceof DefaultSearchOperatorWrapper)
				((DefaultSearchOperatorWrapper<T>) op).filter(values, filtered);
			else
				op.filter(values, filtered);
		}
		values.removeAll(filtered);
		return values;
	}

	@SafeVarargs
	public static <T extends Enum<T>> Collection<T> search(Class<T> valuesE,
			SearchOperator<T>... operators) {
		Collection<T> values = new LinkedList<>(Arrays.asList(valuesE.getEnumConstants()));
		Set<T> filtered = new HashSet<>();
		for (SearchOperator<T> op : operators)
			op.filter(values, filtered);
		values.removeAll(filtered);
		return values;
	}

	@SafeVarargs
	public static <T> Collection<T> search(T[] valuesA, SearchOperator<T>... operators) {
		Collection<T> values = new LinkedList<>(Arrays.asList(valuesA));
		Set<T> filtered = new HashSet<>();
		for (SearchOperator<T> op : operators)
			op.filter(values, filtered);
		values.removeAll(filtered);
		return values;
	}

	public static void main(String... args) {
		PokemonOperators.registerOperators();
		for (EnumPokemon p : search(EnumPokemon.class, "shiny dark")) {
			System.out.println(p);
		}
	}

}
