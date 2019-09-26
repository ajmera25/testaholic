package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.AppiumDriver;

public class BaseTest {
	public String teamName = "Testaholic";
	public WebDriver driver;
	public AppiumDriver appiumDriver;
	public static Properties Config;
	String platform;
	
	protected BaseTest(String platform){
		this.platform = platform;
	}

	@BeforeSuite
	public void suiteSetUp() {
		String configFilePath  =  System.getProperty("user.dir")+ "/src/test/resources/config.properties";
		Config = new Properties();
		try {
			Config.load(new FileInputStream(configFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@BeforeClass
	public void beforeClass() {
		DriverManagerFactory driverFactory = new DriverManagerFactory();
		if (platform.equalsIgnoreCase("desktopWeb")) {
			driverFactory.initializeDriver("desktopWeb");
			driver = driverFactory.getDesktopWebDriver();
			driver.get(Config.getProperty("baseUrl"));
		} else {
			driverFactory.initializeDriver("mobileWeb");
			appiumDriver = driverFactory.getAppiumDriver();
			appiumDriver.get(Config.getProperty("baseUrl"));
		}		
	}

	@AfterClass
	public void afterClass() throws Exception {
		try{
			if(driver!=null)
				driver.quit();
			if(appiumDriver!=null)
				appiumDriver.quit();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*@AfterMethod
	public void takeScreenShotOnFaiStepInForum_FB_DesktopWeblure(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			System.out.println(testResult.getStatus());
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String filePath = System.getProperty("user.dir") + "/src/test/resources/screenshots/"+testResult.getName()+".png";
			FileUtils.copyFile(scrFile, new File(filePath));
	   }        
	}*/
}
