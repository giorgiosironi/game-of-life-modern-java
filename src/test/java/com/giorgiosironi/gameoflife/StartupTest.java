package com.giorgiosironi.gameoflife;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class StartupTest {
		
	private List<Application> applications = new ArrayList<Application>();

	@After
	public void tearDown() {
		applications.forEach((Application a) -> a.stop()); 
	}

	@Test
	public void onlyOneApplicationCanStartAtATime() throws InterruptedException {
		Application original = startAnApplication("Original");
		assertTrue("The original application should be running", original.isRunning());
		
		Application duplicate = startAnApplication("Duplicate");
		assertFalse("The duplicate application should not be able to correctly start", duplicate.isRunning());
	}
	
	private Application startAnApplication(String threadName) throws InterruptedException {
		Application application = new Application();
		Thread applicationThread = new Thread(application);
		applicationThread.setName(threadName);
		applicationThread.start();
		application.waitForStartup();
		applications.add(application);
		return application;
	}

}
