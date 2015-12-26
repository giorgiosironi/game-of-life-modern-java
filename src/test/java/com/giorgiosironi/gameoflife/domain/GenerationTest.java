package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.giorgiosironi.gameoflife.domain.Plane.State;

public class GenerationTest {

	// TODO: isAlive() may become private?
	@Test
	public void modelsTheAliveCells() {
		Generation g = Generation.withAliveCells(Cell.onXAndY(0, 0));
		assertTrue(g.isAlive(Cell.onXAndY(0, 0)));
		assertFalse(g.isAlive(Cell.onXAndY(0, 1)));
	}
	
	@Test 
	public void conformsToThePlaneInterfaceForViews()
	{
		Generation g = Generation.withAliveCells(Cell.onXAndY(0, 0));
		assertEquals(State.ALIVE, g.state(0, 0));
		assertFalse(g.isAlive(Cell.onXAndY(0, 1)));
	}
	
	@Test
	public void a2By2BlockWillRemainFixedForEternity()
	{
		Generation first = Generation.withAliveCells(Cell.onXAndY(0, 0), Cell.onXAndY(0, 1), Cell.onXAndY(1, 0), Cell.onXAndY(1, 1));
		Generation second = first.evolve();
		assertEquals(4, second.countAlive());
	}

}
