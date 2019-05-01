package us.myles_selim.starota.misc.utils;

public class IndexHolder {

	public int value;

	public IndexHolder() {
		this(0);
	}

	public IndexHolder(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

}
