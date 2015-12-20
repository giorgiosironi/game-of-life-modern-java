package com.giorgiosironi.gameoflife;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BrowserAcceptanceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.google.com");
		driver.quit();
	}

}
