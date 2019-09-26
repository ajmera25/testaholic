package pageobjects.mobile;

import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import core.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class GooglePage extends BasePage{

	public GooglePage(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
	}

	@FindBy(xpath = "//input[@name='q']")
	MobileElement txt_SearchText;

	@FindBy(xpath = "//div[text() = 'All']")
	MobileElement lbl_AllCard;

	String strToSearch = "step-in forum facebook";

	@FindBy(xpath = "//div[contains(text(), '25000 test professionals')]/parent::div/parent::div/parent::div//a")
	MobileElement lnk_FBStepInForum;
	
	String stepInFromPosts ="https://m.facebook.com/pg/STeP-IN-Forum-2063693617253588/posts/";
	
    public boolean searchOnGoogle() throws Exception {
       return mobileWebDriverClient.setTextAndEnter(txt_SearchText, strToSearch);
    }

	public boolean clickFbPost() throws Exception {
		if(mobileWebDriverClient.isMobileElementDisplayed(lnk_FBStepInForum)) {
		return mobileWebDriverClient.click(lnk_FBStepInForum);
		}else {
			Reporter.log("25000 test Professionals text not present",true);
			mobileWebDriverClient.setURL(stepInFromPosts);
			return true;
		}
		
	}

    
}
