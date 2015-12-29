package com.giorgiosironi.gameoflife;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class StartupTest {
		
	private List<EmbeddedJettyApplication> applications = new ArrayList<EmbeddedJettyApplication>();

	@After
	public void tearDown() {
		applications.forEach((EmbeddedJettyApplication a) -> a.stop()); 
	}

	@Test
	public void onlyOneApplicationCanStartAtATime() throws InterruptedException {
		EmbeddedJettyApplication original = startAnApplication("Original");
		assertTrue("The original application should be running", original.isRunning());
		
		EmbeddedJettyApplication duplicate = startAnApplication("Duplicate");
		assertFalse("The duplicate application should not be able to correctly start", duplicate.isRunning());
	}
	
	private EmbeddedJettyApplication startAnApplication(String threadName) throws InterruptedException {
		EmbeddedJettyApplication application = new EmbeddedJettyApplication();
		Thread applicationThread = new Thread(application);
		applicationThread.setName(threadName);
		applicationThread.start();
		application.waitForStartup();
		applications.add(application);
		return application;
	}

}
