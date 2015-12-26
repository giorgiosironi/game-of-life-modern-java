package com.giorgiosironi.gameoflife.domain;

import java.util.HashSet;
import java.util.Set;

public final class Generation implements Plane {

	// TODO: rename to aliveCells?
	private Set<Cell> set;
	private ClassicRules rules;

	private Generation(Set<Cell> set) {
		this.set = set;
		this.rules = new ClassicRules();
	}

	private Generation() {
		this(new HashSet<Cell>());
	}

	public static Generation withAliveCells(Cell... cells) {
		Set<Cell> set = new HashSet<Cell>();
		for (int i = 0; i < cells.length; i++) {
			set.add(cells[i]);
		}
		return new Generation(set);
	}
	
	public State state(int x, int y) {
		return state(Cell.onXAndY(x, y));
	}
	
	private State state(Cell cell) {
		return isAlive(cell) ? State.ALIVE : State.DEAD;
	}

	private boolean isAlive(Cell candidate) {
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
			State candidateStateInNewGeneration = rules.nextState(state(candidate), aliveNeighbors);
			if (candidateStateInNewGeneration == State.ALIVE) {
				newGeneration.add(candidate);
			}
		}
		return new Generation(newGeneration);
	}

	public int countAlive() {
		return set.size();
	}

}
