package com.giorgiosironi.gameoflife.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

public final class Zone implements Iterable<Cell> {

	private Set<Cell> set;

	private Zone(Set<Cell> set) {
		this.set = set;
	}
	
	public static Zone fromCollection(Collection<Cell> collection) {
		return new Zone(populatedSet(collection));
	}

	public static Zone empty() {
		return new Zone(emptySet());
	}
		
	public static Zone ofCells(Cell... contents) {
		Set<Cell> set = emptySet();
		for (int i = 0; i < contents.length; i++) {
			set.add(contents[i]);
		}
		return new Zone(set);
	}

	private static Set<Cell> emptySet() {
		Set<Cell> set = new HashSet<Cell>();
		return set;
	}
	
	private static Set<Cell> populatedSet(Collection<Cell> collection) {
		return new HashSet<Cell>(collection);
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
		Set<Cell> union = populatedSet(this.set);
		for (Cell each: intersecting) {
			union.add(each);
		}
		return new Zone(union);
	}

	public Zone remove(Cell target) {
		Set<Cell> smaller = populatedSet(this.set);
		smaller.remove(target);
		return new Zone(smaller);
	}

	public int count(Predicate<Cell> predicate) {
		int total = 0;
		for (Cell candidate : this.set) {
			if (predicate.test(candidate)) {
				total++;
			}
		}
		return total;
	}

}
