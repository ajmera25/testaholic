package pageobjects.desktop;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import core.BasePage;

public class GooglePage extends BasePage{

	@FindBy(xpath="//span[text()='Photos']")
	WebElement photos;
	
	@FindBy(xpath="//a[2]/div/div")
	List<WebElement> movieName_list;
	
	@FindBy(xpath="//div[contains(text(),'photos') and not(contains(text(),'Cover'))]")
	List<WebElement> number_list;
	
	String stepInFromPosts ="https://facebook.com/pg/STeP-IN-Forum-2063693617253588/posts/";
	public GooglePage(WebDriver driver) {
		super(driver);
	}

	public void search(String searchText) throws Exception {
		try {
			webDriverClient.waitForElementToBeClickable("//input[@type='text']");
			webDriverClient.setTextAndEnter("//input[@type='text']", searchText);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnResultWithText(String textDescription) throws Exception{
		String xpath = "//span[contains(.,'"+textDescription+"')]/parent::div/parent::div/preceding-sibling::div/a";
		if(webDriverClient.isWebElementDisplayed(xpath)) {
		webDriverClient.click(xpath);
	}else {
		Reporter.log("25000 test Professionals text not present",true);
		webDriverClient.setURL(stepInFromPosts);
	}}
	
}