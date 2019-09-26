package tests;

import java.util.HashMap;

import org.testng.annotations.Test;

import core.BaseTest;
import pageobjects.desktop.SamplePageObject;

public class SampleWeb extends BaseTest{

    @Test
    public void stepInFB() throws Exception{
        SamplePageObject obj = new SamplePageObject(driver);
        obj.search("step-in forum facebook");
        obj.clickOnResultWithText("25000 test professionals");
        obj.navigateToPosts();
        obj.navigateToPhotos();
        HashMap<String, Integer> finnall = obj.getAllAlbumNames();
        System.out.println("Albums: \n"+finnall);
    }
	
}
