package core;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;

@SuppressWarnings("rawtypes")
public class DriverManagerFactory {

	
	private static ThreadLocal<AppiumDriver> threadAppiumDriver = new ThreadLocal<AppiumDriver>();
	private static ThreadLocal<RemoteWebDriver> threadDesktopWebDriver = new ThreadLocal<RemoteWebDriver>();
	String hub = BaseTest.Config.getProperty("hub");

	public AppiumDriver getAppiumDriver() {
		AppiumDriver wdriver = threadAppiumDriver.get();
		return wdriver;
	}
	
	public WebDriver getDesktopWebDriver() {
		WebDriver wdriver = threadDesktopWebDriver.get();
		return wdriver;
	}
	
	public static void setAppiumDriver(AppiumDriver driver) {
		threadAppiumDriver.set(driver);
	}
	
	public static void setDesktopWebDriver(RemoteWebDriver driver) {
		threadDesktopWebDriver.set(driver);
	}
	
	public void initializeDriver(String platform)  {
		if(platform.equals("desktop")) {
			initializeChromeWebDriver();
		} else if(platform.equals("mobileWeb")) {
			initializeMobileWebDriver();
		} else if(platform.equals("native")) {
			initializeNativeDriver();
		}
	}
	
	public void initializeChromeWebDriver()  {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		ChromeOptions options = new ChromeOptions();
	    capabilities.setCapability("platformName", "WINDOWS");
		options.addArguments("start-maximized");
		options.addArguments("--disable-infobars");
		options.addArguments("--dns-prefetch-disable");
		options.merge(capabilities);
		try {
			setDesktopWebDriver(new RemoteWebDriver(new URL(hub), options));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeMobileWebDriver() {
		ChromeOptions options = new ChromeOptions();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
		capabilities.setCapability("language", "en");
		capabilities.setCapability("locale", "US");
		capabilities.setCapability("deviceName", "9243934");
        capabilities.setCapability("platformName", "Android");
		try {
			setAppiumDriver(new AppiumDriver(new URL("http://192.168.43.141:4723/wd/hub"), capabilities));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeNativeDriver() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", BaseTest.Config.getProperty("deviceName"));
        capabilities.setCapability("platformVersion", BaseTest.Config.getProperty("platformVersion"));
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", BaseTest.Config.getProperty("appPackage"));
        capabilities.setCapability("appActivity", BaseTest.Config.getProperty("appActivity"));
		try {
			setAppiumDriver(new AppiumDriver(new URL("http://192.168.43.141:4723/wd/hub"), capabilities));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeAllDriver() {
		 if(getAppiumDriver()!=null) {
			getAppiumDriver().quit();
		}
		if (getDesktopWebDriver()!=null)  {
			getDesktopWebDriver().quit();
		}
		
	}
	
}
