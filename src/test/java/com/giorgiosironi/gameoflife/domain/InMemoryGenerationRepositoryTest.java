package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.Test;

import com.giorgiosironi.gameoflife.domain.GenerationRepository.GenerationResult;
import com.giorgiosironi.gameoflife.domain.GenerationRepository.GenerationResult.Efficiency;

public class InMemoryGenerationRepositoryTest {
	
	InMemoryGenerationRepository repository = new InMemoryGenerationRepository();

	@Test
	public void storesAGenerationAccordingToANameAndIndex() {
		Generation single = Generation.withAliveCells(Cell.onXAndY(0, 1));
		repository.add("single", 4, single);
		assertEquals(
			GenerationResult.hit(single),
			repository.get("single", 4)
		);
	}
	
	@Test
	public void missingValuesReturnNull() {
		assertEquals(
			GenerationResult.miss(),
			repository.get("single", 4)
		);
	}
	
	@Test
	public void outdatedValuesCanBeUsedToCalculateAMoreRecentGeneration() {
		Generation single = Generation.withAliveCells(Cell.onXAndY(0, 1));
		repository.add("single", 3, single);
		assertEquals(
			GenerationResult.partialHit(Generation.empty(), 1),
			repository.get("single", 4)
		);
	}
	
	@Test
	public void theMostRecentGenerationOfAPlaneIsUsedToCalculateTheMoreRecentOne() {		
		repository.add("single", 2, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		repository.add("single", 3, Generation.blockAt(0, 1));
		assertEquals(
			GenerationResult.partialHit(Generation.blockAt(0, 1), 2),
			repository.get("single", 5)
		);
	}
	
	@Test
	public void multipleGenerationsCanBeCachedTogether() {
		repository.add("single", 3, Generation.blockAt(0, 1));
		repository.add("single", 2, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		assertEquals(
			GenerationResult.partialHit(Generation.blockAt(0, 1), 2),
			repository.get("single", 5)
		);
	}
	
	@Test
	public void previousGenerationsCannotBeCalculatedFromAMoreRecentOne() {
		repository.add("single", 4, Generation.withAliveCells(Cell.onXAndY(0, 1)));
		assertEquals(
			GenerationResult.miss(),
			repository.get("single", 3)
		);
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
		final List<List<Efficiency>> sequences = Collections.synchronizedList(new ArrayList<>());
		for (int i = 0; i < threads; i++) {
			String plane = "plane-" + i;
			List<Efficiency> sequence = Collections.synchronizedList(new ArrayList<>());
			sequences.add(sequence);
			if (i % 2 == 0) {
				executor.execute(new SinglePlaneUser(
					block,
					plane,
					(j) -> {
						GenerationResult result = repository.get(plane, j);
						assertEquals(
							block,
							result.generation()
						);
						sequence.add(result.efficiency());
					}
				));
			} else {
				executor.execute(new SinglePlaneUser(
					horizontalBar,
					plane,
					(j) -> {
						GenerationResult result = repository.get(plane, j);
						if (j % 2 == 0) {
							assertEquals(
								horizontalBar,
								result.generation()
							);
						} else {
							assertEquals(
								verticalBar,
								result.generation()
							);
						}
						sequence.add(result.efficiency());
					}
				));
			}
		}
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);
		for (List<Efficiency> sequence: sequences) {
			assertEquals(iterations, sequence.size());
			assertEquals(Efficiency.HIT, sequence.get(0));
			for (int i = 1; i < sequence.size(); i++) {
				assertEquals(Efficiency.PARTIAL_HIT, sequence.get(i));
			}
		}
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
