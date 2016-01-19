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
		
		public GenerationResult(Generation generation, Efficiency efficiency) {
			this.generation = generation;
			this.efficiency = efficiency;
		}
		
		public Generation generation() {
			return generation;
		}
		
		public Efficiency efficiency() {
			return efficiency;
		}
	}
}