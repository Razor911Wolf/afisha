import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс для проверки старницы Киноафиша Москвы
 */
public class MoviePosterMoscowPage extends BasePage {

    private final static Logger logger = LogManager.getLogger(MoviePosterMoscowPage.class.getName());

    /**
     * Конструктор
     *
     * @param driver - webdriver
     */
    public MoviePosterMoscowPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, 5, 100);
        this.driver = driver;
    }

    /**
     * Проверка установленного для
     *
     * @param dayField - установленные день
     * @return - true/false - проверка прошла или нет
     * Если установленый день "Сегодня" или "Завтра", то сверяем эти дни с тем что в форме
     * иначе сверяем установленные даты типа: 03.10.2020 и 3 октября 2020
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

    /**
     * Проверка станций метро
     *
     * @param stationsMap - список установленных станций метро
     * @return - true/false - проверка прошла или нет
     */
    private boolean checkMetroStationField(Map<Integer, String> stationsMap) {
        logger.info("find metro station and check selected station");
        try {
            List<WebElement> listWebEl = metroStationSelectedLocator.findElements(By.xpath("./div[@class='tag tag_close js-suggest__entered-item margin_left_10']"));
            if (stationsMap.size() == 0) {
                logger.error("No metro stations selected");
                return false;
            }
            if (stationsMap.size() != listWebEl.size()) {
                logger.error("number of selected metro stations is not equal");
                return false;
            }
            for (Map.Entry<Integer, String> entry : stationsMap.entrySet()) {
                logger.info("check station id: " + entry.getKey() + " name: " + entry.getValue());
                try {
                    metroStationSelectedLocator.findElement(By.xpath("./descendant::div[@data-id='" + entry.getKey() + "']"));
                    metroStationSelectedLocator.findElement(By.xpath("./descendant::span[text()='" + entry.getValue() + "']"));
                    logger.info("stations are equal");
                } catch (Exception e) {
                    logger.error("selected metro stations is not equals\nstation id \"" + entry.getKey() + "\" with name \"" + entry.getValue() + "\" not found");
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Проверка жанров
     *
     * @param genreList - список установленных жанров
     * @return - true/false - проверка прошла или нет
     */
    private boolean checkGenreField(ArrayList<String> genreList) {
        logger.info("find genre and check selected genre");
        try {
            List<WebElement> listWebEl = genreSelectedLocator.findElements(By.xpath("./div[@class='tag tag_close js-filter_selected_item margin_left_10']"));
            if (genreList.size() != listWebEl.size()) {
                logger.error("number of selected genres is not equal");
                return false;
            }
            if (genreList.size() == 0) {
                logger.error("genres not selected");
                return false;
            }
            for (String valueGenre : genreList) {
                logger.info("check genre: " + valueGenre);
                try {
                    genreSelectedLocator.findElement(By.xpath("./descendant::span[text()='" + valueGenre + "']"));
                    logger.info("genres are equal");
                } catch (Exception e) {
                    logger.error("selected genre is not equal\ngenre \"" + valueGenre + "\" not found");
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Проверка чекбокса "Только сеансы в 2D"
     *
     * @param cinema2d - состояние установленного чекбокса "Только сеансы в 2D"
     * @return - true/false - проверка прошла или нет
     */
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

    /**
     * Отчет по всем проверка
     *
     * @param afishaPage - страница с установленным днём, станцией метро, жанром и чекбоксом 2d сеансы
     * @return - true/false - общая проверка прошла или нет
     */
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
