package pageobjects.desktop;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import core.BasePage;

public class GooglePage extends BasePage{

	@FindBy(xpath="//span[text()='Photos']")
	WebElement photos;
	
	@FindBy(xpath="//a[2]/div/div")
	List<WebElement> movieName_list;
	
	@FindBy(xpath="//div[contains(text(),'photos') and not(contains(text(),'Cover'))]")
	List<WebElement> number_list;
	public GooglePage(WebDriver driver) {
		super(driver);
	}

	public void search(String searchText) throws Exception {
		webDriverClient.setTextAndEnter("//input[@type='text']", searchText);

	}

	public void clickOnResultWithText(String textDescription) throws Exception{
		String xpath = "//span[contains(.,'"+textDescription+"')]/parent::div/parent::div/preceding-sibling::div/a";
		webDriverClient.click(xpath);
	}
	
}