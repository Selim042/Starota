package us.myles_selim.starota.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public abstract class SearchOperator<T> {

	private static final Map<Class<?>, Set<SearchOperator<?>>> OPERATORS = new HashMap<>();
	private static final Map<Class<?>, SearchOperator<?>> DEFAULT_OPERATORS = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static <T> SearchOperator<T> getOperator(Class<T> type, String search) {
		if (!OPERATORS.containsKey(type))
			return null;
		if (search.contains("|")) {
			String[] ors = search.split("\\|");
			List<SearchOperator<T>> ops = new LinkedList<>();
			for (String or : ors) {
				SearchOperator<T> op = getOperator(type, or);
				if (op != null)
					ops.add(op);
			}
			return new OrSearchOperator<T>(type, ops.toArray(new SearchOperator[0]));
		}
		if (search.startsWith("!")) {
			SearchOperator<T> op = getOperator(type, search.substring(1));
			if (op != null)
				return new NotSearchOperator<>(type, op);
		}
		Set<SearchOperator<?>> ops = OPERATORS.get(type);
		for (SearchOperator<?> opO : ops) {
			if (opO instanceof OrSearchOperator)
				continue;
			for (String t : opO.getSearchTerms()) {
				if (opO.isCaseSensitive()) {
					if (t.equals(search))
						return (SearchOperator<T>) opO;
				} else {
					if (t.equalsIgnoreCase(search))
						return (SearchOperator<T>) opO;
				}
			}
		}
		if (DEFAULT_OPERATORS.containsKey(type))
			return new DefaultSearchOperatorWrapper<>(
					(DefaultSearchOperator<T>) DEFAULT_OPERATORS.get(type), search);
		return null;
	}

	public static <T> List<String[]> getOperatorTerms(Class<T> type) {
		List<String[]> ret = new ArrayList<>();
		Set<SearchOperator<?>> ops = OPERATORS.get(type);
		if (ops == null)
			return Collections.emptyList();
		for (SearchOperator<?> op : ops)
			ret.add(op.getSearchTerms());
		return ret;
	}

	public SearchOperator(Class<T> type) {
		if (type == null)
			return;
		Set<SearchOperator<?>> ops = OPERATORS.get(type);
		if (ops == null) {
			ops = new HashSet<>();
			OPERATORS.put(type, ops);
		}
		ops.add(this);
	}

	/**
	 * Returns values to be filtered out
	 */
	public abstract void filter(Collection<T> vals, Collection<T> filtered);

	/**
	 * Returns any terms that match this search term
	 */
	public abstract String[] getSearchTerms();

	/**
	 * Returns if the search terms are case sensitive
	 */
	public boolean isCaseSensitive() {
		return false;
	}

	@Override
	public String toString() {
		return "SearchOperator:" + getSearchTerms()[0];
	}

	public static final class OrSearchOperator<T> extends SearchOperator<T> {

		private final SearchOperator<T>[] ops;

		@SuppressWarnings("unchecked")
		public OrSearchOperator(Class<T> type, SearchOperator<T>... ops) {
			super(null);
			this.ops = ops;
		}

		@Override
		public void filter(Collection<T> vals, Collection<T> filtered) {
			vals.forEach(t -> {
				Collection<T> sing = Collections.singletonList(t);
				boolean inNone = true;
				boolean inAll = true;
				for (SearchOperator<T> op : ops) {
					Collection<T> fil = new HashSet<>(filtered);
					op.filter(sing, fil);
					if (fil.contains(t))
						inNone = false;
					else
						inAll = false;
				}
				if (!inNone && inAll)
					filtered.add(t);
			});
		}

		@Override
		public String[] getSearchTerms() {
			throw new IllegalArgumentException();
		}

	}

	public static final class NotSearchOperator<T> extends SearchOperator<T> {

		private final SearchOperator<T> op;

		public NotSearchOperator(Class<T> type, SearchOperator<T> op) {
			super(null);
			this.op = op;
		}

		@Override
		public void filter(Collection<T> vals, Collection<T> filtered) {
			Collection<T> fil = new HashSet<>(filtered);
			this.op.filter(vals, fil);
			Collection<T> recent = new HashSet<>(fil);
			recent.removeAll(filtered);
			Collection<T> a = new HashSet<>(vals);
			a.removeAll(recent);
			filtered.addAll(a);
		}

		@Override
		public String[] getSearchTerms() {
			throw new IllegalArgumentException();
		}

	}

	public static final class LlambadaSearchOperator<T> extends SearchOperator<T> {

		private final Predicate<T> predicate;
		private final boolean caseSensitive;
		private final String[] terms;

		public LlambadaSearchOperator(Class<T> type, Predicate<T> predicate, String... terms) {
			this(type, predicate, false, terms);
		}

		public LlambadaSearchOperator(Class<T> type, Predicate<T> predicate, boolean caseSensitive,
				String... terms) {
			super(type);
			this.predicate = predicate;
			this.caseSensitive = caseSensitive;
			this.terms = terms;
		}

		@Override
		public final void filter(Collection<T> vals, Collection<T> filtered) {
			vals.forEach(t -> {
				if (predicate.test(t))
					filtered.add(t);
			});
		}

		@Override
		public String[] getSearchTerms() {
			return this.terms;
		}

		@Override
		public boolean isCaseSensitive() {
			return this.caseSensitive;
		}

	}

	public static abstract class DefaultSearchOperator<T> extends SearchOperator<T> {

		public DefaultSearchOperator(Class<T> type) {
			super(type);
		}

		@Override
		public final void filter(Collection<T> vals, Collection<T> filtered) {}

		public abstract void filter(String term, Collection<T> vals, Collection<T> filtered);

		@Override
		public final String[] getSearchTerms() {
			return null;
		}

	}

	public static final class DefaultSearchOperatorWrapper<T> extends SearchOperator<T> {

		private final DefaultSearchOperator<T> op;
		private final String term;

		public DefaultSearchOperatorWrapper(DefaultSearchOperator<T> op, String term) {
			// pass null so it isn't added to the op map
			super(null);
			this.op = op;
			this.term = term;
		}

		@Override
		public void filter(Collection<T> vals, Collection<T> filtered) {
			op.filter(term, vals, filtered);
		}

		@Override
		public String[] getSearchTerms() {
			return null;
		}

	}

}
