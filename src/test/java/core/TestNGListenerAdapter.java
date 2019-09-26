package core;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TestNGListenerAdapter extends TestListenerAdapter implements ISuiteListener, ITestListener {

	public ThreadLocal<RemoteWebDriver> threadDriver = null;
	
	@Override
	public void onTestFailure(ITestResult result) {
		try {
			this.threadDriver = new ThreadLocal<RemoteWebDriver>();
			takeScreenShot(result, threadDriver.get());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		super.onTestFailure(result);
	}

	public static  void takeScreenShot(ITestResult result, WebDriver driver) {
		try {
			driver.switchTo().defaultContent();
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			ITestNGMethod testMethod = result.getMethod();
			File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			String nameScreenshot = ((RemoteWebDriver) driver).getCapabilities().getBrowserName() + "_" + testMethod.getTestClass().getRealClass().getSimpleName() + "_" + testMethod.getMethodName();
			String path = getFullPath(nameScreenshot);
			Reporter.log("Path in take screenshot" +path);
			FileUtils.copyFile(screenshot, new File(path));
			Reporter.log ("\n failure screenshot:");
			Reporter.log ("\n getFileName:" +getFileName(nameScreenshot));

			Reporter.log("<img src='../../screenShots/" + getFileName(nameScreenshot) +"'"+ " target='_blank' >" + "<br>" + getFileName(nameScreenshot) );

		}catch (IOException e) {
			e.printStackTrace();
		}
	}	

	private static String getFileName(String nameTest) throws IOException {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss");	
		return dateFormat.format(date) + "_" + nameTest + ".png";
	}

	private static  String getFullPath(String nameTest) throws IOException {
		File directory = new File(".");
		System.out.println("Path in getpath" +directory.getCanonicalPath());
		String newFileNamePath = directory.getCanonicalPath() + "//screenShots//" + getFileName(nameTest);
		return newFileNamePath;
	}
}
