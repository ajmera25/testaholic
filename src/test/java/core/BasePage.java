package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BasePage {
	public WebDriverClient webDriverClient = null;

	public MobileWebDriverClient mobileWebDriverClient = null;
	DriverManagerFactory driverManagerFactory = null;
	@SuppressWarnings("rawtypes")
	protected AppiumDriver appiumDriver = null;
	protected WebDriver driver = null;
	public String currentURL = "";
	
	public BasePage(WebDriver driver) {
        this.driver = driver;
		PageFactory.initElements(driver, this);
		webDriverClient = new WebDriverClient(driver);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BasePage(AppiumDriver appiumDriver) {
		//BasePage constructor for appium driver
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver), this);
        mobileWebDriverClient = new MobileWebDriverClient(appiumDriver);
    }
	
}