package com.giorgiosironi.gameoflife.view;

public class GenerationTable {

	private int rows;
	private int columns;

	public GenerationTable(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}
	
	public GenerationTable() {
		this(10, 10);
	}

	public String toString() {
		String representation = "<table>";
		for (int x = 0; x < rows; x++) {
			representation += "<tr>";
			for (int y = 0; y < columns; y++) {
				representation += "<td></td>";
			}
			representation += "</tr>";
		}
		representation += "</table>";
		return representation;
	}

}
