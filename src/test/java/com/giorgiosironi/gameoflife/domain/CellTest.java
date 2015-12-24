package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class CellTest {

	@Test
	public void generatesItsNeighborhood() {
		Cell center = Cell.onXAndY(1, 4);
		Zone zone = center.neighborhood();
		assertEquals("A neighborhood of a single cell is a 9-cell zone", 9, zone.size());
		assertTrue("The center of the neighborhood should be included", zone.contains(Cell.onXAndY(1, 4)));
		for (Cell each : zone) {
			// TODO: improve the assertion error message in an idiomatic way
			assertTrue("Neighborhood cells cannot be too distant on the x axis", each.distanceOnX(center) <= 1);
			assertTrue("Neighborhood cells cannot be too distant on the y axis", each.distanceOny(center) <= 1);
		}
	}

}
