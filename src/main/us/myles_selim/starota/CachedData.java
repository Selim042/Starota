package us.myles_selim.starota;

public class CachedData<V> {

	private long time;
	private V value;

	public CachedData(V value) {
		this(System.currentTimeMillis(), value);
	}

	public CachedData(long time, V value) {
		this.time = time;
		this.value = value;
	}

	public V getValue() {
		return this.value;
	}

	public long getCreationTime() {
		return this.time;
	}

	public boolean hasPassed(long millis) {
		return System.currentTimeMillis() >= time + millis;
	}

}
