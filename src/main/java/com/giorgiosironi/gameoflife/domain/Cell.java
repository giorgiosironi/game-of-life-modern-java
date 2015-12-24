package com.giorgiosironi.gameoflife.domain;

import java.util.HashSet;
import java.util.Set;

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

	public Zone neighborhood() {
		// TODO: we have knowledge of HashSet here but we should only know about it in Zone
		Set<Cell> set = new HashSet<Cell>();
		for (int x = this.x - 1; x <= this.x + 1; x++) {
			for (int y = this.y - 1; y <= this.y + 1; y++) {
				set.add(Cell.onXAndY(x, y));
			}
		}
		
		return Zone.fromSet(set);
	}

	public int distanceOnX(Cell center) {
		return Math.abs(center.x - this.x);
	}

	public int distanceOny(Cell center) {
		return Math.abs(center.y - this.y);
	}

}
