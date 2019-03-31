package us.myles_selim.starota.cache;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CachedSet<V> extends TreeSet<V> implements ICache {

	private static final long serialVersionUID = -6539607562419913707L;
	private final Set<CachedData<V>> data = new TreeSet<>();
	private final long cacheTime;

	public CachedSet(long cacheTime) {
		this.cacheTime = cacheTime;
	}

	public boolean isCached(V value) {
		return data.contains(new NonCachedData<>(value)) && find(value).hasPassed(this.cacheTime);
	}

	@Override
	public boolean add(V value) {
		boolean ret = this.data.add(new CachedData<>(value));
		cleanCache();
		return ret;
	}

	public long getCreationTime(V value) {
		return isCached(value) ? -1 : find(value).time;
	}

	public Instant getCreationInstant(V value) {
		return isCached(value) ? null : Instant.ofEpochMilli(find(value).time);
	}

	private CachedData<V> find(V value) {
		if (!data.contains(new NonCachedData<>(value)))
			return null;
		for (CachedData<V> cd : this.data)
			if (cd.value != null & cd.value.equals(value))
				return cd;
		return null;
	}

	private void cleanCache() {
		List<CachedData<?>> toClean = new ArrayList<>();
		for (CachedData<?> cd : this.data)
			if (cd.hasPassed(this.cacheTime))
				toClean.add(cd);
		this.data.removeAll(toClean);
	}

	private class CachedData<V2> {

		protected long time;
		protected V2 value;

		public CachedData(V2 value) {
			this(System.currentTimeMillis(), value);
		}

		protected CachedData(long time, V2 value) {
			this.time = time;
			this.value = value;
		}

		public boolean hasPassed(long millis) {
			return System.currentTimeMillis() >= time + millis;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj instanceof NonCachedData) {
				NonCachedData ncd = (NonCachedData) obj;
				return this.value == ncd.value || this.value.equals(ncd.value);
			}
			if (obj instanceof CachedData) {
				CachedData cd = (CachedData) obj;
				return this.time == cd.time && this.value.equals(cd.value);
			}
			return false;
		}

	}

	private class NonCachedData<V2> extends CachedData<V2> {

		public NonCachedData(V2 value) {
			super(-1, value);
		}

	}

}
