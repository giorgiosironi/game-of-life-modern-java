package com.giorgiosironi.gameoflife;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BrowserAcceptanceTest {

	private static WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void test() throws InterruptedException {
		driver.get("http://localhost:8080");
		WebElement element = driver.findElement(By.cssSelector("h1"));
		assertEquals("Game Of Life", element.getText());
	}

}
