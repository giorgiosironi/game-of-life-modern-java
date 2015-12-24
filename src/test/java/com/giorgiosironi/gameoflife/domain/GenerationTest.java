package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class GenerationTest {

	@Test
	public void modelsTheAliveCells() {
		Generation g = Generation.withAliveCells(Cell.onXAndY(0, 0));
		assertTrue(g.isAlive(Cell.onXAndY(0, 0)));
		assertFalse(g.isAlive(Cell.onXAndY(0, 1)));
	}
	
	@Test
	public void evolvesFollowingTheFirstRuleKillingSolitaryCells()
	{
		Generation first = Generation.withAliveCells(Cell.onXAndY(0, 0));
		Generation second = first.evolve();
		assertEquals(0, second.countAlive());
	}

}
