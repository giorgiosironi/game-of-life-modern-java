package com.giorgiosironi.gameoflife.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.giorgiosironi.gameoflife.domain.Plane.State;

public class ClassicRulesTest {

	private ClassicRules rules;
	
	@Before
	public void setUp() {
		rules = new ClassicRules();
	}
	
	@Test
	public void cellsDieBySolitude() {
		assertEquals(State.DEAD, rules.nextState(State.ALIVE, 0));
		assertEquals(State.DEAD, rules.nextState(State.ALIVE, 1));
	}
	
	@Test
	public void cellsDieOfOvercrowding() {
		assertEquals(State.DEAD, rules.nextState(State.ALIVE, 7));
		assertEquals(State.DEAD, rules.nextState(State.ALIVE, 8));
	}
	
	@Test
	public void cellsComeToLifeOfWarmth() {
		assertEquals(State.ALIVE, rules.nextState(State.DEAD, 3));
	}

}
