package tests;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.BaseTest;
import pageobjects.desktop.SamplePageObject;

public class SampleWeb extends BaseTest{

    @Test
    public void stepInFB() throws Exception{
        SamplePageObject desktop = new SamplePageObject(driver);
        desktop.search("step-in forum facebook");
        desktop.clickOnResultWithText("25000 test professionals");
        Assert.assertTrue(desktop.isStepInForumFBPageDisplayed(),"Error! Unable to navigate to Step In forum facebook page");
        Assert.assertTrue(desktop.navigateToPosts(),"Unable to navigate to posts");
        Assert.assertTrue(desktop.navigateToAlbums(),"Unable to navigate to all albums");
        HashMap<String, Integer> finnall = desktop.getAllAlbumNames();
        System.out.println("Albums: \n"+finnall);
    }
	
}
