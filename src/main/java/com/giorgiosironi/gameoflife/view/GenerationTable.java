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
		return "";
	}

}
