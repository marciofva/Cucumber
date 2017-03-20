package com.steps;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.pageObjects.HomePage;
import com.pageObjects.SignIn;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public abstract class Hooks {
	
	protected  WebDriver driver;
	private String chromeDriverLocation;
	private String browser = "chrome";
	
	protected String title = "ToDo Rails and Angular";
	
	//Objects
	protected  SignIn signInObj;
	protected HomePage homePageObj;

	
	@Before
	public void setUp(){
		
		System.out.println("setUp");

		String currentDir = System.getProperty("user.dir");
		
	      switch(browser.toLowerCase()) {
	         case "chrome" :
	     		chromeDriverLocation = currentDir + "/src/main/resources/drivers/chromedriver.exe";
	    		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
	    		driver = new ChromeDriver();
	            break;
	         case "ie" :
	     		chromeDriverLocation = currentDir + "/src/main/resources/drivers/IEDriverServer.exe";
	    		System.setProperty("webdriver.ie.driver", chromeDriverLocation);
	    		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
	    		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	    		capabilities.setCapability(InternetExplorerDriver.
	    				  INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
	    		InternetExplorerDriver driverIE = new InternetExplorerDriver(capabilities); 
	    		driver = driverIE;
	            break;
	         case "firefox" :
	     		chromeDriverLocation = currentDir + "/src/main/resources/drivers/geckodriver.exe";
	    		System.setProperty("webdriver.gecko.driver", chromeDriverLocation);
	    		DesiredCapabilities capabilitiesf=DesiredCapabilities.firefox();
	    		capabilitiesf.setBrowserName("FIREFOX");
	    		capabilitiesf.setPlatform(org.openqa.selenium.Platform.WINDOWS);
	    		capabilitiesf.setCapability("marionette", true);
	    		WebDriver driverFirefox = new FirefoxDriver(capabilitiesf);
	    		driver = driverFirefox;
	            break;
	          default:
	        	  Assert.assertTrue("Browser '" + browser + "' is INVALID.", false);
	      }
	      
			driver.manage().window().maximize();
		}
	
	@After
	public void shotDown(){
		System.out.println("shotDown");
		driver.quit();
	}
	
}
