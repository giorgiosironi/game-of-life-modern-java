package com.giorgiosironi.gameoflife.domain;

import java.util.HashSet;
import java.util.Set;

public final class Generation implements Plane {

	// TODO: rename to aliveCells?
	private Set<Cell> set;

	public Generation(Set<Cell> set) {
		this.set = set;
	}

	public Generation() {
		this.set = new HashSet<Cell>();
	}

	public static Generation withAliveCells(Cell... cells) {
		Set<Cell> set = new HashSet<Cell>();
		for (int i = 0; i < cells.length; i++) {
			set.add(cells[i]);
		}
		return new Generation(set);
	}
	
	public State state(int x, int y) {
		return isAlive(Cell.onXAndY(x, y)) ? State.ALIVE : State.DEAD;
	}

	public boolean isAlive(Cell candidate) {
		return set.contains(candidate);
	}

	public Generation evolve() {
		Zone toCalculate = Zone.empty();
		for (Cell alive : set) {
			toCalculate = toCalculate.union(alive.block());
		}
		Set<Cell> newGeneration = new HashSet<Cell>();
		// TODO: try Zone.map with Java 8 closures?
		// TODO: Zone.countAlive(AliveRegister register);
		for (Cell candidate : toCalculate) {
			int aliveNeighbors = 0;
			for (Cell neighbor : candidate.neighbors()) {
				if (isAlive(neighbor)) {
					aliveNeighbors++;
				}
			}
			if (isAlive(candidate) && aliveNeighbors >= 2) {
				newGeneration.add(candidate);
			}
		}
		return new Generation(newGeneration);
	}

	public int countAlive() {
		return set.size();
	}

}
