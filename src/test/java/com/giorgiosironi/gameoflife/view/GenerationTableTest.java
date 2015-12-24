package com.giorgiosironi.gameoflife.view;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.giorgiosironi.gameoflife.domain.Plane;
import com.giorgiosironi.gameoflife.domain.Plane.State;

public class GenerationTableTest {

	@Test
	public void testRendersATableRepresentingAnAreaOfThePlaneStartingFromTheOrigin() {
		Plane plane = mock(Plane.class);
		when(plane.state(0, 0)).thenReturn(State.DEAD);
		when(plane.state(0, 1)).thenReturn(State.DEAD);
		when(plane.state(0, 2)).thenReturn(State.ALIVE);
		when(plane.state(1, 0)).thenReturn(State.DEAD);
		when(plane.state(1, 1)).thenReturn(State.ALIVE);
		when(plane.state(1, 2)).thenReturn(State.ALIVE);
		
		GenerationTable table = new GenerationTable(2, 3, plane);
		
		assertEquals(
			"<table>"
			+ "<tr>"
			+ "<td></td>"
			+ "<td></td>"
			+ "<td>X</td>"
			+ "</tr>"
			+ "<tr>"
			+ "<td></td>"
			+ "<td>X</td>"
			+ "<td>X</td>"
			+ "</tr>"
			+ "</table>",
			table.toString()
		);
		
	}

}
