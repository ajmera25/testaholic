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
	public boolean navigateToPosts() throws Exception {
		webDriverClient.click("//span[text()='Posts']");
		return webDriverClient.getCurrentURL().contains("posts");
		
	}

	public boolean navigateToAlbums() throws Exception {
		webDriverClient.scrollElementToCentre(photos);
		webDriverClient.click("//span[text()='Photos']");

		webDriverClient.click("//div[text()='Albums']/parent::div/following-sibling::div/a/div[text()='See All']");
	return webDriverClient.getCurrentURL().contains("tab=albums");
	}
	
	public HashMap<String, Integer> getAllAlbumNames() throws Exception {
		HashMap<String,Integer> albumDetails = new HashMap<>();
		for(int i=0;i<movieName_list.size();i++) {
			String albumName=movieName_list.get(i).getText();
			String number_of_photos=number_list.get(i).getText().split(" ")[0];
			int noOfPhotos = Integer.valueOf(number_of_photos);
			albumDetails.put(albumName, noOfPhotos);
		}
		return albumDetails;
	}
	
	public boolean isStepInForumFBPageDisplayed() throws Exception {
		return webDriverClient.isWebElementDisplayed("//a[@class='_64-f' and @href='https://www.facebook.com/STeP-IN-Forum-2063693617253588/']");
		
	}
}