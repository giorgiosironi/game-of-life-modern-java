package com.giorgiosironi.gameoflife.domain;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGenerationRepository {
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

	private Map<Key, Generation> contents;
	
	public InMemoryGenerationRepository() {
		this.contents = new HashMap<Key,Generation>();
	}

	public void add(String name, int index, Generation single) {
		this.contents.put(
			Key.fromNameAndIndex(name, index),
			single
		);
	}

	public Generation get(String name, int index) {
		return this.contents.get(
			Key.fromNameAndIndex(name, index)
		);
	}
}
