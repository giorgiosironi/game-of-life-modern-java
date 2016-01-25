package com.giorgiosironi.gameoflife.domain;

public interface GenerationRepository {
	public void add(String name, int index, Generation single);
	public GenerationResult get(String name, int index);
	
	final class GenerationResult {
		enum Efficiency {
			MISS,
			HIT,
			PARTIAL_HIT
		}

		private final Generation generation;
		private final Efficiency efficiency;
		private final int calculations;
		
		public static GenerationResult miss() {
			return new GenerationResult(null, Efficiency.MISS, 0);
		}
		
		public static GenerationResult hit(Generation generation) {
			return new GenerationResult(generation, Efficiency.HIT, 0);
		}
		
		public static GenerationResult partialHit(Generation generation, int calculations) {
			return new GenerationResult(generation, Efficiency.PARTIAL_HIT, calculations);
		}
		
		private GenerationResult(Generation generation, Efficiency efficiency, int calculations) {
			this.generation = generation;
			this.efficiency = efficiency;
			this.calculations = calculations;
		}
		
		public Generation generation() {
			return generation;
		}
		
		public Efficiency efficiency() {
			return efficiency;
		}
		
		public int calculations() {
			return calculations;
		}
		
		@Override
		public boolean equals(Object anotherObject) {
			if (!(anotherObject instanceof GenerationResult)) {
				return false;
			}
			
			GenerationResult another = (GenerationResult) anotherObject;
			if (generation == null) {
				return efficiency.equals(another.efficiency)
					&& calculations == another.calculations;
			}
			
			// TODO: duplication in equals
			return generation.equals(another.generation)
				&& efficiency.equals(another.efficiency)
				&& calculations == another.calculations;			
		}
		
		@Override
		public int hashCode() {
			// TODO: better way to calculate hashCode() for int?
			return generation.hashCode()
				+ 31 * efficiency.hashCode()
				+ 17 * Integer.valueOf(calculations).hashCode();
		}
		
		public String toString() {
			return "" 
				+ (generation != null ? generation : "EMPTY") 
				+ ": " 
				+ efficiency 
				+ (calculations > 0 ? " " + calculations : "");
		}
	}
}