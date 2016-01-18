package com.giorgiosironi.gameoflife.domain;

import java.util.HashMap;
import java.util.Map;

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

	private Map<Key, Generation> contents = new HashMap<Key,Generation>();
	private Logger logger = LoggerFactory.getLogger(InMemoryGenerationRepository.class);

	public void add(String name, int index, Generation single) {
		this.contents.put(
			Key.fromNameAndIndex(name, index),
			single
		);
	}

	public Generation get(String name, int index) {
		Generation generation = this.contents.get(
			Key.fromNameAndIndex(name, index)
		);
		if (generation != null) {
			logger.info(String.format("Cache hit: %s,%d", name, index));
		} else {
			logger.info(String.format("Cache miss: %s,%d", name, index));
		}
		return generation;
	}
}
