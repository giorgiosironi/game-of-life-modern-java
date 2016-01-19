package com.giorgiosironi.gameoflife.domain;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

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

	private Map<String, NavigableMap<Integer,Generation>> contents = new ConcurrentHashMap<>();
	private Logger logger = LoggerFactory.getLogger(InMemoryGenerationRepository.class);

	public void add(String name, int index, Generation generation) {
		this.contents.putIfAbsent(name, new ConcurrentSkipListMap<>());
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
				return GenerationResult.partialHit(current);
			}
		}
		
		logger.info(String.format("Cache miss: %s,%d", name, index));
		return GenerationResult.miss();
	}
}
