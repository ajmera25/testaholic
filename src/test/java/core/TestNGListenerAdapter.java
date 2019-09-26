package core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestNGListenerAdapter implements ITestListener {

	protected static ExtentReports reports;
	protected static ExtentTest test;

	public void onTestStart(ITestResult result) {
		System.out.println("on test start");
		test = reports.startTest(result.getMethod().getMethodName());
		test.log(LogStatus.INFO, result.getMethod().getMethodName() + "test is started");
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("On test success");
		test.log(LogStatus.PASS, result.getMethod().getMethodName() + "test is passed");
	}
	
	public void onTestFailure(ITestResult result) {
		System.out.println("On test failure");
		String platform = result.getInstanceName();
		DriverManagerFactory driverFactory = new DriverManagerFactory();
		WebDriver driver;
		if (platform.contains("Desktop")) {
			driver = driverFactory.getDesktopWebDriver();
		} else {
			driver = driverFactory.getAppiumDriver();
		}
		
		test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed");
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "/src/test/resources/screenshots/" + result.getMethod().getMethodName() + ".png"));
			String file = test.addScreenCapture(System.getProperty("user.dir") + "/src/test/resources/screenshots/" + result.getMethod().getMethodName() + ".png");
			test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", file);
			test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", result.getThrowable().getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		System.out.println("On test skipped");
		test.log(LogStatus.SKIP, result.getMethod().getMethodName() + "test is skipped");
	}
	
	public void onStart(ITestContext context) {
		System.out.println("On start");
		reports = new ExtentReports(System.getProperty("user.dir") + "/target/" + new SimpleDateFormat("yyyy-MM-dd hh-mm").format(new Date()) + "_Report.html");
	}
	
	public void onFinish(ITestContext context) {
		System.out.println("On finish");
		reports.endTest(test);
		reports.flush();
	}

}
