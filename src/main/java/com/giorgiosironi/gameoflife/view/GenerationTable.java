package com.giorgiosironi.gameoflife.view;

import com.giorgiosironi.gameoflife.model.Plane;
import com.giorgiosironi.gameoflife.model.Plane.State;

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
	
	public GenerationTable() {
		this(10, 10, null);
	}

	public String toString() {
		String representation = "<table>";
		for (int x = 0; x < rows; x++) {
			representation += "<tr>";
			for (int y = 0; y < columns; y++) {
				String content = this.plane.state(x, y) == State.ALIVE ? "X" : "";
				representation += "<td>" + content + "</td>";
			}
			representation += "</tr>";
		}
		representation += "</table>";
		return representation;
	}

}
