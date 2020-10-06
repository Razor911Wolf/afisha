import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Общий класс с общими методами и локаторами
public class BasePage {

    // Реклама на весь экран
    @FindBy(xpath = "//div[@class='trg-b-fullScreen mailru-visibility-check _screenLarge']")
    protected static WebElement ads1Locator;
    // Реклама на весь экран 2
    @FindBy(xpath = "//div[@class='trg-b-fullScreen mailru-visibility-check _screenTiny']")
    protected static WebElement ads2Locator;
    // Реклама в правом углу
    @FindBy(xpath = "//div[@class='m-promo-popup m-promo-popup_event js-promo-popup m-promo-popup_shown']")
    protected static WebElement ads3Locator;
    // Кнопка "Наверх" в левом углу мешающая кликать по дате
    @FindBy(xpath = "//a[@name='clb21261896']/..")
    protected static WebElement upButtonLocator;
    // В кино
    @FindBy(xpath = "//span[text()='В кино']")
    protected static WebElement cinemaLocator;
    // Выбор дня
    @FindBy(xpath = "//div[@class='input-group input-group_fixed']//div[@class='dropdown__text js-dates__title']")
    protected static WebElement daysLocator;
    // Список дней в выпадайке
    @FindBy(xpath = "//div[@class='tab__content js-tab__content tab__content_active']//div[@class='suggest__inner js-scrollable__view dropdown__scroll']")
    protected static WebElement datePickerListLocator;
    // Выбор станции метро
    @FindBy(xpath = "//div[@class='input-group__item']//input[@class='input__field js-suggest__input']")
    protected static WebElement metroLocator;
    // Список станций в выпадайке (кольцева и обычная)
    @FindBy(xpath = "//div[@class='input-group input-group_fixed']//div[@class='suggest__inner']")
    protected static WebElement metroStationPickerListLocator;
    // Локатор формы, из которой появляется выпадайка станций
    @FindBy(xpath = "//div[@class='input-group input-group_fixed']//div[@class='input js-suggest__input-wrap']")
    protected static WebElement metroStationRootPickerListLocator;
    // Выбор жанра
    @FindBy(xpath = "//form[@class='js-module']//input[@data-placeholder='Все жанры']")
    protected static WebElement genresLocator;
    // Список жанров
    @FindBy(xpath = "//form[@class='js-module']//div[@class='suggest__inner js-select__options__list js-scrollable__view dropdown__scroll']")
    protected static WebElement genresPickerListLocator;
    // Только сеансы в 2D
    @FindBy(xpath = "//div[@class='checkbox checkbox_colored margin_right_20']")
    protected static WebElement cinema2dLocator;
    // Подобрать
    @FindBy(name = "clb6796813")
    protected static WebElement submitLocator;

    // Локатор с выбранной станцией метро
    @FindBy(xpath = "//div[@class='input-group input-group_fixed']//div[@class='input__tags-inner js-suggest__entered-list']")
    protected static WebElement metroStationSelectedLocator;
    // Локатор с выбранным жанром
    @FindBy(xpath = "//div[@class='input-group input-group_fixed']//div[@class='input__tags-inner js-filter_selected_list']")
    protected static WebElement genreSelectedLocator;

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    // прячет указанный элемент
    protected void hideWebElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='hidden'", element);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
    }

    // фокусировка и ожидание кликабельности веб элемента. Для клика по элементам, которые за пределами окна браузера (для маленьких экранов)
    protected WebElement focusWebElement(WebElement webElement) {
        hideWebElement(upButtonLocator); // Прячет кнопку "наверех" чтоб не мешалась
        waitInvisibilityOfAds(); // ждёт пока уберётся реклама, если она есть
        //Actions action = new Actions(driver);
        //action.moveToElement(webElement).build().perform(); // не работает в ff
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].scrollIntoView(false);", webElement);
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    // ожидание пока пропадёт мешающая реклама
    static void waitInvisibilityOfAds() {
        try {
            if (ads1Locator.isDisplayed()) {
                new WebDriverWait(driver, 15, 300).until(ExpectedConditions.invisibilityOf(ads1Locator));
            }
        } catch (Exception e) {

        }
        try {
            if (ads2Locator.isDisplayed()) {
                new WebDriverWait(driver, 15, 300).until(ExpectedConditions.invisibilityOf(ads2Locator));
            }
        } catch (Exception e) {

        }
        try {
            if (ads3Locator.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('class','m-promo-popup m-promo-popup_event js-promo-popup')", ads3Locator);
            }
        } catch (Exception e) {

        }
    }

    // проверка тайтла страницы
    public void checkPageTitle(String pageTitle) {
        try {
            new WebDriverWait(driver, 15, 300).until(ExpectedConditions.titleContains(pageTitle));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new IllegalStateException("This is not the " + pageTitle + " page or page not loaded");
        /*if (!"Кино Mail.ru — фильмы, сериалы и телешоу из самых популярных онлайн-кинотеатров".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the afisha.mail.ru page");
        }*/
        }
    }

}
