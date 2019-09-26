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

	public void search() throws Exception {
		webDriverClient.setTextAndEnter("//input[@type='text']", "step-in forum facebook");
		webDriverClient.click("//*[@id='rso']/div/div/div[6]/div/div/div[1]/a/h3/div");
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