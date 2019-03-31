package us.myles_selim.starota.cache;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CachedMap<K, V> extends HashMap<K, V> implements ICache {

	private static final long serialVersionUID = 8458113639890753658L;
	private final Map<K, CachedData<V>> data = new HashMap<>();
	private final long cacheTime;

	public CachedMap(long cacheTime) {
		this.cacheTime = cacheTime;
	}

	@Override
	public V put(K key, V value) {
		CachedData<V> oldValue = this.data.put(key, new CachedData<>(value));
		cleanCache();
		return oldValue == null ? null : oldValue.value;
	}

	@Override
	public V get(Object key) {
		CachedData<V> val = data.get(key);
		if (val == null || val.hasPassed(cacheTime))
			return null;
		return val.value;
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return data.containsKey(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> e : m.entrySet())
			data.put(e.getKey(), new CachedData<>(e.getValue()));
	}

	@Override
	public V remove(Object key) {
		CachedData<V> oldValue = data.remove(key);
		return oldValue == null ? null : oldValue.value;
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public boolean containsValue(Object value) {
		for (Entry<K, CachedData<V>> e : data.entrySet())
			if (e.getValue().value != null && e.getValue().value.equals(value))
				return true;
		return false;
	}

	@Override
	public Set<K> keySet() {
		return data.keySet();
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return data.getOrDefault(key, new CachedData<>(defaultValue)).value;
	}

	@Override
	public V putIfAbsent(K key, V value) {
		return data.putIfAbsent(key, new CachedData<>(value)).value;
	}

	@Override
	public boolean remove(Object key, Object value) {
		return data.remove(key, new NonCachedData<>(value));
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return data.replace(key, new CachedData<>(oldValue), new CachedData<>(newValue));
	}

	@Override
	public V replace(K key, V value) {
		return data.replace(key, new CachedData<>(value)).value;
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		Objects.requireNonNull(mappingFunction);
		V v;
		if ((v = get(key)) == null) {
			V newValue;
			if ((newValue = mappingFunction.apply(key)) != null) {
				data.put(key, new CachedData<>(newValue));
				return newValue;
			}
		}
		return v;
	}

	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		Objects.requireNonNull(remappingFunction);
		V oldValue;
		if ((oldValue = get(key)) != null) {
			V newValue = remappingFunction.apply(key, oldValue);
			if (newValue != null) {
				data.put(key, new CachedData<>(newValue));
				return newValue;
			} else {
				data.remove(key);
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		throw new UnsupportedOperationException();
	}

	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		throw new UnsupportedOperationException();
	}

	public boolean isCached(K key) {
		return data.containsKey(key) && data.get(key).hasPassed(this.cacheTime);
	}

	public long getCreationTime(K key) {
		return isCached(key) ? -1 : data.get(key).time;
	}

	public Instant getCreationInstant(K key) {
		return isCached(key) ? null : Instant.ofEpochMilli(data.get(key).time);
	}

	private void cleanCache() {
		List<K> toClean = new ArrayList<>();
		for (Entry<K, CachedData<V>> e : this.data.entrySet())
			if (e.getValue().hasPassed(this.cacheTime))
				toClean.add(e.getKey());
		for (K key : toClean)
			data.remove(key);
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
