package pageobjects.mobile;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

import core.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import utilities.FileUtils;

public class FacebookPage extends BasePage{
	
	public FacebookPage(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
	}


	@FindBy(xpath = "//a/span[text()='Posts']")
	MobileElement lbl_FBPosts;

	@FindBy(xpath = "//a/span[text()='Reviews']")
	MobileElement lbl_FBReviews;

	@FindBy(xpath = "//div[contains(text(), '+')]/parent::div/parent::a/parent::div/a")
	MobileElement lnk_MoreThan4Photos;

	@FindBy(xpath = "//*[@id='popup_xout']")
	MobileElement lnk_closePopup;

	String strMoreAlbums  = "//a[contains(@href, '/albums')]";

	String strFullSize  = "//a[text()='View full size']";

	String strNext = "//a[@data-sigil='touchable'][last()]";

	@FindBy(xpath = "//span[text()='Photos']")
	MobileElement lbl_FBPhotos;

	@FindBy(xpath = "//span[text()='See All']")
	MobileElement lbl_FBSeeAll;

	String strAllAlbums = "//div[@class = '%s']"; 

	String strAllPhotosCount = "(//div[@class = '%s']/parent::div/span/span)[%i]"; 

	String strGetClass = "//div[text() = 'Profile Pictures']";

	@FindBy(id = "m_login_email")
	MobileElement txt_mUsername;
	
	@FindBy(id = "m_login_password")
	MobileElement txt_mPassword;
	
	String fbUrl = "https://m.facebook.com";
	String fbLoginNotNow =".//span[text()='Not Now']";
	
	String stepInFromPhotos ="https://m.facebook.com/STeP-IN-Forum-2063693617253588/photos";
	
	public HashMap<String, Integer> getListOfAlbumns(){
		HashMap<String, Integer> hMapAlbum = new HashMap<>();
		try{
			mobileWebDriverClient.setURL(fbUrl);
			doFBMLogin();
			mobileWebDriverClient.setURL(stepInFromPhotos);
			mobileWebDriverClient.click(lbl_FBSeeAll);
			//mobileWebDriverClient.scroll(5);
			mobileWebDriverClient.JSClick(strMoreAlbums);
			String strClassName = mobileWebDriverClient.getAttribute(strGetClass, "class");

			List<MobileElement> allAlbums = mobileWebDriverClient.findElements(strAllAlbums.replace("%s", strClassName));
			int i = 1;
			for(MobileElement album: allAlbums){         
				hMapAlbum.put(album.getText(), 
						Integer.valueOf(mobileWebDriverClient.getText(strAllPhotosCount.replace("%s", strClassName).replace("%i", String.valueOf(i))).split(" ")[0]));
				i++;
			}
			return hMapAlbum;
		}catch(Exception e){
			e.printStackTrace();
		}
		return hMapAlbum;        	
	}

	public void doFBMLogin() throws Exception{
		try{
			mobileWebDriverClient.waitForVisibilityOfElement(txt_mUsername);
			mobileWebDriverClient.setText(txt_mUsername, "pratik3");
			mobileWebDriverClient.setText(txt_mPassword, "AJmera@2428");
			mobileWebDriverClient.sendEnterKey();
			mobileWebDriverClient.JSClick(fbLoginNotNow);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean openFbPost() throws Exception {
		try{
			mobileWebDriverClient.click(lbl_FBReviews);
			return mobileWebDriverClient.click(lbl_FBPosts);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean openPhotosPost() throws Exception{
		try{
			if(mobileWebDriverClient.isMobileElementDisplayed(lnk_closePopup)){
				mobileWebDriverClient.click(lnk_closePopup);
			}    
			return mobileWebDriverClient.click(lnk_MoreThan4Photos);
		}catch(Exception e){
			e.printStackTrace();
		}    
		return false;
	}

    public void clickImage() throws Exception {
        int imageCounter=1;
        FileUtils fileUtils = new FileUtils();
        while(imageCounter<=5) {    
            mobileWebDriverClient.waitForVisibilityOfElementLocatedBy(strFullSize);
            String src = mobileWebDriverClient.getAttribute(strFullSize, "href");
            Reporter.log(src,true);
            fileUtils.downloadImage(src,"Mobile",imageCounter);
            imageCounter++;
            mobileWebDriverClient.JSClick(strNext);
        }
    }
}
