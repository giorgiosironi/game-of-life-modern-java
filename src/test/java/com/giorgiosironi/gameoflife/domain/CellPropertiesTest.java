package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class CellPropertiesTest {

	@Property
	public void blockGeneration(int x, int y) {
		Cell center = Cell.onXAndY(x, y);
		Zone zone = center.block();
		assertEquals("A block of a single cell is a 9-cell square zone", 9, zone.size());
		assertTrue("The center of the neighborhood should be included", zone.contains(Cell.onXAndY(x, y)));
		for (Cell each : zone) {
			distanceOnBothAxesIsLowerOrEqualThan(1, center, each);
		}
	}
	
	@Property
	public void neighborhoodGeneration(int x, int y) {
		Cell center = Cell.onXAndY(x, y);
		Zone zone = center.neighbors();
		assertEquals("A neighborhood of a single cell is a 8-cell closed path", 8, Cell.onXAndY(x, y).neighbors().size());
		assertFalse("The center of the neighborhood should not be included", zone.contains(Cell.onXAndY(x, y)));
		for (Cell each : zone) {
			distanceOnBothAxesIsLowerOrEqualThan(1, center, each);
		}
	}

	private void distanceOnBothAxesIsLowerOrEqualThan(int maximum, Cell first, Cell second) {
		assertTrue(
			"Neighborhood cells cannot be too distant on the x axis: " + first + ", " + second,
			second.distanceOnX(first) <= maximum
		);
		assertTrue(
			"Neighborhood cells cannot be too distant on the y axis: " + first + ", " + second,
			second.distanceOny(first) <= maximum
		);
	}
}
