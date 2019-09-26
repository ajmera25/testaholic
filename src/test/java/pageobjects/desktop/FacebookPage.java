package pageobjects.desktop;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import core.BasePage;
import utilities.FileUtils;

public class FacebookPage extends BasePage{
	
	@FindBy(xpath="//span[text()='Photos']")
	WebElement photos;
	
	@FindBy(xpath="//a[2]/div/div")
	List<WebElement> movieName_list;
	
	@FindBy(xpath="//div[contains(text(),'photos') and not(contains(text(),'Cover'))]")
	List<WebElement> number_list;
	
	public FacebookPage(WebDriver driver) {
		super(driver);
	}

	public boolean downloadPhotos () {
		boolean bval = false;
		String firstPhoto = "//div[contains(text(),'+')]/parent::div/parent::div/ancestor::a/parent::div/a";
		try {
			webDriverClient.scrollWindow();
			webDriverClient.waitForElementToBeClickable("//a[text()='Not Now']");
			webDriverClient.click("//a[text()='Not Now']");
			webDriverClient.scrollWindowVerticallyToClickableElement(webDriverClient.findElement(firstPhoto));
			webDriverClient.click(firstPhoto);
			webDriverClient.waitForVisibilityOfElementLocatedBy("//img[@class='spotlight']");
			FileUtils fileUtils = new FileUtils();
			String src = "";
			for(int i=1; i<=5; i++) {
				try {
					src = webDriverClient.findElement("//img[@class='spotlight']").getAttribute("src");
				}catch(StaleElementReferenceException ex) {
					src = webDriverClient.findElement("//img[@class='spotlight']").getAttribute("src");
				}
				
				bval = fileUtils.downloadImage(src,"Desktop", i);
				if(!bval) {
					return false;
				}
				webDriverClient.JSClick("//a[contains(@class,'snowliftPager next')]");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bval;
				
	}
	
	public boolean verifyPhotoSize() {
		boolean bval = false;
		for(int i=1; i<=5; i++) {
			String filePath = System.getProperty("user.dir") + "/target/DesktopPhotos/" +i+ ".jpg";
			File file = new File(filePath);
			bval = file.length() / 1024 > 0;
			if(!bval) {
				return false;
			}
		}
		webDriverClient.sendEscapeKey();
		return bval;
	}
	
	public boolean navigateToAlbums() throws Exception {
		webDriverClient.scrollWindowVerticallyToClickableElement(photos);
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
	public boolean navigateToPosts() throws Exception {
		webDriverClient.click("//span[text()='Posts']");
		Thread.sleep(5000);
		return webDriverClient.getCurrentURL().contains("posts");
		
	}
}
