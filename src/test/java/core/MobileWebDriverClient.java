package core;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

public class MobileWebDriverClient {

	protected AppiumDriver<MobileElement> myDriver;
	private String myBrowser;
	private String myUrl;
	private String currentUrl;

	private final int implicitWait = 5000;
	private final int pageloadtimeout = 60;
	private final int WEBDRIVER_CLIENT_EXPLICIT_DELAY = 90;
	public WebDriverWait webdriverWait ;

	public MobileWebDriverClient(AppiumDriver<MobileElement> driver) {
		this.myDriver = driver;
		webdriverWait = new WebDriverWait(myDriver, WEBDRIVER_CLIENT_EXPLICIT_DELAY);
		webdriverWait.ignoring(StaleElementReferenceException.class);
		setImplicitWait(implicitWait);
		((RemoteWebDriver) myDriver).setFileDetector(new LocalFileDetector());
	}

	public void setImplicitWait(int implicitWait) {
		myDriver.manage().timeouts()
		.implicitlyWait(implicitWait, TimeUnit.MILLISECONDS);
	}

	public void resetImplicitWait() {
		myDriver.manage().timeouts()
		.implicitlyWait(implicitWait, TimeUnit.MILLISECONDS);
	}

	public void setExplicitWait(int explicitWait) {
		webdriverWait = new WebDriverWait(myDriver,explicitWait, 60);
	}

	public void resetExplicitWait() {
		webdriverWait = new WebDriverWait(myDriver,WEBDRIVER_CLIENT_EXPLICIT_DELAY, 60);
	}

	public void setPageLoadTimeout(int pageLoadTimeoutWait) {
		myDriver.manage().timeouts()
		.pageLoadTimeout(pageLoadTimeoutWait, TimeUnit.SECONDS);
	}

	public void resetPageLoadTimeout() {
		myDriver.manage().timeouts()
		.pageLoadTimeout(pageloadtimeout, TimeUnit.SECONDS);
	}

	public WebDriver getWebDriver() {
		return myDriver;
	}

	public void setBrowser(String browserOption) {
		myBrowser = browserOption;
	}

	public String getBrowser() {
		return myBrowser;
	}

	public Integer getWindowHeight() {
		return getWindowSize().height;
	}

	public Integer getWindowWidth() {
		return getWindowSize().width;
	}

	public Dimension getWindowSize() {
		return myDriver.manage().window().getSize();
	}

	public void clearCookies() throws Exception {
		try{
			myDriver.manage().deleteAllCookies();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}	
	}

	public void setURL(String url) {
		Reporter.log("Opening URL: " + url, true);
		myUrl = url;
		myDriver.manage().timeouts()
		.pageLoadTimeout(pageloadtimeout, TimeUnit.SECONDS);
		myDriver.manage().timeouts()
		.implicitlyWait(implicitWait, TimeUnit.MILLISECONDS);
		myDriver.get(url);
	}

	public String getURL() {
		return myUrl;
	}

	public String getCurrentURL() {
		currentUrl = myDriver.getCurrentUrl();
		return currentUrl;
	}

	public void quit() {
		myDriver.quit();
	}

	public void close() {
		myDriver.close();
	}

	public boolean setText(MobileElement element, String inputText) throws Exception {
		try{
			element.sendKeys(inputText);
			return true;
		}
		catch(Exception e){
			throw new TestFrameworkException("Failed to set text field with input text " + inputText,e);
		}
	}

