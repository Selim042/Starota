package us.myles_selim.starota.misc.data_types;

public class Pair<L, R> {

	public final L left;
	public final R right;

	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return String.format("[%s, %s]", left, right);
	}

}
