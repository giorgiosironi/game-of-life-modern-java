package com.giorgiosironi.gameoflife.domain;

import java.util.HashSet;
import java.util.Set;

public final class Generation implements Plane {

	private Set<Cell> aliveCells;
	private ClassicRules rules;

	private Generation(Set<Cell> set) {
		this.aliveCells = set;
		this.rules = new ClassicRules();
	}

	public static Generation withAliveCells(Cell... cells) {
		Set<Cell> set = new HashSet<Cell>();
		for (int i = 0; i < cells.length; i++) {
			set.add(cells[i]);
		}
		return new Generation(set);
	}
	
	public static Generation empty() {
		return Generation.withAliveCells();
	}
	
	public static Generation blockAt(int x, int y) {
		return Generation.withAliveCells(
			Cell.onXAndY(x, y),
			Cell.onXAndY(x + 1, y),
			Cell.onXAndY(x, y + 1),
			Cell.onXAndY(x + 1, y + 1)
		);
	}
	
	public static Generation horizontalBarAt(int x, int y) {
		return Generation.withAliveCells(
			Cell.onXAndY(x, y),
			Cell.onXAndY(x + 1, y),
			Cell.onXAndY(x + 2, y)
		);
	}
	
	public static Generation verticalBarAt(int x, int y) {
		return Generation.withAliveCells(
			Cell.onXAndY(x, y),
			Cell.onXAndY(x, y + 1),
			Cell.onXAndY(x, y + 2)
		);
	}
	
	public State state(int x, int y) {
		return state(Cell.onXAndY(x, y));
	}
	
	private State state(Cell cell) {
		return isAlive(cell) ? State.ALIVE : State.DEAD;
	}

	private boolean isAlive(Cell candidate) {
		return aliveCells.contains(candidate);
	}

	public Generation evolve() {
		Set<Cell> newGeneration = new HashSet<Cell>();
		allCandidateToBeAliveInTheNextGeneration().forEach((Cell candidate) -> {
			int aliveNeighbors = candidate.neighbors().count((Cell c) -> isAlive(c));
			State candidateStateInNewGeneration = rules.nextState(state(candidate), aliveNeighbors);
			if (candidateStateInNewGeneration == State.ALIVE) {
				newGeneration.add(candidate);
			}
		}); 
		return new Generation(newGeneration);
	}

	private Zone allCandidateToBeAliveInTheNextGeneration() {
		Zone toCalculate = aliveCells
			.stream()
			.map((alive) -> alive.block())
			.reduce(
				Zone.empty(), 
				(Zone z1, Zone z2) -> z1.union(z2)
			);
		return toCalculate;
	}

	public int countAlive() {
		return aliveCells.size();
	}
	
	@Override
	public boolean equals(Object anotherObject) {
		if (!(anotherObject instanceof Generation)) {
			return false;
		}
		Generation another = (Generation) anotherObject;
		return aliveCells.equals(another.aliveCells);
	}

	@Override
	public int hashCode() {
		return aliveCells.hashCode();
	}
	
	@Override
	public String toString() {
		return "Generation: " + aliveCells.toString();
	}
}
