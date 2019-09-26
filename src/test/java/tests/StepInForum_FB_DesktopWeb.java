package tests;

import java.util.HashMap;

import core.APIHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import core.BaseTest;
import pageobjects.desktop.FacebookPage;
import pageobjects.desktop.GooglePage;
import utilities.FileUtils;
import utilities.JsonTemplate;

public class StepInForum_FB_DesktopWeb extends BaseTest{
	
	GooglePage google = null;
	FacebookPage facebook = null;
	HashMap<String, Integer> albumNames;

	@Test
    public void stepInFB() throws Exception{
    	google = new GooglePage(driver);
    	facebook = new FacebookPage(driver);
    	
    	google.search("step-in forum facebook");
    	google.clickOnResultWithText("25000 test professionals");
        Assert.assertTrue(facebook.isStepInForumFBPageDisplayed(),"Error! Unable to navigate to Step In forum facebook page");
        Assert.assertTrue(facebook.navigateToPosts(),"Unable to navigate to posts");
    }
    
    @Test(dependsOnMethods = "stepInFB")
	public void verifyDownloadPhotos() {
		Assert.assertTrue(facebook.downloadPhotos(), "Failed to download photos");
		Assert.assertTrue(facebook.verifyPhotoSize(), "Photo size is not as expected");
	}
	
    @Test(dependsOnMethods = "verifyDownloadPhotos")
	public void getAlbumNamesAndPhotoCount() throws Exception {
    	Assert.assertTrue(facebook.navigateToAlbums(),"Unable to navigate to all albums");
        this.albumNames= facebook.getAllAlbumNames();
        Assert.assertFalse(albumNames.isEmpty(), "Failed to get album names");
	}

	@Test(dependsOnMethods = "getAlbumNamesAndPhotoCount" )
	public void verifyFileUploaded(){
		String fileName = new FileUtils().createJSONFile(new JsonTemplate(teamName, albumNames).getJsonString());
		APIHelper apiHelper = new APIHelper();
		String response = apiHelper.upload(fileName);
		Assert.assertTrue(response.contains(teamName),"Team name is not present in the response => "+response);
	}

}
