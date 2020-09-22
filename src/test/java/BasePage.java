import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    // Реклама на весь экран
    public static final By ads1Locator = By.xpath("//div[@class='trg-b-fullScreen mailru-visibility-check _screenLarge']");
    // Реклама на весь экран
    public static final By ads2Locator = By.xpath("//div[@class='trg-b-fullScreen mailru-visibility-check _screenTiny']");
    // Реклама в правом углу
    public static final By ads3Locator = By.xpath("//a[@class='text text_bold_large text_fixed color_white link-holder']");
    // Кнопка "Наверх" в левом углу мешающая кликать по дате
    public static final By upButtonLocator = By.xpath("//a[@name='clb21261896']/..");
    // В кино
    public static final By cinemaLocator = By.xpath("//span[text()='В кино']");
    // Выбор дня
    public static final By daysLocator = By.xpath("//div[@class='input-group input-group_fixed']//div[@class='dropdown__text js-dates__title']");
    // Список дней в выпадайке
    public static final By datePickerListLocator = By.xpath("//div[@class='tab__content js-tab__content tab__content_active']//div[@class='suggest__inner js-scrollable__view dropdown__scroll']");
    // Выбор станции метро
    public static final By metroLocator = By.xpath("//div[@class='input-group__item']//input[@class='input__field js-suggest__input']");
    // Список старций в выпадайке (кольцева и обычная)
    public static final By metroStationPickerListLocator = By.xpath("//div[@class='input-group input-group_fixed']//div[@class='suggest__inner']");
    // Выбор жанра
    public static final By genresLocator = By.xpath("//form[@class='js-module']//input[@data-placeholder='Все жанры']");
    // Список жанров
    public static final By genresPickerListLocator = By.xpath("//form[@class='js-module']//div[@class='suggest__inner js-select__options__list js-scrollable__view dropdown__scroll']");
    // Только сеансы в 2D
    public static final By cinema2dLocator = By.xpath("//div[@class='checkbox checkbox_colored margin_right_20']");
    // Подобрать
    public static final By submitLocator = By.name("clb6796813");

    // Локатор с выбранной станцией метро
    public static final By metroStationSelectedLocator = By.xpath("//div[@class='input-group input-group_fixed']//div[@class='input__tags-inner js-suggest__entered-list']");
    // Локатор с выбранным жанром
    public static final By genreSelectedLocator = By.xpath("//div[@class='input-group input-group_fixed']//div[@class='input__tags-inner js-filter_selected_list']");

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    // прячет указанный элемент
    protected void hideWebElement(By xpathLocator) {
        WebElement element = driver.findElement(xpathLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='hidden'", element);
    }

    // фокусировка и ожидание кликабельности веб элемента. Для клика по элементам, которые за пределами окна браузера (для маленьких экранов)
    protected WebElement focusWebElement(WebElement webElement) {
        waitInvisibilityOfAds(); // ждёт пока уберётся реклама, если она есть
        hideWebElement(upButtonLocator); // Прячет кнопку "наверех" чтоб не мешалась

        Actions actions = new Actions(driver);
        actions.moveToElement(webElement);
        actions.perform();
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    // ожидание пока пропадёт мешающая реклама
    static void waitInvisibilityOfAds() {
        new WebDriverWait(driver, 15, 300).until(ExpectedConditions.invisibilityOfElementLocated(ads1Locator));
        new WebDriverWait(driver, 15, 300).until(ExpectedConditions.invisibilityOfElementLocated(ads2Locator));
        new WebDriverWait(driver, 25, 300).until(ExpectedConditions.invisibilityOfElementLocated(ads3Locator));
    }

    // проверка тайтла страницы
    public void checkPageTitle(String pageTitle) {
        try {
            new WebDriverWait(driver, 15, 300).until(ExpectedConditions.titleContains(pageTitle));
        } catch (Exception e) {
            throw new IllegalStateException("This is not the " + pageTitle + " page or page not loaded");
        /*if (!"Кино Mail.ru — фильмы, сериалы и телешоу из самых популярных онлайн-кинотеатров".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the afisha.mail.ru page");
        }*/
        }
    }

}
