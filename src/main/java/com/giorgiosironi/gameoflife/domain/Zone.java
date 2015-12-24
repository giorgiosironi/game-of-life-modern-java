package com.giorgiosironi.gameoflife.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class Zone implements Iterable<Cell> {

	private Set<Cell> set;

	// TODO: keyboard shortcuts of Eclipse for quick fixes
	// TODO: make constructors private
	public Zone(Set<Cell> set) {
		this.set = set;
	}

	public static Zone fromSet(Set<Cell> set) {
		return new Zone(set);
	}
	
	public static Zone ofCells(Cell... contents) {
		Set<Cell> set = new HashSet<Cell>();
		for (int i = 0; i < contents.length; i++) {
			set.add(contents[i]);
		}
		return new Zone(set);
	}

	public int size() {
		return set.size();
	}

	public boolean contains(Cell candidate) {
		return set.contains(candidate);
	}

	@Override
	public Iterator<Cell> iterator() {
		return set.iterator();
	}

	public Zone union(Zone intersecting) {
		Set<Cell> union = new HashSet<Cell>(this.set);
		for (Cell each: intersecting) {
			union.add(each);
		}
		return new Zone(union);
	}
}
