package com.giorgiosironi.gameoflife.domain;

public interface GenerationRepository {
	public void add(String name, int index, Generation single);
	public Generation get(String name, int index);
}