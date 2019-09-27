package core;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestNGListenerAdapter implements ITestListener, ISuiteListener {

	protected static ExtentReports reports;
	protected static ExtentTest test;

	public void onTestStart(ITestResult result) {
		Reporter.log("Test started :: " + result.getMethod().getMethodName(),true);
		test = reports.startTest(result.getMethod().getMethodName());
		test.log(LogStatus.INFO, result.getMethod().getMethodName() + " Test Started");
		test.log(LogStatus.INFO, result.getMethod().getDescription());
	}

	public void onTestSuccess(ITestResult result) {
		Reporter.log("Test success :: " + result.getMethod().getMethodName(),true);
		test.log(LogStatus.PASS, result.getMethod().getMethodName() + " Test PASSED");
	}
	
	public void onTestFailure(ITestResult result) {
		Reporter.log("Test failure :: " + result.getMethod().getMethodName(),true);
		String platform = result.getInstanceName();
		DriverManagerFactory driverFactory = new DriverManagerFactory();
		WebDriver driver;
		if (platform.contains("Desktop")) {
			driver = driverFactory.getDesktopWebDriver();
		} else {
			driver = driverFactory.getAppiumDriver();
		}
		
		test.log(LogStatus.FAIL, result.getMethod().getMethodName() + " Test FAILED");
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "/target/screenshots/" + result.getMethod().getMethodName() + ".png"));
			String file = test.addScreenCapture(System.getProperty("user.dir") + "/target/screenshots/" + result.getMethod().getMethodName() + ".png");
			test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", file);
			test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", result.getThrowable().getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		Reporter.log("Test skipped :: " + result.getMethod().getMethodName(),true);
		test.log(LogStatus.SKIP, result.getMethod().getMethodName() + " Test SKIPPED");
	}
	
	public void onStart(ISuite context) {
		Reporter.log("-----------Suite started-----------");
		reports = new ExtentReports(System.getProperty("user.dir") + "/target/Report.html");
	}
	
	public void onFinish(ISuite context) {
		Reporter.log("-----------Suite finished---------------");
		reports.endTest(test);
		reports.flush();
	}

}
