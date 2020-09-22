import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoviePosterMoscowPage extends BasePage {

    private WebDriver driver;
    private final static Logger logger = LogManager.getLogger(MoviePosterMoscowPage.class);

    public MoviePosterMoscowPage(WebDriver driver) {
        logger.info("test: MoviePosterMoscowPage");
        logger.info("start test");
        this.wait = new WebDriverWait(driver, 3, 100);
        this.driver = driver;
    }

    private boolean checkDayField(String dayField) {

        logger.info("find days and check selected day with: " + dayField);
        if (dayField.equals(driver.findElement(daysLocator).getText())) {
            logger.info("days are equal");
            return true;
        } else {
            logger.info("days are not equals");
            return false;
        }
    }

    private boolean checkMetroStationField(Map<Integer, String> stationsMap) {
        logger.info("find metro station and check selected station");
        WebElement rootWebElement = driver.findElement(metroStationSelectedLocator);
        boolean checkPassed = true;

        List<WebElement> listWebEl = rootWebElement.findElements(By.xpath("./div[@class='tag tag_close js-suggest__entered-item margin_left_10']"));
        if (stationsMap.size() != listWebEl.size()) {
            logger.error("number of selected metro stations is not equal");
            checkPassed = false;
        }
        for (Map.Entry<Integer, String> entry : stationsMap.entrySet()) {
            logger.info("check station id: " + entry.getKey() + " name: " + entry.getValue());
            try {
                rootWebElement.findElement(By.xpath("./descendant::div[@data-id='" + entry.getKey() + "']"));
                rootWebElement.findElement(By.xpath("./descendant::span[text()='" + entry.getValue() + "']"));
                logger.info("stations are equal");
            } catch (Exception e) {
                logger.error("selected metro stations is not equals\nstation id \"" + entry.getKey() + "\" with name \"" + entry.getValue() + "\" not found");
                checkPassed = false;
            }
        }
        return checkPassed;
    }

    private boolean checkGenreField(ArrayList<String> genreList) {
        logger.info("find genre and check selected genre");
        WebElement rootWebElement = driver.findElement(genreSelectedLocator);
        boolean checkPassed = true;
        List<WebElement> listWebEl = rootWebElement.findElements(By.xpath("./div[@class='tag tag_close js-filter_selected_item margin_left_10']"));
        if (genreList.size() != listWebEl.size()) {
            logger.error("number of selected genres is not equal");
            checkPassed = false;
        }
        for (String valueGenre : genreList) {
            logger.info("check genre: " + valueGenre);
            try {
                rootWebElement.findElement(By.xpath("./descendant::span[text()='" + valueGenre + "']"));
                logger.info("genres are equal");
            } catch (Exception e) {
                logger.error("selected genre is not equal\ngenre \"" + valueGenre + "\" not found");
                checkPassed = false;
            }
        }
        return checkPassed;
    }

    public MoviePosterMoscowPage getReport(AfishaPage afishaPage) {
        logger.info("get report: MoviePosterMoscowPage");
        if (checkDayField(afishaPage.getDays()) && checkMetroStationField(afishaPage.getStationMap()) && checkGenreField(afishaPage.getGenreList())) {

            logger.info("all selected fields are equal");
            logger.info("the functionality meets the requirements");
        } else {
            logger.error("the functionality does not meet the requirements");
            throw new IllegalStateException("The functionality does not meet the requirements");
        }
        return this;
    }

}
