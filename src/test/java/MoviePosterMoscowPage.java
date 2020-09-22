import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MoviePosterMoscowPage extends BasePage {

    private WebDriver driver;
    private final static Logger logger = LogManager.getLogger(MoviePosterMoscowPage.class);

    public MoviePosterMoscowPage(WebDriver driver) {
        logger.info("test: MoviePosterMoscowPage");
        logger.info("start test");
        this.wait = new WebDriverWait(driver, 3, 100);
        this.driver = driver;
    }

    // проверка дня
    /*
    Если установленый день "Сегодня" или "Завтра", про сверяем эти дни с тем что в форме
    иначе сверяем установленные даты типа: 03.10.2020 и 3 октября 2020
     */
    private boolean checkDayField(String dayField) {
        logger.info("find days and check selected day with: " + dayField);
        if (dayField.equals("Сегодня") || dayField.equals("Завтра")) {
            if (dayField.equals(driver.findElement(daysLocator).getText())) {
                logger.info("days are equal");
                return true;
            } else {
                logger.error("days are not equals");
                return false;
            }
        } else {
            try {
                DateFormat format = new SimpleDateFormat("d MMMM yyyy", new Locale("ru"));
                Date date1 = format.parse(driver.findElement(daysLocator).getText());
                format = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));
                Date date2 = format.parse(dayField);
                if (date1.equals(date2)) {
                    logger.info("days are equal");
                    return true;
                } else {
                    logger.error("days are not equals");
                    return false;
                }
            } catch (Exception e) {
                throw new IllegalStateException("days format error");
            }
        }
    }

    // проверка станций метро
    /*
    1. Сверить количество станций в форме и сколько было реально выбрано. Если !=, то присвоить checkPassed = false. Проверять дальше все станции, которые есть.
    2. Проверить каждую станцию. Если найдено несовпадение, то присвоить checkPassed = false и всё равно проверить остальные станции и залогировать какая именно станция не прошла проверку.
    3. Вернуть значение checkPassed
     */
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

    // проверка жанров
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

    // проверка чекбокса 2d сеанс
    private boolean CheckCinema2d(boolean cinema2d) {
        logger.info("find cinema2d and check");
        WebElement rootWebElement = driver.findElement(cinema2dLocator);
        if (cinema2d == rootWebElement.findElement(By.xpath("./descendant::input[@name='is_2d']")).isSelected()) {
            logger.info("cinema2d selected");
            return true;
        } else {
            logger.error("cinema2d not selected");
            return false;
        }
    }

    // отчёт по всем выбранным полям
    public MoviePosterMoscowPage getReport(AfishaPage afishaPage) {
        logger.info("get report: MoviePosterMoscowPage");
        if (checkDayField(afishaPage.getDays()) && checkMetroStationField(afishaPage.getStationMap()) && checkGenreField(afishaPage.getGenreList()) && CheckCinema2d(afishaPage.getCinema2dStatus())) {
            logger.info("all selected fields are equal");
            logger.info("The functionality meets the requirements");
        } else {
            logger.error("the selected fields are not equal");
            throw new IllegalStateException("The functionality does not meet the requirements");
        }
        return this;
    }

}
