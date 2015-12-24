package com.giorgiosironi.gameoflife.domain;

import java.util.HashSet;
import java.util.Set;

public final class Generation {

	private Set<Cell> set;

	public Generation(Set<Cell> set) {
		this.set = set;
	}

	public static Generation withAliveCells(Cell theOnlyAliveOne) {
		Set<Cell> set = new HashSet<Cell>();
		set.add(theOnlyAliveOne);
		return new Generation(set);
	}

	public boolean isAlive(Cell candidate) {
		return set.contains(candidate);
	}

}
