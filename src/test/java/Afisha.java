import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Afisha {

    private static Logger logger = LogManager.getLogger(Afisha.class);

    private WebDriver driver;
    private AfishaPage afishaPage;
    private MoviePosterMoscowPage moviePosterMoscowPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(800, 600));
    }

    @After
    public void closed() {
        driver.close();
    }

    @Test
    public void test_01() {
        logger.info("start: test_01");
        afishaPage = new AfishaPage(driver);
        afishaPage.openPage();
        afishaPage.checkPageTitle("Кино Mail.ru — фильмы, сериалы и телешоу из самых популярных онлайн-кинотеатров");
        afishaPage.clickToTheCinema();
        afishaPage.clickCinema2d();
        afishaPage.setDay("Завтра");
        afishaPage.setMetroStation("Курская", 68); //курская кольцевая id=68
        afishaPage.setGenre("драма");
        afishaPage.setGenre("комедия");
        afishaPage.clickPickUpFilms();
        moviePosterMoscowPage = new MoviePosterMoscowPage(driver);
        moviePosterMoscowPage.checkPageTitle("Киноафиша Москвы");
        moviePosterMoscowPage.getReport(afishaPage);
        logger.info("end: test_01");
    }

    @Test
    @Ignore
    public void test_02() {
        logger.info("start: test_02");
        afishaPage = new AfishaPage(driver);
        afishaPage.openPage();
        afishaPage.checkPageTitle("Кино Mail.ru — фильмы, сериалы и телешоу из самых популярных онлайн-кинотеатров");
        afishaPage.clickToTheCinema();
        afishaPage.clickCinema2d();
        afishaPage.setDay("03.10.2020");
        afishaPage.setMetroStation("Третьяковская", 287); //третьяковская id=287
        afishaPage.setMetroStation("Курская", 68); //курская кольцевая id=68
        afishaPage.setMetroStation("Первомайская", 107); //первомайская id=107
        afishaPage.setGenre("фэнтези");
        afishaPage.setGenre("боевик");
        afishaPage.setGenre("фантастика");
        afishaPage.setGenre("аниме");
        afishaPage.clickPickUpFilms();
        moviePosterMoscowPage = new MoviePosterMoscowPage(driver);
        moviePosterMoscowPage.checkPageTitle("Киноафиша Москвы");
        moviePosterMoscowPage.getReport(afishaPage);
        logger.info("end: test_02");
    }
}

/*
1. Перейти на https://afisha.mail.ru/
2. В блоке под поиском выбираем "Сходить в кино"
3. Выбираем день "Завтра"
4. Вводим метро "Курская" и выбираем из предложенных вариантов Курская (кольцевая)
5. Выставляем жанр "Драма" и "Комедия"
6. Ставим чекбокс "Только сеансы в 2D"
7. Жмем "Подобрать"
8. Убедиться что открыта страница "Киноафиша Москвы" и на форме выставлены заданные параметры.

Все действия необходимо логировать.
Результатом теста является ответ на вопрос: соответствует функционал требованиям?
 */