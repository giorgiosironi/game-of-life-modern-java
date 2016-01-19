package com.giorgiosironi.gameoflife.domain;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InMemoryGenerationRepository implements GenerationRepository {
	private Map<String, NavigableMap<Integer,Generation>> contents = new ConcurrentHashMap<>();
	private Logger logger = LoggerFactory.getLogger(InMemoryGenerationRepository.class);

	public void add(String name, int index, Generation generation) {
		this.contents.putIfAbsent(name, new ConcurrentSkipListMap<>());
		// use to demonstrate concurrency bug
		//this.contents.putIfAbsent(name, new TreeMap<>());
		this.contents.get(name).put(index, generation);
	}

	public GenerationResult get(String name, int index) {
		NavigableMap<Integer,Generation> generations = this.contents.get(
			name
		);
		
		if (generations != null) {
			Entry<Integer, Generation> best = generations.floorEntry(index);
			if (best == null) {
				// cache miss, fall through
			} else if (best.getKey().equals(index)) {
				logger.info(String.format("Cache hit: %s,%d", name, index));
				return GenerationResult.hit(best.getValue());
			} else {
				logger.info(String.format("Partial cache hit: %s,%d", name, index));
				Generation current = best.getValue();
				for (int i = best.getKey() + 1; i <= index; i++) {
					current = current.evolve();
				}
				generations.put(index, current);
				return GenerationResult.partialHit(current, index - best.getKey());
			}
		}
		
		logger.info(String.format("Cache miss: %s,%d", name, index));
		return GenerationResult.miss();
	}
	
	public int size() {
		return contents.values()
			.stream()
			.map((generations) -> generations.size())
			.reduce(0, (a, b) -> a + b);
	}
}
