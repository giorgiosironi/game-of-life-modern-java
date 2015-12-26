package com.giorgiosironi.gameoflife.domain;

import com.giorgiosironi.gameoflife.domain.Plane.State;

public final class ClassicRules {

	public State nextState(State currentState, int aliveNeighbors) {
		if (currentState == State.ALIVE && aliveNeighbors >= 2 && aliveNeighbors <= 3) {
			return State.ALIVE;
		}
		
		if (currentState == State.DEAD && aliveNeighbors == 3) {
			return State.ALIVE;
		}
		
		return State.DEAD;
	}

}
