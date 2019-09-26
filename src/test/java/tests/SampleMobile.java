package tests;

import org.testng.annotations.Test;

import core.BaseTest;
import core.MobileWebDriverClient;
import pageobjects.mobile.SamplePageObject;

public class SampleMobile extends BaseTest{
	
	@Test
    public void method() throws Exception{
//		appiumDriver.get("https://www.google.com");
		SamplePageObject obj = new SamplePageObject(appiumDriver);
		obj.scroll();
    }
	
	
}
