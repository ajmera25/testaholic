package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import core.BaseTest;
import pageobjects.mobile.MobilePageObjects;
import utilities.FileUtils;
public class StepInForum_FB_Mobile extends BaseTest{
    
    MobilePageObjects mobileFlow = new MobilePageObjects(appiumDriver);
    @Test
    public void test001_StepInFB() throws Exception{
        Assert.assertTrue(mobileFlow.searchOnGoogle(), "Unable to search in Google");
        Assert.assertTrue(mobileFlow.clickFbPost(), "Unable to click on FB Post");
        Assert.assertTrue(mobileFlow.openFbPost(), "Unable to Open FB Post");
        Assert.assertTrue(mobileFlow.openPhotosPost(), "Unable to Open Photos");
    }
    
    @Test
    public void test002_DownloadPhotosAndCheckSize() throws Exception{
        mobileFlow.clickImage();
        int imageCounter=1;
        FileUtils fileUtils = new FileUtils();
        while(imageCounter<=5) {
            String filePath = System.getProperty("user.dir") + "/src/test/resources/mobilephotos/" + imageCounter + ".jpg";
            int size = fileUtils.getFileSizeInKb(filePath);
            Assert.assertTrue(size > 0, "Image Size is not greater than O KB for Image: " +  imageCounter);
        }
    }
    
}


