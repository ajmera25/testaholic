package tests;

import org.testng.annotations.Test;

import core.BaseTest;
import pageobjects.mobile.MobilePageObjects;
import utilities.FileUtils;

public class StepInForum_FB_Mobile extends BaseTest{
	
	@Test
    public void method() throws Exception{
//		appiumDriver.get("https://www.google.com");
		MobilePageObjects mpo = new MobilePageObjects(appiumDriver);
		mpo.searchOnGoogle();
		mpo.clickFbPost();
		mpo.openFbPost();
		mpo.openPhotosPost();
		mpo.clickImage();
		int imageCounter=1;
		FileUtils fileUtils = new FileUtils();
		while(imageCounter<=5) {
			String filePath = System.getProperty("user.dir") + "/src/test/resources/mobilephotos/" + imageCounter + ".jpg";
			int size = fileUtils.getFileSizeInKb(filePath);
		}
    }
	
	
}
