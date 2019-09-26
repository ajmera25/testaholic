package tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.BaseTest;
import pageobjects.desktop.FacebookPage;
import pageobjects.desktop.GooglePage;

public class StepInForum_FB_DesktopWeb extends BaseTest{
	
	GooglePage google = null;
	FacebookPage facebook = null;
	
    @Test
    public void stepInFB() throws Exception{
    	google = new GooglePage(driver);
    	facebook = new FacebookPage(driver);
    	google.search("step-in forum facebook");
    	google.clickOnResultWithText("25000 test professionals");
        Assert.assertTrue(facebook.isStepInForumFBPageDisplayed(),"Error! Unable to navigate to Step In forum facebook page");
        //Assert.assertTrue(desktop.navigateToPosts(),"Unable to navigate to posts");
    }
    
    @Test(dependsOnMethods = "stepInFB")
	public void verifyDownloadPhotos() {
		Assert.assertTrue(facebook.downloadPhotos(), "Failed to download photos");
		Assert.assertTrue(facebook.verifyPhotoSize(), "Photo size is not as expected");
	}
	
    @Test(dependsOnMethods = "verifyDownloadPhotos")
	public void getAlbumNamesAndPhotoCount() throws Exception {
    	Assert.assertTrue(facebook.navigateToAlbums(),"Unable to navigate to all albums");
        HashMap<String, Integer> albumNames = facebook.getAllAlbumNames();
        System.out.println("Albums: \n"+albumNames);
	}
    
}
