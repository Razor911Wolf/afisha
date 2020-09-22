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

public class AfishaPage extends BasePage {

    private final static Logger logger = LogManager.getLogger(AfishaPage.class);

    private String dayField = "Сегодня"; // для сверки выбранного дня с тем что в поле
    private Map<Integer, String> stationsMap = new HashMap<>(); // для сверки выбранной станции метро с тем что в поле (id, Курская)
    private final ArrayList<String> genreList = new ArrayList(); // для сверки выбранного жанра с тем что в поле
    private boolean cinema2d = false;

    public String getDays() {
        return dayField;
    }

    public Map getStationMap() {
        return stationsMap;
    }

    public ArrayList getGenreList() {
        return genreList;
    }

    public boolean getCinema2dStatus() { return cinema2d; }

    public AfishaPage(WebDriver driver) {
        logger.info("test: AfishaPage");
        logger.info("start test");
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 3, 100);
    }

    // открывает страницу
    public void openPage() {
        logger.info("open page: https://afisha.mail.ru/");
        driver.get("https://afisha.mail.ru/");
    }

    // кликает "в кино"
    public AfishaPage clickToTheCinema() {
        logger.info("find and click to the cinema");
        focusWebElement(driver.findElement(cinemaLocator)).click();
        return this;
    }

    // выставляет день
    public AfishaPage setDay(String dayField) {
        logger.info("find and click to days");
        focusWebElement(driver.findElement(daysLocator)).click();
        logger.info("find and click on the day with name: " + dayField);
        //корневой элемент выпадайти
        WebElement rootWebElement = driver.findElement(datePickerListLocator);
        //поиск элемента с текстом
        WebElement findEl = rootWebElement.findElement(By.xpath("./descendant::span[contains(text(),'" + dayField + "')]"));
        focusWebElement(findEl.findElement(By.xpath("./../.."))).click(); // ./../.. - ищет ближайшего кликабельного предка
        this.dayField = dayField; // для дальнейших проверок
        return this;
    }

    // выставляет станцию метро + id станции
    public AfishaPage setMetroStation(String metroName, int stationId) {
        // ждать пока браузер уберёт предыдущий список станций, если такой есть (когда вводится несклько станций, браузер тормозит)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(metroStationPickerListLocator));
        logger.info("find metro locator input and type: " + metroName);
        driver.findElement(metroLocator).sendKeys(metroName);
        logger.info("find metro list and click on station with id: " + stationId);
        //корневой элемент выпадайти
        WebElement rootWebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(metroStationPickerListLocator));
        //поиск элемента с data_id = stationId
        focusWebElement(rootWebElement.findElement(By.xpath("./descendant::div[@data-id='" + stationId + "']"))).click();
        stationsMap.put(stationId, metroName); // для дальнейших проверок
        return this;
    }

    // выставляет жанр
    public AfishaPage setGenre(String genreField) {
        logger.info("find genres and click");
        focusWebElement(driver.findElement(genresLocator)).click();
        logger.info("find and click on the genre with name: " + genreField);
        //корневой элемент выпадайти
        WebElement rootWebElement = driver.findElement(genresPickerListLocator);
        //поиск элемента с текстом среди потомков
        WebElement findEl = rootWebElement.findElement(By.xpath("./descendant::span[text()='" + genreField + "']")); //descendant:: — Возвращает всех множество потомков
        focusWebElement(findEl.findElement(By.xpath("./../.."))).click(); // ./../.. - ищет ближайшего кликабельного предка
        genreList.add(genreField); // для дальнейших проверок
        return this;
    }

    // кликает "2d сеанс"
    public AfishaPage clickCinema2d() {
        logger.info("find cinema2d and click");
        focusWebElement(driver.findElement(cinema2dLocator)).click();
        cinema2d = true; // для дальнейших проверок
        return this;
    }

    // кликает "подобрать"
    public AfishaPage clickPickUpFilms() {
        logger.info("find pick up button and click");
        focusWebElement(driver.findElement(submitLocator)).click();
        return this;
    }

}
