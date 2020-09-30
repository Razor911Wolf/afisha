import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// Класс с настройками web driver
public class WebDiverSettings {
    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(800, 600));
    }

    @After
    public void closed() {
        driver.close();
    }

}
