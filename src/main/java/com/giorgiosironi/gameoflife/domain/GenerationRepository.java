package com.giorgiosironi.gameoflife.domain;

public interface GenerationRepository {
	public void add(String name, int index, Generation single);
	public GenerationResult get(String name, int index);
	
	class GenerationResult {
		enum Efficiency {
			MISS,
			HIT,
			PARTIAL_HIT
		}

		private final Generation generation;
		private final Efficiency efficiency;
		
		public static GenerationResult miss() {
			return new GenerationResult(null, Efficiency.MISS);
		}
		
		public static GenerationResult hit(Generation generation) {
			return new GenerationResult(generation, Efficiency.HIT);
		}
		
		public static GenerationResult partialHit(Generation generation) {
			return new GenerationResult(generation, Efficiency.PARTIAL_HIT);
		}
		
		private GenerationResult(Generation generation, Efficiency efficiency) {
			this.generation = generation;
			this.efficiency = efficiency;
		}
		
		public Generation generation() {
			return generation;
		}
		
		public Efficiency efficiency() {
			return efficiency;
		}
		
		@Override
		public boolean equals(Object anotherObject) {
			if (!(anotherObject instanceof GenerationResult)) {
				return false;
			}
			
			GenerationResult another = (GenerationResult) anotherObject;
			if (generation == null) {
				return efficiency.equals(another.efficiency);
			}
			
			return generation.equals(another.generation)
				&& efficiency.equals(another.efficiency);			
		}
		
		@Override
		public int hashCode() {
			return generation.hashCode()
				+ 31 * efficiency.hashCode();
		}
		
		public String toString() {
			return "" + (generation != null ? generation : "EMPTY") + ": " + efficiency;
		}
	}
}