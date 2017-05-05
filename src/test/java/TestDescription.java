import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;

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
//        driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("TestDescription starting");
    }

    @Override
    protected void finished(Description description) {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("./sccreenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();
        System.out.println("TestDescription finished");
    }
}
