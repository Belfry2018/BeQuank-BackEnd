import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 10:45 AM 9/1/18
 * @Modifiedby:
 */

public class main {
    public static void main(String[] args)throws IOException {
        System.setProperty("webdriver.chrome.driver","/home/allen/Downloads/milkycat/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.get("http://elite.nju.edu.cn/jiaowu/");
        WebElement ele = driver.findElement(By.id("ValidateImg"));

// Get entire page screenshot
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

// Get the location of element on the page
        Point point = ele.getLocation();

// Get width and height of the element
        int eleWidth = ele.getSize().getWidth();
        int eleHeight = ele.getSize().getHeight();

// Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);

// Copy the element screenshot to disk
        File screenshotLocation = new File("/home/allen/Desktop/GoogleLogo_screenshot.png");
        FileUtils.copyFile(screenshot, screenshotLocation);
    }
}
