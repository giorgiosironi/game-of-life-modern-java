package com.giorgiosironi.gameoflife;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BrowserAcceptanceTest {

	private WebDriver driver;
	private EmbeddedJettyApplication application;
	private Thread applicationThread;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		application = new EmbeddedJettyApplication();
		applicationThread = new Thread(application);
		applicationThread.setName("ApplicationTest");
		applicationThread.start();
		application.waitForStartup();
	}
	
	@After
	public void tearDown() {
		driver.quit();
		application.stop();
	}

	@Test
	public void testLoadsTheFirstGenerationInTheBrowserAndCanMakeItEvolve() throws InterruptedException {
		driver.get("http://localhost:8080/planes/a-block-and-bar");
		WebElement title = driver.findElement(By.cssSelector("h1"));
		assertEquals("Game Of Life", title.getText());
		WebElement generation = driver.findElement(By.cssSelector("table"));
		List<WebElement> cells = generation.findElements(By.cssSelector("td"));
		assertEquals("Should show a 10x10 window over the infinite plane", 100, cells.size());
		WebElement next = driver.findElement(By.cssSelector("a[rel~=next]"));
		next.click();
		assertTrue(
			"Should show the second generation but is pointing at " + driver.getCurrentUrl(),
			driver.getCurrentUrl().endsWith("/generation/1")
		);
		WebElement previous = driver.findElement(By.cssSelector("a[rel~=prev]"));
		previous.click();
		assertTrue(
			"Should show again the first generation but is pointing at " + driver.getCurrentUrl(),
			driver.getCurrentUrl().endsWith("/generation/0")
		);
	}

}
