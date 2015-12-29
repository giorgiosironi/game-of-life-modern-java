package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ZoneTest {

	@Test
	public void differentInstancesCanBeMerged() {
		Zone first = Zone.ofCells(Cell.onXAndY(0, 0), Cell.onXAndY(0, 1));
		Zone intersecting = Zone.ofCells(Cell.onXAndY(0, 0), Cell.onXAndY(1, 1));
		Zone union = first.union(intersecting);
		assertEquals(3, union.size());
		assertTrue("First set cells should be contained in the union", union.contains(Cell.onXAndY(0, 1)));
		assertTrue("Second set cells should be contained in the union", union.contains(Cell.onXAndY(1, 1)));
		assertTrue("Common set cells should be contained in the union", union.contains(Cell.onXAndY(0, 0)));
	}

	@Test
	public void aSubsetOfTheZoneSatisfyingAPredicateCanBeCounted() {
		Zone area = Zone.ofCells(Cell.onXAndY(0, 0), Cell.onXAndY(0, 1), Cell.onXAndY(2, 3), Cell.onXAndY(3, 4));
		
		assertEquals(3, area.count((Cell c) -> c.getX() % 2 == 0));
	}
	
	@Test
	public void aZoneCanBeMappedOver() {
		Zone area = Zone.ofCells(Cell.onXAndY(0, 0), Cell.onXAndY(0, 1), Cell.onXAndY(2, 3), Cell.onXAndY(3, 4));
		
		List<Cell> collected = new ArrayList<Cell>();
		area.forEach((Cell c) -> collected.add(c));
		assertEquals(4, collected.size());	
	}
	
}
