package pageobjects.desktop;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import core.BasePage;
public class SamplePageObject extends BasePage{

	@FindBy(xpath="//span[text()='Photos']")
	WebElement photos;
	public SamplePageObject(WebDriver driver) {
		super(driver);
	}

	public void search(String searchText) throws Exception {
		webDriverClient.setTextAndEnter("//input[@type='text']", searchText);

	}

	public void clickOnResultWithText(String textDescription) throws Exception{
		String xpath = "//span[contains(.,'"+textDescription+"')]/parent::div/parent::div/preceding-sibling::div/a";
		webDriverClient.click(xpath);
	}
	public void navigateToPosts() throws Exception {
		webDriverClient.click("//span[text()='Posts']");
	}

	public void navigateToPhotos() throws Exception {
		webDriverClient.scrollElementToCentre(photos);
		webDriverClient.click("//span[text()='Photos']");

		webDriverClient.click("//div[text()='Albums']/parent::div/following-sibling::div/a/div[text()='See All']");
	}
}