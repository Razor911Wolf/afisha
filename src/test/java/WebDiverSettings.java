import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Класс с настройками web driver
 */
public class WebDiverSettings {
    protected WebDriver driver;

    /**
     * Инициализация
     */
    @Before
    public void setUp() {
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
        //driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
        //driver.manage().window().maximize();
    }

    /**
     * Закрытие браузера
     */
    @After
    public void closed() {
        driver.quit();
    }

}
