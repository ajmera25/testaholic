package pageobjects.mobile;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import org.openqa.selenium.support.FindBy;
import core.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
public class MobilePageObjects extends BasePage{
    public MobilePageObjects(AppiumDriver<MobileElement> appiumdriver) {
        super(appiumdriver);
    }
    
    @FindBy(xpath = "//input[@name='q']")
        MobileElement txt_SearchText;
       
        String strToSearch = "step-in forum facebook";
       
        @FindBy(xpath = "//div[contains(text(), '25000 test professionals')]/parent::div/parent::div/parent::div//a")
        MobileElement lnk_FBStepInForum;
       
        @FindBy(xpath = "//a/span[text()='Posts']")
        MobileElement lbl_FBPosts;
        
        @FindBy(xpath = "//a/span[text()='Reviews']")
        MobileElement lbl_FBReviews;
       
        @FindBy(xpath = "//div[contains(text(), '+')]/parent::div/parent::a/parent::div/a")
        MobileElement lnk_MoreThan4Photos;
        
        @FindBy(xpath = "//*[@id='popup_xout']")
        MobileElement lnk_closePopup;
        String strFullSize  = "//a[text()='View full size']";
        
        String strNext = "//a[@data-sigil='touchable'][last()]";
    
    public boolean searchOnGoogle() throws Exception 
    {
        return mobileWebDriverClient.setTextAndEnter(txt_SearchText, strToSearch);
    }
    
    public boolean clickFbPost() throws Exception 
    {
        return mobileWebDriverClient.click(lnk_FBStepInForum);
    }
    
    public boolean openFbPost() throws Exception 
    {
        try{
            mobileWebDriverClient.click(lbl_FBReviews);
            return mobileWebDriverClient.click(lbl_FBPosts);
        }catch(Exception e){
            System.out.println("Exception will be thrown");
        }
        return false;
    }
    
    public boolean openPhotosPost() throws Exception 
    {
        try{
            mobileWebDriverClient.scroll(4);
            if(mobileWebDriverClient.isMobileElementDisplayed(lnk_closePopup)){
                mobileWebDriverClient.click(lnk_closePopup);
            }    
            return mobileWebDriverClient.click(lnk_MoreThan4Photos);
        }catch(Exception e){
            System.out.println("Exception will be thrown");
        }    
        return false;
    }
    
    public void clickImage() throws Exception {
        int imageCounter=1;
        while(imageCounter<=5) {    
            mobileWebDriverClient.waitForVisibilityOfElementLocatedBy(strFullSize);
            String src = mobileWebDriverClient.getAttribute(strFullSize, "href");
            System.out.println(src);
            BufferedImage bufferedImage = ImageIO.read(new URL(src));
            String filePath = System.getProperty("user.dir") + "/src/test/resources/mobilephotos/" + imageCounter + ".jpg";
            File outputfile = new File(filePath);
            imageCounter++;
            ImageIO.write(bufferedImage, "jpeg", outputfile);
            mobileWebDriverClient.JSClick(strNext);
        }
    }
}


