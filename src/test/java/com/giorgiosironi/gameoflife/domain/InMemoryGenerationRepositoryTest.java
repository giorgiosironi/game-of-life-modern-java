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
	
	@Test
	public void outdatedValuesCanBeUsedToCalculateAMoreRecentGeneration() {
		InMemoryGenerationRepository repository = new InMemoryGenerationRepository();
		Generation single = Generation.withAliveCells(Cell.onXAndY(0, 1));
		repository.add("single", 3, single);
		assertEquals(Generation.empty(), repository.get("single", 4));
	}
	
	@Test
	public void theMostRecentGenerationOfAPlaneIsUsedToCalculateTheMoreRecentOne() {
		InMemoryGenerationRepository repository = new InMemoryGenerationRepository();
		
		repository.add("single", 2, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		repository.add("single", 3, Generation.blockAt(0, 1));
		assertEquals(Generation.blockAt(0, 1), repository.get("single", 4));
	}
	
	@Test
	public void multipleGenerationsCanBeCachedTogether() {
		InMemoryGenerationRepository repository = new InMemoryGenerationRepository();
		repository.add("single", 3, Generation.blockAt(0, 1));
		repository.add("single", 2, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		assertEquals(Generation.blockAt(0, 1), repository.get("single", 4));
	}
	
	@Test
	public void previousGenerationsCannotBeCalculatedFromAMoreRecentOne() {
		InMemoryGenerationRepository repository = new InMemoryGenerationRepository();
		repository.add("single", 4, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		assertNull(repository.get("single", 3));
	}
}
