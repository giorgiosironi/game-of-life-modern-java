package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.Test;

public class InMemoryGenerationRepositoryTest {
	
	InMemoryGenerationRepository repository = new InMemoryGenerationRepository();

	@Test
	public void storesAGenerationAccordingToANameAndIndex() {
		Generation single = Generation.withAliveCells(Cell.onXAndY(0, 1));
		repository.add("single", 4, single);
		assertEquals(single, repository.get("single", 4));
	}
	
	@Test
	public void missingValuesReturnNull() {
		assertNull(repository.get("single", 4));
	}
	
	@Test
	public void outdatedValuesCanBeUsedToCalculateAMoreRecentGeneration() {
		Generation single = Generation.withAliveCells(Cell.onXAndY(0, 1));
		repository.add("single", 3, single);
		assertEquals(Generation.empty(), repository.get("single", 4));
	}
	
	@Test
	public void theMostRecentGenerationOfAPlaneIsUsedToCalculateTheMoreRecentOne() {		
		repository.add("single", 2, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		repository.add("single", 3, Generation.blockAt(0, 1));
		assertEquals(Generation.blockAt(0, 1), repository.get("single", 4));
	}
	
	@Test
	public void multipleGenerationsCanBeCachedTogether() {
		repository.add("single", 3, Generation.blockAt(0, 1));
		repository.add("single", 2, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		assertEquals(Generation.blockAt(0, 1), repository.get("single", 4));
	}
	
	@Test
	public void previousGenerationsCannotBeCalculatedFromAMoreRecentOne() {
		repository.add("single", 4, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		assertNull(repository.get("single", 3));
	}
	
	int threads = 10;
	int iterations = 100;
	CyclicBarrier barrier = new CyclicBarrier(
		threads,
		() -> {
			//System.out.println("Round completed");
		}
	);
	
	@Test
	public void concurrentUsageOfPlanesDoNotInterfereWithEachOther() throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Generation block = Generation.blockAt(0, 1);
		Generation horizontalBar = Generation.horizontalBarAt(0, 1);
		Generation verticalBar = Generation.verticalBarAt(1, 0);
		for (int i = 0; i < threads; i++) {
			String plane = "plane-" + i;
			if (i % 2 == 0) {
				executor.execute(new SinglePlaneUser(
					block,
					plane,
					(j) -> {
						assertEquals(
							block,
							repository.get(plane, j)
						);
					}
				));
			} else {
				executor.execute(new SinglePlaneUser(
					horizontalBar,
					plane,
					(j) -> {
						if (j % 2 == 0) {
							assertEquals(
								horizontalBar,
								repository.get(plane, j)
							);
						} else {
							assertEquals(
								verticalBar,
								repository.get(plane, j)
							);
						}
					}
				));
			}
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
	}
	
	private class SinglePlaneUser implements Runnable {

		private String plane;
		private Generation startingPoint;
		private Consumer<Integer> check;
		
		private SinglePlaneUser(Generation startingPoint, String plane, Consumer<Integer> check) {
			this.startingPoint = startingPoint;
			this.plane = plane;
			this.check = check;
		}
		
		@Override
		public void run() {
			try {
				barrier.await();
				repository.add(plane, 0, startingPoint);
				for (int j = 0; j < iterations; j++) {
					barrier.await();
					check.accept(j);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
