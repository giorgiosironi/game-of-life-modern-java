package com.giorgiosironi.gameoflife.domain;

import java.util.Iterator;
import java.util.Set;

public final class Zone implements Iterable<Cell> {

	private Set<Cell> set;

	// TODO: keyboard shortcuts of Eclipse for quick fixes
	public Zone(Set<Cell> set) {
		this.set = set;
	}

	public static Zone fromSet(Set<Cell> set) {
		return new Zone(set);
	}

	public int size() {
		return set.size();
	}

	public boolean contains(Cell candidate) {
		// TODO Auto-generated method stub
		return set.contains(candidate);
	}

	@Override
	public Iterator<Cell> iterator() {
		return set.iterator();
	}
	
}
