package us.myles_selim.starota.misc.data_types;

import java.util.ArrayDeque;

public class MaxQueue<T> extends ArrayDeque<T> {

	private final int maxSize;

	public MaxQueue(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public boolean add(T t) {
		super.add(t);
		if (this.size() > maxSize)
			this.poll();
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8063864272739097678L;

}
