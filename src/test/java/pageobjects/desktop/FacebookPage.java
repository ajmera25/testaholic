package pageobjects.desktop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import core.BasePage;

public class FacebookPage extends BasePage{

	public FacebookPage(WebDriver driver) {
		super(driver);
	}

	public boolean downloadPhotos () {
		boolean bval = false;
		String firstPhoto = "//div[@class='_52db']/ancestor::a/parent::div/a";
		try {
			webDriverClient.scrollWindow();
			webDriverClient.waitForElementToBeClickable("//a[text()='Not Now']");
			webDriverClient.click("//a[text()='Not Now']");
			webDriverClient.scrollElementToCentre(webDriverClient.findElement(firstPhoto));
			webDriverClient.click(firstPhoto);
			for(int i=1; i<=5; i++) {
				String src = webDriverClient.findElement("//img[@class='spotlight']").getAttribute("src");
				BufferedImage bufferedImage = ImageIO.read(new URL(src));
				String filePath = System.getProperty("user.dir") + "/src/test/resources/WebPhotos/" + i + ".jpg";
				File outputfile = new File(filePath);
				ImageIO.write(bufferedImage, "jpeg", outputfile);
				webDriverClient.JSClick("//a[contains(@class,'snowliftPager next')]");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bval;
				
	}
	
	public boolean isPhotoSizeGreaterThanZero() {
		boolean bval = false;
		for(int i=1; i<=5; i++) {
			String filePath = System.getProperty("user.dir") + "/src/test/resources/results/" +i+ ".jpg";
			File file = new File(filePath);
			System.out.println(file.length() / 1024);
			if(file.length() / 1024 < 0) {
				return false;
			}
			bval = true;
		}
		return bval;
	}
}
