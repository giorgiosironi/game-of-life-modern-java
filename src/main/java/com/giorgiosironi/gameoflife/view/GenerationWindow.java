package com.giorgiosironi.gameoflife.view;

import com.giorgiosironi.gameoflife.domain.Plane;
import com.giorgiosironi.gameoflife.domain.Plane.State;

public class GenerationWindow {

	private int rows;
	private int columns;
	private Plane plane;

	public GenerationWindow(int rows, int columns, Plane plane) {
		this.rows = rows;
		this.columns = columns;
		this.plane = plane;
	}
	
	public GenerationWindow(Plane plane) {
		this(10, 10, plane);
	}
	
	public State state(int x, int y) {
		return plane.state(x, y);
	}
	
	public int getRows()
	{
		return this.rows;
	}
	
	public int getColumns()
	{
		return this.columns;
	}

}
