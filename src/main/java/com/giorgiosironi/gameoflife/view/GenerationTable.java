package com.giorgiosironi.gameoflife.view;

import com.giorgiosironi.gameoflife.domain.Plane;
import com.giorgiosironi.gameoflife.domain.Plane.State;

public class GenerationTable {

	private int rows;
	private int columns;
	private Plane plane;

	public GenerationTable(int rows, int columns, Plane plane) {
		// TODO: should take a Generation object containing the state of the generation
		// Generation should be able to evolve
		this.rows = rows;
		this.columns = columns;
		this.plane = plane;
	}
	
	public GenerationTable(Plane plane) {
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
