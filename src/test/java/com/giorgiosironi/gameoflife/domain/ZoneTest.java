package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

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

}
