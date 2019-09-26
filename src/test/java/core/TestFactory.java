package core;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import tests.StepInForum_FB_DesktopWeb;
import tests.StepInForum_FB_Mobile;


public class TestFactory {
	
	@Factory(dataProvider="getPlatform")
	public Object[] platformInvocationInstances(String platform) throws Exception {
		if(platform.equalsIgnoreCase("mobileWeb"))
			return new Object[] { new StepInForum_FB_Mobile() };
		else
			return new Object[] { new StepInForum_FB_DesktopWeb() };
		       
	}
	
	@DataProvider
	public static Object[][] getPlatform() {
		String platform = System.getProperty("Platform");
	         if (platform.equalsIgnoreCase("All")) {
	        	 return new Object[][] { {"mobileWeb"},{"desktopWeb"}};
	         } else if(platform.equalsIgnoreCase("desktopWeb")){
	        	 return new Object[][] { {"desktopWeb"}};
	         }else {
	        	 return new Object[][] {{"mobileWeb"}};
	         }
	}

}