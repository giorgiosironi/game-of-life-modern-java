package com.giorgiosironi.gameoflife.model;

public interface Plane {
	public enum State { ALIVE, DEAD };
	
	public State state(int x, int y);
}
