package com.giorgiosironi.gameoflife.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryGenerationRepository implements GenerationRepository {
	private static final class Key {
		
		private String value;

		public static Key fromNameAndIndex(String name, int index) {
			return new Key(name + "/" + index);
		}
		
		private Key(String value) {
			this.value = value;
		}
		
		@Override
		public boolean equals(Object anotherObject) {
			if (!(anotherObject instanceof Key)) {
				return false;
			}
			Key another = (Key) anotherObject;
			
			return value.equals(another.value);
		}
		
		@Override
		public int hashCode() {
			return value.hashCode();
		}
	}

	private Map<String, Map<Integer,Generation>> contents = new ConcurrentHashMap<>();
	private Logger logger = LoggerFactory.getLogger(InMemoryGenerationRepository.class);

	public void add(String name, int index, Generation generation) {
		// TODO: use TreeMap and floorKey()
		this.contents.putIfAbsent(name, new ConcurrentHashMap<>());
		this.contents.get(name).put(index, generation);
	}

	public Generation get(String name, int index) {
		Map<Integer,Generation> generations = this.contents.get(
			name
		);
		
		if (generations != null) {
			Generation generation = generations.get(index);
			if (generation != null) {
				logger.info(String.format("Cache hit: %s,%d", name, index));
				return generation;
			} else {
				Optional<Integer> best = generations.keySet()
					.stream()
					.filter((candidate) -> candidate < index)
					.max((a, b) -> a - b);

				if (best.isPresent()) {
					logger.info(String.format("Partial cache hit: %s,%d", name, index));
					Generation current = generations.get(best.get());
					for (int i = best.get() + 1; i <= index; i++) {
						current = current.evolve();
					}
					return current;
				}
			}
		}
		
		logger.info(String.format("Cache miss: %s,%d", name, index));
		return null;
	}
}
