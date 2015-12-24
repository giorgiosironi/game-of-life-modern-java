package com.giorgiosironi.gameoflife.domain;

// TODO: Position may be a better name
public final class Cell {

	private int x;
	private int y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Cell onXAndY(int x, int y) {
		return new Cell(x, y);
	}
	
	public int hashCode()
	{
		return 0;
	}
	
	public boolean equals(Object candidate)
	{
		if (!(candidate instanceof Cell)) {
			return false;
		}
		
		Cell another = (Cell) candidate;
		return another.x == this.x
			&& another.y == this.y;
	}

}
