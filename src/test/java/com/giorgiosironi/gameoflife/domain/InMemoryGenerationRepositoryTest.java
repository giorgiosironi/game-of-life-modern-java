package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class InMemoryGenerationRepositoryTest {

	@Test
	public void storesAGenerationAccordingToANameAndIndex() {
		InMemoryGenerationRepository repository = new InMemoryGenerationRepository();
		Generation single = Generation.withAliveCells(Cell.onXAndY(0, 1));
		repository.add("single", 4, single);
		assertEquals(single, repository.get("single", 4));
	}
	
	@Test
	public void missingValuesReturnNull() {
		InMemoryGenerationRepository repository = new InMemoryGenerationRepository();
		assertNull(repository.get("single", 4));
	}
}
