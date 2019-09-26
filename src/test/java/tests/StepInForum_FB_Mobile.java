package tests;
import java.util.HashMap;

import core.APIHelper;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import core.BaseTest;
import pageobjects.mobile.MobilePageObjects;
import utilities.FileUtils;
import utilities.JsonTemplate;

public class StepInForum_FB_Mobile extends BaseTest{
	MobilePageObjects mobileFlow;
    HashMap<String, Integer> albumNames;
    
    @Test
    public void test001_StepInFB() throws Exception{
    	mobileFlow = new MobilePageObjects(appiumDriver);
        Assert.assertTrue(mobileFlow.searchOnGoogle(), "Unable to search in Google");
        Assert.assertTrue(mobileFlow.clickFbPost(), "Unable to click on FB Post");
        Assert.assertTrue(mobileFlow.openFbPost(), "Unable to Open FB Post");
        Assert.assertTrue(mobileFlow.openPhotosPost(), "Unable to Open Photos");
    }
    
    @Test
    public void test002_DownloadPhotosAndCheckSize() throws Exception{
    	SoftAssert ImageSizeVerification = new SoftAssert();
    	mobileFlow = new MobilePageObjects(appiumDriver);
        mobileFlow.clickImage();
        int imageCounter=1;
        FileUtils fileUtils = new FileUtils();
        while(imageCounter<=5) {
            String filePath = System.getProperty("user.dir") + "/src/test/resources/mobilephotos/" + imageCounter + ".jpg";
            int size = fileUtils.getFileSizeInKb(filePath);
            ImageSizeVerification.assertTrue(size > 0, "Image Size is not greater than O KB for Image: " +  imageCounter);
            imageCounter++;
        }
        ImageSizeVerification.assertAll();
        this.albumNames  = mobileFlow.getListOfAlbumns();
    }

    @Test(dependsOnMethods = "test002_DownloadPhotosAndCheckSize" )
    public void test003_verifyFileUploaded(){
        String fileName = new FileUtils().createJSONFile(new JsonTemplate(teamName, albumNames).getJsonString());
        APIHelper apiHelper = new APIHelper();
        String response = apiHelper.upload(fileName);
        Assert.assertTrue(response.contains(teamName),"Team name is not present in the response => "+response);
    }
}


