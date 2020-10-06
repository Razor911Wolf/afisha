import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для проверки Кино Mail.ru
 */
public class AfishaPage extends BasePage {

    private final static Logger logger = LogManager.getLogger(AfishaPage.class.getName());

    private String dayField = "Сегодня"; // для сверки выбранного дня с тем что в поле
    private final Map<Integer, String> stationsMap = new HashMap<>(); // для сверки выбранной станции метро с тем что в поле (id, Курская)
    private final ArrayList<String> genreList = new ArrayList(); // для сверки выбранного жанра с тем что в поле
    private boolean cinema2d = false;

    /**
     * Получение уыставленной даты
     *
     * @return dayField - уыставленная дата
     */
    public String getDay() {
        return dayField;
    }

    /**
     * Получение установленной станции метро
     *
     * @return stationsMap - установленные станции метро
     */
    public Map getStationMap() {
        return stationsMap;
    }

    /**
     * Получение уставленного жанра
     *
     * @return genreList - уставленные жанры
     */
    public ArrayList getGenreList() {
        return genreList;
    }

    /**
     * Получение состояния чекбокса "Только сеансы в 2D"
     *
     * @return genreList - true/false - состояние чекбокса
     */
    public boolean getCinema2dStatus() {
        return cinema2d;
    }

    /**
     * Конструктор
     *
     * @param driver - webdriver
     */
    public AfishaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5, 100);
    }

    /**
     * Открывает страницу afisha.mail.ru
     */
    public void openPage() {
        logger.info("open page: https://afisha.mail.ru/");
        driver.get("https://afisha.mail.ru/");
    }

    /**
     * Кликает кнопку "в кино"
     */
    public void clickToTheCinema() {
        logger.info("find and click to the cinema");
        try {
            focusWebElement(cinemaLocator).click();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Установка для
     *
     * @param dayField - день, который есть в выпадающем списке (Сегодня, Завтра, 03.10.2020 и т.д.)
     */
    // выставляет день
    public void setDay(String dayField) {
        logger.info("find and click to days");
        try {
            focusWebElement(daysLocator).click();
            logger.info("find and click on the day with name: " + dayField);
            //поиск элемента с текстом
            WebElement findEl = datePickerListLocator.findElement(By.xpath("./descendant::span[contains(text(),'" + dayField + "')]"));
            focusWebElement(findEl.findElement(By.xpath("./../.."))).click(); // ./../.. - ищет ближайшего кликабельного предка
            this.dayField = dayField; // для дальнейших проверок
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    /**
     * Установка станции метро
     *
     * @param metroName - название станции метро строкой
     * @param stationId - id станции метро цифрами
     */
    // выставляет станцию метро + id станции
    public void setMetroStation(String metroName, int stationId) {
        try {
            // ждать пока браузер уберёт предыдущий список станций, если такой есть (когда вводится несклько станций, браузер тормозит)
            wait.until(ExpectedConditions.invisibilityOf(metroStationPickerListLocator));
            logger.info("find metro locator input and type: " + metroName);
            focusWebElement(metroLocator).sendKeys(metroName);
            logger.info("find metro list and click on station with id: " + stationId);
            //корневой элемент выпадайти
            wait.until(ExpectedConditions.visibilityOf(metroStationPickerListLocator));
            //поиск элемента с data_id = stationId
            WebElement webElement = metroStationRootPickerListLocator.findElement(By.xpath("./descendant::div[@data-id='" + stationId + "']"));
            focusWebElement(webElement).click();
            stationsMap.put(stationId, metroName); // для дальнейших проверок
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Установка жанра
     *
     * @param genreField - название жанра
     */
    public void setGenre(String genreField) {
        logger.info("find genres and click");
        try {
            focusWebElement(genresLocator).click();
            logger.info("find and click on the genre with name: " + genreField);
            //поиск элемента с текстом среди потомков
            WebElement findEl = genresPickerListLocator.findElement(By.xpath("./descendant::span[text()='" + genreField + "']")); //descendant:: — Возвращает всех потомков
            focusWebElement(findEl.findElement(By.xpath("./../.."))).click(); // ./../.. - ищет ближайшего кликабельного предка
            genreList.add(genreField); // для дальнейших проверок
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Кликает кнопку "Только сеансы в 2D"
     */
    public void clickCinema2d() {
        logger.info("find cinema2d and click");
        try {
            focusWebElement(cinema2dLocator).click();
            cinema2d = true; // для дальнейших проверок
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Кликает кнопку "Подобрать"
     */
    public void clickPickUpFilms() {
        logger.info("find pick up button and click");
        try {
            focusWebElement(submitLocator).click();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
