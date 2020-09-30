import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// Класс для проверки старницы Киноафиша Москвы
public class MoviePosterMoscowPage extends BasePage {

    private WebDriver driver;
    private final static Logger logger = LogManager.getLogger(MoviePosterMoscowPage.class.getName());

    public MoviePosterMoscowPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, 3, 100);
        this.driver = driver;
    }

    // проверка дня
    /*
    Если установленый день "Сегодня" или "Завтра", то сверяем эти дни с тем что в форме
    иначе сверяем установленные даты типа: 03.10.2020 и 3 октября 2020
     */
    private boolean checkDayField(String dayField) {
        logger.info("find days and check selected day with: " + dayField);
        try {
            if (dayField.equals("Сегодня") || dayField.equals("Завтра")) {
                if (dayField.equals(daysLocator.getText())) {
                    logger.info("days are equal");
                    return true;
                } else {
                    logger.error("days are not equals");
                    return false;
                }
            } else {
                DateFormat format = new SimpleDateFormat("d MMMM yyyy", new Locale("ru"));
                Date date1 = format.parse(daysLocator.getText());
                format = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru"));
                Date date2 = format.parse(dayField);
                if (date1.equals(date2)) {
                    logger.info("days are equal");
                    return true;
                } else {
                    logger.error("days are not equals");
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
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
        try {
            boolean checkPassed = true;
            List<WebElement> listWebEl = metroStationSelectedLocator.findElements(By.xpath("./div[@class='tag tag_close js-suggest__entered-item margin_left_10']"));
            if (stationsMap.size() != listWebEl.size()) {
                logger.error("number of selected metro stations is not equal");
                checkPassed = false;
            }
            for (Map.Entry<Integer, String> entry : stationsMap.entrySet()) {
                logger.info("check station id: " + entry.getKey() + " name: " + entry.getValue());
                try {
                    metroStationSelectedLocator.findElement(By.xpath("./descendant::div[@data-id='" + entry.getKey() + "']"));
                    metroStationSelectedLocator.findElement(By.xpath("./descendant::span[text()='" + entry.getValue() + "']"));
                    logger.info("stations are equal");
                } catch (Exception e) {
                    logger.error("selected metro stations is not equals\nstation id \"" + entry.getKey() + "\" with name \"" + entry.getValue() + "\" not found");
                    checkPassed = false;
                }
            }
            return checkPassed;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    // проверка жанров
    private boolean checkGenreField(ArrayList<String> genreList) {
        logger.info("find genre and check selected genre");
        try {
            boolean checkPassed = true;
            List<WebElement> listWebEl = genreSelectedLocator.findElements(By.xpath("./div[@class='tag tag_close js-filter_selected_item margin_left_10']"));
            if (genreList.size() != listWebEl.size()) {
                logger.error("number of selected genres is not equal");
                checkPassed = false;
            }
            for (String valueGenre : genreList) {
                logger.info("check genre: " + valueGenre);
                try {
                    genreSelectedLocator.findElement(By.xpath("./descendant::span[text()='" + valueGenre + "']"));
                    logger.info("genres are equal");
                } catch (Exception e) {
                    logger.error("selected genre is not equal\ngenre \"" + valueGenre + "\" not found");
                    checkPassed = false;
                }
            }
            return checkPassed;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    // проверка чекбокса 2d сеанс
    private boolean CheckCinema2d(boolean cinema2d) {
        logger.info("find cinema2d and check");
        try {
            if (cinema2d == cinema2dLocator.findElement(By.xpath("./descendant::input[@name='is_2d']")).isSelected()) {
                logger.info("cinema2d selected");
                return true;
            } else {
                logger.error("cinema2d not selected");
                return false;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    // отчёт по всем выбранным полям
    public boolean getReport(AfishaPage afishaPage) {
        logger.info("get report: MoviePosterMoscowPage");
        if (checkDayField(afishaPage.getDay()) && checkMetroStationField(afishaPage.getStationMap()) && checkGenreField(afishaPage.getGenreList()) && CheckCinema2d(afishaPage.getCinema2dStatus())) {
            logger.info("all selected fields are equal");
            return true;
        } else {
            logger.error("the selected fields are not equal");
            return false;
        }
    }

}
