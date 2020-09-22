import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Driver;

public class Main {


    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        Afisha afishaTest = new Afisha();
        AfishaPage afishaPage = new AfishaPage(driver);
        afishaTest.setUp(driver);
        afishaTest.test_01(afishaPage);
        afishaTest.closed();
    }

}