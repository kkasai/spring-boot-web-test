import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestDescription extends TestWatcher {
    private Description description;
    WebDriver driver;

    public Description getDescription() {
        return description;
    }

    @Override
    protected void starting(Description d) {
        description = d;
//        System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
//        System.setProperty("webdriver.gecko.driver", "geckodriver");
//        driver = new FirefoxDriver();
//        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        System.out.println("TestDescription starting");
    }

    @Override
    protected void finished(Description description) {
        Path screenshotDirectoryPath = Paths.get("./screenshot", description.getClassName());
        Path screenshotFilePath = Paths.get(screenshotDirectoryPath.toString(), description.getMethodName() + ".png");
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(screenshotDirectoryPath);
            FileUtils.copyFile(file, screenshotFilePath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();
        System.out.println("TestDescription finished");
    }
}
