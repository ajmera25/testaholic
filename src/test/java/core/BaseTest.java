package core;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.AppiumDriver;

public class BaseTest {

	public WebDriver driver;
	public AppiumDriver appiumDriver;

	public static Properties Config;

	@BeforeSuite
	public void suiteSetUp() {
		String configFilePath  =  System.getProperty("user.dir")+ "/src/test/resources/config.properties";
		Config = new Properties();
		try {
			Config.load(new FileInputStream(configFilePath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@BeforeClass
	public void beforeClass() {
		String platform = Config.getProperty("platform");
		DriverManagerFactory driverFactory = new DriverManagerFactory();
		if (platform.equalsIgnoreCase("desktop")) {
			driverFactory.initializeDriver("desktop");
			driver = driverFactory.getDesktopWebDriver();
			driver.get(Config.getProperty("baseUrl"));
		} else {
			driverFactory.initializeDriver("native");
			appiumDriver = driverFactory.getAppiumDriver();
		}		
	}

	@AfterClass
	public void afterClass() throws Exception {
		DriverManagerFactory dmf = new DriverManagerFactory();
		try{
			if(dmf.getDesktopWebDriver()!=null)
				dmf.getDesktopWebDriver().quit();
			if(dmf.getAppiumDriver()!=null)
				dmf.getAppiumDriver().quit();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
