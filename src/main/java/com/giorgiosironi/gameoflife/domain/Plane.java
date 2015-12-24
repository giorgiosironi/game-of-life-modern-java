package com.giorgiosironi.gameoflife.domain;

public interface Plane {
	public enum State { ALIVE, DEAD };
	
	public State state(int x, int y);
}