	public void clearText(MobileElement element) throws Exception {
		try{
			click(element);
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.DELETE);		
		}
		catch(Exception e){
			throw new TestFrameworkException("Failed to clear text field " + element.toString(),e);
		}
	}

	public void clearTextAndType(MobileElement element,
			String inputText) throws Exception {
		if(isMobileElementEnabled(element)){
			element.click();
			element.clear();
			element.sendKeys(inputText);
			Thread.sleep(1000);
		}else{
			throw new TestFrameworkException("Locator does not exists or is disabled");
		}
	}

	public boolean setText(String locator, String inputText) throws Exception {
		MobileElement element = findElement(locator);
		return setText(element, inputText);
	}

	public boolean setTextAndTab(MobileElement element, String inputText) throws Exception {
		boolean bval;
		try{
			bval = setText(element, inputText);
			element.sendKeys(Keys.TAB);
			return bval;
		}catch(Exception e){
			throw new TestFrameworkException("Failed to set text field with input text " + inputText,e);
		}
	}

	public boolean setTextAndTab(String locator, String inputText) throws Exception {
		MobileElement element = findElement(locator);
		return setTextAndTab(element, inputText);
	}

	public boolean setTextAndEnter(MobileElement element, String inputText) throws Exception {
		boolean bval;
		try{
			bval = setText(element, inputText);
			element.sendKeys(Keys.ENTER);
			return bval;
		}catch(Exception e){
			throw new TestFrameworkException("Failed to set text field with input text " + inputText,e);
		}
	}

	public boolean setTextAndEnter(String locator, String inputText) throws Exception {
		MobileElement element = findElement(locator);	
		return setTextAndEnter(element, inputText);
	}


	public boolean setTextAndEscape(MobileElement element, String inputText) throws Exception {
		boolean bval;
		try{
			bval = setText(element, inputText);
			element.sendKeys(Keys.ESCAPE);
			return bval;
		}catch(Exception e){
			throw new TestFrameworkException("Failed to set text field with input text " + inputText,e);
		}
	}	

	public boolean setTextAndEscape(String locator, String inputText) throws Exception {
		MobileElement element = findElement(locator);	
		return setTextAndEscape(element, inputText);
	}


	/**
	 * Waits for the visibility of the element (MobileElement) on DOM of a page
	 *
	 * @param element
	 * @return MobileElement
	 * @throws Exception
	 */
	public MobileElement waitForVisibilityOfElement(MobileElement element) throws Exception{
		return (MobileElement) webdriverWait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Wait for visibility of locator on DOM of a page by xpath
	 *
	 * @param locator used to find the element
	 * @return MobileElement
	 * @throws Exception
	 */
	public MobileElement waitForVisibilityOfElementLocatedBy(String locator) throws Exception {
		return (MobileElement) webdriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	/**
	 * Wait for visibility of locator on DOM of a page by xpath
	 *
	 * @param locator used to find elements
	 * @return List<MobileElement>
	 * @throws Exception
	 */
	//	public List<MobileElement> waitForVisibilityOfElementsLocatedBy(String locator) throws Exception {
	//			return webdriverWait.until(ExpectedConditions
	//					.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
	//	}



	/**
	 * Wait for element to be visible and enabled such that you
	 * can click it.
	 *
	 * @param element
	 * @return MobileElement
	 * @throws Exception
	 */
	public MobileElement waitForElementToBeClickable(MobileElement element) throws Exception {
		return (MobileElement) webdriverWait.until(ExpectedConditions
				.elementToBeClickable(element));
	}

	/**
	 * Wait for element to be visible and enabled such that you
	 * can click it.
	 *
	 * @param xpath
	 * @return MobileElement
	 * @throws Exception
	 */
	public MobileElement waitForElementToBeClickable(String xpath) throws Exception {
		return (MobileElement) webdriverWait.until(ExpectedConditions
				.elementToBeClickable(By.xpath(xpath)));
	}

	/**
	 * Check to see if element is location is greater than window size
	 * then will window scroll to it if true
	 *
	 * @param string xpath of element
	 * @return MobileElement
	 * @throws Exception
	 */

	public MobileElement scrollWindowVerticallyToClickableElement(String element_xpath) throws Exception {
		try{
			MobileElement ele = findElement(element_xpath);
			return scrollWindowVerticallyToClickableElement(ele);
		}
		catch (Exception ex) {
			throw new TestFrameworkException("failed to scroll vertically to clickable element "+ element_xpath,ex);
		}

	}


	/**
	 * Check to see if element is location is greater than window size
	 * then will window scroll to it if true
	 *
	 * @param MobileElement
	 * @return MobileElement
	 * @throws Exception
	 */
	public MobileElement scrollWindowVerticallyToClickableElement(MobileElement element) throws Exception {
		JavascriptExecutor jse = myDriver;
		int winSize = 0;
		int eleYPosition = 0;
		int count = 1;

		try {
			String javascript = "window.scrollTo(0,";
			jse.executeScript(javascript + "0)");

			winSize = getWindowHeight()-200;
			eleYPosition = element.getLocation().getY();

			while(eleYPosition > winSize &&  (200*count) < eleYPosition) {
				jse.executeScript(javascript + (200*count) + ")");
				count++;
			}
		}
		catch(Exception e) {
			Reporter.log("scrollVerticallyToClickableElement not able to execute, count:" + count + " element position:" + eleYPosition, true);
		}
		return waitForElementToBeClickable(element);
	}

	public boolean isDialogItemVisibleVertically(String xPath, String dialog_obj_class) throws Exception {
		JavascriptExecutor jse = myDriver;
		int count = 0;
		String javascript = "document.querySelectorAll('" + dialog_obj_class + "')[0].scrollTop = ";
		jse.executeScript(javascript + "0");
		while((myDriver.findElement(By.xpath(xPath)).getLocation().getY() > getWindowHeight()-200) && count < 50){
			jse.executeScript(javascript + (200*count));
			count++;
		}
		if(count >= 50) throw new Exception ("Cannot find element in dialog " + dialog_obj_class);

		return true;
	}

	public boolean isItemVisibleVerticallyInScrollableObject(String xPath, String scrollable_obj_css) throws Exception {
		boolean bval = false;
		JavascriptExecutor jse = myDriver;
		int count = 0;
		String javascript = "document.querySelectorAll('" + scrollable_obj_css + "')[0].scrollTop = ";
		String javascript_returnScrollHeight = "return document.querySelectorAll('" + scrollable_obj_css + "')[0].scrollHeight";
		String javascript_returnClientHeight = "return document.querySelectorAll('" + scrollable_obj_css + "')[0].clientHeight";
		String scrollHeight = jse.executeScript(javascript_returnScrollHeight).toString();
		String clientHeight = jse.executeScript(javascript_returnClientHeight).toString();

		if(Integer.parseInt(scrollHeight) > Integer.parseInt(clientHeight)) {
			jse.executeScript(javascript + "0");

			//Scroll the scrollable object (A) until the target object (B) location is less than the location of scrollable (A) + height of scrollable object (A)
			while(((myDriver.findElement(By.cssSelector(scrollable_obj_css)).getLocation().getY() + 
					myDriver.findElement(By.cssSelector(scrollable_obj_css)).getSize().getHeight()) < myDriver.findElement(By.xpath(xPath)).getLocation().getY()) 
					&& count < 50){
				jse.executeScript(javascript + (200*count));
				count++;
			}
			if(count >= 50) throw new Exception ("Cannot find element in object " + scrollable_obj_css);

			bval = true;
		}
		else
			if(findElements(xPath).size() > 0) 
				bval = true;

		return bval;
	}

	public boolean isItemVisibleHorizontallyInScrollableObject(String xPath, String scrollable_obj_css) throws Exception {
		JavascriptExecutor jse = myDriver;
		int count = 0;
		String javascript = "document.querySelectorAll('" + scrollable_obj_css + "')[0].scrollLeft = ";
		try{
			if(myDriver.findElements(By.cssSelector(scrollable_obj_css)).size() > 0) {	
				jse.executeScript(javascript + "0");

				//Scroll the scrollable object (A) until the target object (B) location is less than the location of scrollable (A) + height of scrollable object (A)
				while(((myDriver.findElement(By.cssSelector(scrollable_obj_css)).getLocation().getX() + 
						myDriver.findElement(By.cssSelector(scrollable_obj_css)).getSize().getWidth()) < myDriver.findElement(By.xpath(xPath)).getLocation().getX()) 
						&& count < 50){
					jse.executeScript(javascript + (200*count));
					count++;
				}
				if(count >= 50) throw new Exception ("Cannot find element in object " + scrollable_obj_css);

				return true;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}



	/**
	 * Wait for element to be invisible or not present on the DOM
	 *
	 * @param element
	 * @return true if element is invisible, false otherwise
	 * @throws Exception
	 */
	public boolean waitForInvisibilityofElementLocatedBy(MobileElement element) throws Exception {
		try{
			boolean isElementNotPresent = webdriverWait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.id(element.getAttribute("id"))));
			return isElementNotPresent;
		}catch(Exception e){
		}
		return false;
	}

	/**
	 * First checks if element is displayed or not? If true then checks if it is enabled
	 *
	 * @param element
	 * @return True if the element is enabled, false otherwise.
	 * @throws Exception
	 */
	public boolean isMobileElementEnabled(MobileElement element) throws Exception{
		if(isMobileElementDisplayed(element)) {
			if(!element.getAttribute("class").contains("disabled"))
				return element.isEnabled();
			else
				Reporter.log("!element.getAttribute('class').contains('disabled')" , true);
		}
		else {
			Reporter.log("isMobileElementDisplayed(element) : false" , true);
		}
		return false;
	}

	/**
	 * Is this element displayed or not? This method avoids the problem of having to parse an
	 * element's "style" attribute.
	 *
	 * @param element
	 * @return true if the element is displayed, false otherwise.
	 */
	public boolean isMobileElementDisplayed(MobileElement element) {
		try{
			if(element.isDisplayed())
				return true;
		}catch(Exception e){}
		return false;
	}

	public boolean waitForVisibilityThenCheckIsMobileElementDisplayed(String locator) throws Exception {
		MobileElement element;
		try{
			setExplicitWait(30);
			element = (MobileElement) webdriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		}catch(Exception ex){
			return false;
		}finally{
			resetExplicitWait();
		}
		return element.isDisplayed();
	}

	public boolean isMobileElementDisplayed(MobileElement element, int timeOutInMilliseconds) {
		try{
			setImplicitWait(timeOutInMilliseconds);
			if(element.isDisplayed())
				return true;
		}catch(Exception e){}
		finally{
			resetImplicitWait();
		}
		return false;
	}

	/**
	 * Is this locator displayed or not? This method avoids the problem of having to parse an
	 * element's "style" attribute.
	 *
	 * @param locator
	 * @return true if the element is displayed, false otherwise.
	 *
	 * @see http://stackoverflow.com/questions/24946703/selenium-webdriver-using-isdisplayed-in-if-statement-is-not-working
	 */
	public boolean isMobileElementDisplayed(String locator){
		try{
			if(myDriver.findElement(By.xpath(locator)).isDisplayed()){
				return true;
			}
		}catch(Exception e){}
		return false;
	}

	public boolean isMobileElementDisplayed(String locator, int timeOutInMilliseconds){
		try{
			setImplicitWait(timeOutInMilliseconds);
			if(myDriver.findElement(By.xpath(locator)).isDisplayed()){
				return true;
			}
		}catch(Exception e){}
		finally{
			resetImplicitWait();
		}
		return false;
	}

	/**
	 * Find the element based on provided locating technique
	 *
	 * @param locator
	 * @return MobileElement
	 * @throws Exception
	 */
	public MobileElement findElement(String locator) throws Exception {
		List<MobileElement> elements = myDriver.findElements(By.xpath(locator));
		if (elements.size() == 0){
			Reporter.log("findElement returned no objects for '" + locator + "'", true);
			return null;
		}
		else if(elements.size() > 1){
			for(MobileElement ele : elements) {
				if (ele.isDisplayed()) return ele; 
			}
			Reporter.log("findElement returned more than one object and not displayed for '" + locator + "'", true);
			return null;
		}
		else{
			return elements.get(elements.size()-1);
		}
	}

	/**
	 * Find the elements based on provided locating technique
	 *
	 * @param locator
	 * @return List of MobileElement
	 * @throws Exception
	 */
	public List<MobileElement> findElements(String locator) throws Exception {
		return myDriver.findElements(By.xpath(locator));
	}

	public List<MobileElement> findElements(String locator,int timeOutInMilliseconds) throws Exception {
		List<MobileElement> elements = null;
		try{
			setImplicitWait(timeOutInMilliseconds);
			elements =  myDriver.findElements(By.xpath(locator));
		}catch(Exception e){}
		finally{
			resetImplicitWait();
		}
		return elements;
	}


	public boolean click(MobileElement element) throws Exception {
		try {
			waitForVisibilityOfElement(element);
			waitForElementToBeClickable(element);
			element.click();
			return true;


		}catch(Exception e){
			throw new TestFrameworkException("Failed to click, as either unable to locate element OR met an exception " + element.toString(),e);
		}
	}

	public boolean doubleclick(MobileElement element) throws Exception {
		Actions action = new Actions(myDriver);
		try {
			waitForVisibilityOfElement(element);
			if(!isMobileElementDisplayed(element)){
				scrollWindowVerticallyToClickableElement(element);
			}

			if(isMobileElementEnabled(element)){
				action.doubleClick(element).perform();
				return true;
			}
			else{
				throw new TestFrameworkException("Double click failed as the element is disabled " + element.toString());
			}
		} catch(Exception e){
			throw new TestFrameworkException("Failed to double click, as either unable to locate element OR met an exception " + element.toString(),e);
		}
	}



	public boolean right_click(MobileElement element) throws Exception {
		Actions oAction = new Actions(myDriver);
		try {
			scrollWindowVerticallyToClickableElement(element);
			waitForVisibilityOfElement(element);
			if(isMobileElementDisplayed(element)){
				oAction.moveToElement(element);
				oAction.contextClick(element).build().perform();
				//waitToLoad();
				return true;
			}
			else{
				throw new TestFrameworkException("Right click failed as the element is not visible " + element.toString());
			}
		} catch(Exception e){
			throw new TestFrameworkException("Failed to right click, as either unable to locate element OR met an exception " + element.toString(),e);
		}
	}

	/** 
	 * Right clicks at a point with an offset from the top-left corner of passed element. 
	 *
	 * @param element - element to use for eventual click.
	 * @param xOffset - Offset from the top-left corner of concerned web element. A negative value means coordinates left from the element.
	 * @param yOffset - Offset from the top-left corner of concerned web element. A negative value means coordinates above the element.
	 * @throws Exception
	 */
	public boolean right_click(String locator, int xOffset, int yOffset) throws Exception {
		MobileElement element = findElement(locator);
		scrollWindowVerticallyToClickableElement(element);
		if(isMobileElementDisplayed(element)){
			Actions action = new Actions(myDriver);
			action.moveToElement(element,xOffset,yOffset).contextClick().build().perform();
			//waitToLoad();
			return true;
		}
		else{
			throw new TestFrameworkException("Right click failed as the element is not visible." + locator);
		}
	}


	public boolean click(String locator) throws Exception {
		MobileElement element = null;
		int retryattempts = 0;
		boolean bval = false;
		try{
			List<MobileElement> MobileElements = findElements(locator);
			if(MobileElements.size() > 0){
				for(MobileElement ele : MobileElements)
					if(isMobileElementEnabled(ele))
					{	
						bval = click(ele);
						break;
					}
			}
			else if (findElements(locator).size() < 1) {
				scrollWindowVerticallyToClickableElement(element);
				click(element);
			}
		}
		catch(StaleElementReferenceException ser) {
			while(retryattempts < 2) {
				try{	
					findElement(locator).click();
					break;
				}
				catch(StaleElementReferenceException ex) {}
				retryattempts++;
			}


		}


		return bval;
	}

	public boolean doubleclick(String locator) throws Exception {
		MobileElement element = null;
		int retryattempts = 0;
		try{
			if(findElements(locator).size() == 1){
				element =findElement(locator);
				doubleclick(element);
			}
			else if (findElements(locator).size() < 1) {
				scrollWindowVerticallyToClickableElement(element);
				doubleclick(element);
			}


			//Reporter.log("locator: " + locator, true);

		}
		catch(StaleElementReferenceException ser) {
			while(retryattempts < 2) {
				try{	
					findElement(locator).click();
					break;
				}
				catch(StaleElementReferenceException ex) {}
				retryattempts++;
			}
		}
		return true;
	}



	public boolean right_click(String locator) throws Exception {
		MobileElement element = findElement(locator);	
		return right_click(element);
	}




	/**
	 * Clicks supplied MobileElement (use this method when obvious ways to click using webdriver implementations fail)
	 * @param element
	 * @throws Exception
	 */
	public void JSClick(MobileElement element) throws Exception {
		JavascriptExecutor js = myDriver;
		try {
			js.executeScript("arguments[0].click();", element);
		}catch(StaleElementReferenceException ex) {
			js.executeScript("arguments[0].click();", element);
		} 
		catch (Exception e) {
			throw new TestFrameworkException("JSClick failed for " + element.toString(), e);
		}
	}

	/**
	 * Clicks supplied locator (use this method when obvious ways to click using webdriver implementations fail)
	 * @param locator
	 * @throws Exception
	 */
	public boolean JSClick(String locator) throws Exception {
		boolean bval = false;
		try {
			MobileElement element = (MobileElement) myDriver.findElement(By.xpath(locator));
			if(element != null) {
				JSClick(element);
				bval = true;
			}
		} catch (Exception e) {
			throw new TestFrameworkException("JSClick failed for " + locator,  e);
		}
		return bval;
	}

	/**
	 * Get the value of provided attribute of a MobileElement 
	 *
	 * @param element
	 * @param attribute
	 * @return value of the provided attribute
	 * @throws Exception
	 */
	public String getAttribute(MobileElement element, String attribute) throws Exception {
		return element.getAttribute(attribute);
	}

	public String getAttribute(String locator, String attribute) throws Exception {
		MobileElement element = (MobileElement) myDriver.findElement(By.xpath(locator));
		return element.getAttribute(attribute);
	}

	public String getCssValue(String locator, String attribute) throws Exception {
		MobileElement element = (MobileElement) myDriver.findElement(By.xpath(locator));
		return element.getCssValue(attribute);
	}

	public String getText(MobileElement element) throws Exception {
		return element.getText();
	}

	public String getText(String locator) throws Exception {
		MobileElement element = (MobileElement) myDriver.findElement(By.xpath(locator));
		return element.getText();
	}

	/**
	 * Moves the mouse to the middle of the element, so that element is scrolled into view
	 *
	 * @param element
	 * @throws Exception 
	 */
	public void hover(MobileElement element) throws Exception {
		Actions action = new Actions(myDriver);
		action.moveToElement(element).build().perform();
		//Thread.sleep(2000);
	}

	/**
	 * Moves the mouse to the middle of the element, so that element is scrolled into view
	 *
	 * @param locator
	 * @throws Exception
	 */
	public void hover(String locator) throws Exception {
		try{
			waitForVisibilityOfElementLocatedBy(locator);
			if(!isMobileElementDisplayed(locator)){
				scrollWindowVerticallyToClickableElement(locator);
			}
			MobileElement element = (MobileElement) myDriver.findElement(By.xpath(locator));
			hover(element);
		}
		catch(Exception e) {
			throw new TestFrameworkException("Hover on locator failed: " + locator,e);
		}
	}

	/**
	 * Moves the mouse to the middle of element and scrolls the element in view
	 *
	 * @param element
	 * @throws Exception
	 */
	public void moveToElement(MobileElement element) throws Exception {
		Actions action = new Actions(myDriver);
		action.moveToElement(element).build().perform();
	}
	/**
	 * Moves the mouse to the middle of element, and perform click action
	 *
	 * @param element
	 * @throws Exception
	 */
	public void moveToAndClick(MobileElement element) throws Exception {
		Actions action = new Actions(myDriver);
		action.moveToElement(element);
		action.click().build().perform();
	}

	/**
	 * Moves the mouse to the middle of the element, so that element is scrolled into view
	 * click and hold
	 * @param locator
	 * @throws Exception
	 */
	public void hoverAndHoldMouseButton(String locator) throws Exception {
		MobileElement element = (MobileElement) myDriver.findElement(By.xpath(locator));
		hoverAndHoldMouseButton(element);
	}

	/**
	 * Moves the mouse to the middle of the element, so that element is scrolled into view
	 * and perform click action on it
	 *
	 * @param element
	 * @throws Exception
	 */
	public void hoverAndClick(MobileElement element) throws Exception {
		Actions action = new Actions(myDriver);
		action.moveByOffset(element.getLocation().getX(),
				element.getLocation().getY()).build();
		Thread.sleep(1000);
		action.click(element).perform();
	}

	/**
	 * Moves the mouse to the middle of the element, so that element is scrolled into view
	 * and perform click and hold action on it
	 *
	 * @param element
	 * @throws Exception
	 */
	public void hoverAndHoldMouseButton(MobileElement element) throws Exception {
		Actions action = new Actions(myDriver);
		action.moveByOffset(element.getLocation().getX(),
				element.getLocation().getY()).build();
		Thread.sleep(1000);
		action.clickAndHold(element).perform();
	}

	/**
	 * Moves the mouse to the middle of the element, so that element is scrolled into view
	 * and perform click action on it
	 *
	 * @param locator
	 * @throws Exception
	 */
	public void hoverAndClick(String locator) throws Exception {
		MobileElement element = (MobileElement) myDriver.findElement(By.xpath(locator));
		hoverAndClick(element);
	}


	/**
	 * Select all options that display text matching the argument.
	 *
	 * @param locator
	 * @param text
	 * @return true if provided text is set as selected, false otherwise
	 * @throws Exception
	 */
	public boolean selectByVisibleText(String locator, String text) throws Exception {
		MobileElement element = (MobileElement) myDriver.findElementByXPath(locator);
		return selectByVisibleText(element, text);
	}

	/**
	 * Select all options that display text matching the argument.
	 *
	 * @param element
	 * @param visibleText
	 * @return true if provided text is set as selected, false otherwise
	 * @throws Exception
	 */
	public boolean selectByVisibleText(MobileElement element, String visibleText) throws Exception {
		//current state of element is stored due to stale element exception happening after action is performed
		MobileElement elementPast = element;
		try {
			if(isMobileElementEnabled(element)){
				Select select = new Select(element);
				select.selectByVisibleText(visibleText);
				List<MobileElement> eleList = myDriver.findElements(By.xpath("//option[@selected='selected' and text()='" + visibleText + "']"));
				if(eleList.size() > 0){
					for(MobileElement ele : eleList) {
						if(ele.findElement(By.xpath("..")).equals(elementPast)) 
							return true;
					}		

				}
			}else{
				Select select = new Select(element);
				select.selectByVisibleText(visibleText);
				throw new TestFrameworkException("Locator does not exist or is disabled : " + visibleText);
			}


		}
		catch (StaleElementReferenceException se) {
			se.printStackTrace();
		}
		catch (Exception ex) {
			throw new TestFrameworkException("Failed select option by visible text: " + visibleText,ex);
		}
		return false;
	}

	public boolean setframeTextArea(String txtAreaId, String textValue) throws Exception {
		try{
			//Assuming there is always single frame in the screen
			myDriver.switchTo().frame(0);
			findElement("//body").sendKeys(textValue);
			myDriver.switchTo().defaultContent();
			return true;
		}
		catch(Exception ex) {
			throw new TestFrameworkException("Failed to set frame text area " + txtAreaId + " with " + textValue,ex);
		}
	}


	/**
	 * Sets text in the given text field
	 *
	 * @param xpath - String that includes % that will be replaced with txtField
	 * @param txtField
	 * @param inputText - Value to enter to field
	 * @return boolean of entered field
	 * @throws Exception
	 */
	public boolean setTextField(String xpath, String txtField, String inputText) throws Exception {
		try{
			if(findElements(xpath.replace("%s", txtField)).size()>0){
				return setTextAndTab(xpath.replace("%s", txtField), inputText);
			}
		}catch(Exception ex) {
			throw new TestFrameworkException("Failed to set text field " + txtField + " with " + inputText,ex);

		}
		return false;
	}

	public boolean setCheckbox(String locator, boolean state) throws Exception {
		MobileElement element = findElement(locator);
		return setCheckbox(element, state);
	}

	public boolean setCheckbox(MobileElement element, boolean state) throws Exception {
		if((!element.isSelected() && state) || (element.isSelected() && !state)){
			return click(element);
		}
		return true;
	}

	public void selectRadio(String xpath, String radioField, String option) throws Exception {
		try{
			List<MobileElement> eleList = findElements(xpath.replace("%s", radioField));
			if(eleList.size()>0){
				for(MobileElement we : eleList){
					if(we.getAttribute("value").equalsIgnoreCase(option)) {
						we.click();
						break;
					}
				}
			}
		}catch(Exception ex) {
			throw new TestFrameworkException("Failed to select radio " + radioField + " with " + option,ex);
		}
	}

	public boolean setCheckbox(String xpath, String name, String isChecked) throws Exception {
		boolean bval = false;
		try{
			List<MobileElement> eleList = findElements(xpath.replace("%s", name));
			if(eleList.size()>0){
				for(MobileElement we : eleList){
					if(we.getAttribute("checked")==null) {
						if(isChecked.equalsIgnoreCase("true")) {
							we.click();
							bval = true;
						}
						else if(isChecked.equalsIgnoreCase("false")) {
							bval = true;
							break;
						}
					}
					else if(isChecked.equalsIgnoreCase("false") && we.getAttribute("checked").toString().equals("true")){
						we.click();
						bval = true;
					}
					break;
				}
			}

		}
		catch(Exception ex) {
			throw new TestFrameworkException("Failed to set checkbox for " + xpath,ex);
		}
		return bval;
	}

	public boolean multipeSelection(List<String> selectList) throws Exception {
		try {
			Actions actions = new Actions(myDriver);
			actions.keyDown(Keys.CONTROL);
			for(String selectitem : selectList){
				if(!findElement(selectitem).getAttribute("class").contains("Highlighted"))
					actions.click(findElement(selectitem));
			}
			actions.keyUp(Keys.CONTROL).build().perform();
			return true;
		} catch (Exception e) {
			throw new TestFrameworkException("Failed to multiple select for " + selectList.toString(),e);
		} 
	}


	public boolean dragAndDrop(String elementXpath, Integer xPostion, Integer yPosition) throws Exception {
		MobileElement dragElement = null;
		try{
			dragElement = findElement(elementXpath);
			scrollWindowVerticallyToClickableElement(dragElement);

			Actions builder = new Actions(myDriver); 
			Action dragAndDrop = builder.dragAndDropBy(dragElement, xPostion, yPosition).build();
			dragAndDrop.perform();	

			return true;

		}
		catch(Exception ex) {
			throw new TestFrameworkException("failed drag and drop item " + elementXpath,ex);		
		}
	}

	public void moveByOffset(String elementXpath, int xPostion, int yPosition) throws Exception {
		MobileElement dragElement = null;
		try{
			dragElement = findElement(elementXpath);
			scrollWindowVerticallyToClickableElement(dragElement);
			int widthSize = dragElement.getSize().getWidth()- 2;

			Actions builder = new Actions(myDriver); 
			Action resize = builder.moveToElement(dragElement, widthSize, 0).clickAndHold().
					moveByOffset(xPostion, yPosition).release().build();
			resize.perform();	
		}
		catch(Exception ex) {
			throw new TestFrameworkException("failed to move by Offset " + elementXpath,ex);		
		}
	}

	/**
	 * Sends the 'Tab' key stroke
	 */
	public void sendTabKey() {
		Actions action = new Actions(myDriver);
		action.sendKeys(Keys.TAB).build().perform();
	}

	public void sendEnterKey() {
		Actions action = new Actions(myDriver);
		action.sendKeys(Keys.ENTER).build().perform();
	}

	/**
	 * Sends the 'Tab' key stroke
	 */
	public void sendEscapeKey() {
		Actions action = new Actions(myDriver);
		action.sendKeys(Keys.ESCAPE).build().perform();
	}

	/**
	 * Send specified key chords (Sequence of key strokes)
	 */
	public void sendKeys(String keyChord) {
		Actions action = new Actions(myDriver);
		action.sendKeys(keyChord).build().perform();
	}

	public void scrollElementToCentre(MobileElement locator) {
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";

		((JavascriptExecutor) myDriver).executeScript(scrollElementIntoMiddle, locator);
	}

	@SuppressWarnings("rawtypes")
	public void scroll(int counter) throws Exception {
		for(int i=0;i<=counter;i++) {
			new TouchAction(myDriver).press(PointOption.point(0, 600)).waitAction().moveTo(PointOption.point(0, 1000)).release().perform();
		}
	}

	public void scrollToExact(MobileElement m) throws Exception {
		TouchActions action = new TouchActions(myDriver);
		action.scroll(m, 100, 100);
		action.perform();


	}
	public void scrollAndClick(String visibleText) {
		MobileElement element = (MobileElement) myDriver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().resourceId(\"com.android.vending:id/tab_recycler_view\")).getChildByText("
						+ "new UiSelector().className(\"android.widget.TextView\"), \"Games We Are Playing\")"));

		//Perform the action on the element
		element.click();
	}

	public void scrollWindowToFindElement(String locator) throws Exception {
		try {
			//int count =1;
			while(findElements(locator).size()<1) {
				((JavascriptExecutor) myDriver).executeScript("window.scrollBy(0,4000)"); 
				Thread.sleep(1000);
			} } catch (Exception ex) {
				throw new TestFrameworkException("failed to find element",ex);
			}
	}

    public void scrollToElement(MobileElement mobElement) throws Exception {
        if (myDriver instanceof JavascriptExecutor) {
            System.out.println("Scrolling to be in view");
            ((JavascriptExecutor) myDriver)
                .executeScript("arguments[0].scrollIntoView(true);", mobElement);
        }         
    }


	public void scrollWindow() throws Exception {
		try {
			((JavascriptExecutor) myDriver).executeScript("window.scrollBy(0,4000)"); 
		}catch (Exception ex) {
			throw new TestFrameworkException("failed to find element",ex);
		}
	}

	public String getScreenAsBase64() {
		return ((TakesScreenshot) myDriver).getScreenshotAs(OutputType.BASE64);
	}
}
