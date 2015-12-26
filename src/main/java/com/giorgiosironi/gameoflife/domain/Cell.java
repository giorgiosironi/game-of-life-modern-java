package com.giorgiosironi.gameoflife.domain;

import java.util.ArrayList;
import java.util.List;

public final class Cell {

	private int x;
	private int y;

	private Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Cell onXAndY(int x, int y) {
		return new Cell(x, y);
	}
	
	public int hashCode() {
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

	public Zone block() {
		List<Cell> set = new ArrayList<Cell>();
		for (int x = this.x - 1; x <= this.x + 1; x++) {
			for (int y = this.y - 1; y <= this.y + 1; y++) {
				set.add(Cell.onXAndY(x, y));
			}
		}
		
		return Zone.fromCollection(set);
	}
	
	public Zone neighbors() {
		return block().remove(this);
	}

	public int distanceOnX(Cell center) {
		return Math.abs(center.x - this.x);
	}

	public int distanceOny(Cell center) {
		return Math.abs(center.y - this.y);
	}


}
