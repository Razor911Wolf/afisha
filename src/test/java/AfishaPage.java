import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AfishaPage extends BasePage {


    private final static Logger logger = LogManager.getLogger(AfishaPage.class);

    private String dayField = "Сегодня"; // для сверки выбранного дня с тем что в поле
    private Map<Integer, String> stationsMap = new HashMap<>(); // для сверки выбранной станции метро с тем что в поле (id, Курская)
    private ArrayList<String> genreList = new ArrayList(); // для сверки выбранного жанра с тем что в поле

    public String getDays() {
        return dayField;
    }

    public Map getStationMap() {
        return stationsMap;
    }

    public ArrayList getGenreList(){
        return genreList;
    }

    public AfishaPage(WebDriver driver) {
        logger.info("test: AfishaPage");
        logger.info("start test");
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 3, 100);
    }

    public void openPage() {
        logger.debug("open page: https://afisha.mail.ru/");
        driver.get("https://afisha.mail.ru/");
    }

    public AfishaPage clickToTheCinema() {
        logger.debug("find and click to the cinema");
        focusWebElement(driver.findElement(cinemaLocator)).click();
        return this;
    }

    public AfishaPage setDay(String dayField) {
        logger.debug("find and click to days");
        focusWebElement(driver.findElement(daysLocator)).click();
        logger.debug("find and click on the day with name: " + dayField);
        //корневой элемент выпадайти
        WebElement rootWebElement = driver.findElement(datePickerListLocator);
        //поиск элемента с текстом
        WebElement findEl = rootWebElement.findElement(By.xpath("./descendant::span[contains(text(),'" + dayField + "')]"));
        focusWebElement(findEl.findElement(By.xpath("./../.."))).click(); // ./../.. - ищет ближайшего кликабельного предка
        this.dayField = dayField; // для дальнейших проверок
        return this;
    }

    public AfishaPage setMetroStation(String metroName, int stationId) {
        // ждать пока браузер уберёт предыдущий список станций, если такой есть (когда вводится несклько станций, браузер тормозит)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(metroStationPickerListLocator));
        logger.debug("find metro locator input and type: " + metroName);
        driver.findElement(metroLocator).sendKeys(metroName);
        logger.debug("find metro list and click on station with id: " + stationId);
        //корневой элемент выпадайти
        WebElement rootWebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(metroStationPickerListLocator));
        //поиск элемента с data_id = stationId
        focusWebElement(rootWebElement.findElement(By.xpath("./descendant::div[@data-id='" + stationId + "']"))).click();
        stationsMap.put(stationId, metroName); // для дальнейших проверок
        return this;
    }

    public AfishaPage setGenre(String genreField) {
        logger.debug("find genres and click");
        focusWebElement(driver.findElement(genresLocator)).click();
        logger.debug("find and click on the genre with name: " + genreField);
        //корневой элемент выпадайти
        WebElement rootWebElement = driver.findElement(genresPickerListLocator);
        //поиск элемента с текстом среди потомков
        WebElement findEl = rootWebElement.findElement(By.xpath("./descendant::span[text()='" + genreField + "']")); //descendant:: — Возвращает всех множество потомков
        focusWebElement(findEl.findElement(By.xpath("./../.."))).click(); // ./../.. - ищет ближайшего кликабельного предка
        genreList.add(genreField);
        return this;
    }

    public AfishaPage clickCinema2d() {
        logger.debug("find cinema2d and click");
        focusWebElement(driver.findElement(cinema2dLocator)).click();
        return this;
    }

    public AfishaPage clickPickUpFilms() {
        logger.debug("find pick up button and click");
        focusWebElement(driver.findElement(submitLocator)).click();
        return this;
    }



}
