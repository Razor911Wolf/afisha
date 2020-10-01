import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

/**
 * Класс тестирования "афиша мэйл"
 */
public class AfishaTesting extends WebDiverSettings {

    private final static Logger logger = LogManager.getLogger(AfishaTesting.class);

    private AfishaPage afishaPage;
    private MoviePosterMoscowPage moviePosterMoscowPage;

    /**
     * test_01
     */
    @Test
    public void test_01() {
        logger.info("start: test_01");

        // initialize a AfishaPage
        afishaPage = PageFactory.initElements(driver, AfishaPage.class);
        logger.info("AfishaPage initialized");

        // open Afisha page
        afishaPage.openPage();
        logger.info("AfishaPage opened");

        // check page title
        afishaPage.checkPageTitle("Кино Mail.ru — фильмы, сериалы и телешоу из самых популярных онлайн-кинотеатров");
        logger.info("Page title checked");

        // click go to the cinema
        afishaPage.clickToTheCinema();
        logger.info("go to the cinema clicked");

        // click on checkbox "only2d"
        afishaPage.clickCinema2d();
        logger.info("checkbox \"only2d\" clicked");

        // set day
        afishaPage.setDay("Завтра");
        logger.info("day chosen: " + afishaPage.getDay());

        // set metro station
        afishaPage.setMetroStation("Курская", 68);
        logger.info("metro station inputed: " + afishaPage.getStationMap()); //курская кольцевая id=68

        // set genre
        afishaPage.setGenre("драма");
        logger.info("ganre inputed: " + afishaPage.getGenreList());

        // set genre
        afishaPage.setGenre("комедия");
        logger.info("ganre inputed: " + afishaPage.getGenreList());

        // click pick up films
        afishaPage.clickPickUpFilms();
        logger.info("pick up films clicked");

        // initialize a moviePosterMoscowPage
        moviePosterMoscowPage = PageFactory.initElements(driver, MoviePosterMoscowPage.class);
        logger.info("MoviePosterMoscowPage initialized");

        // check page title
        moviePosterMoscowPage.checkPageTitle("Киноафиша Москвы");
        logger.info("Page title checked");

        // get report
        Assert.assertTrue(moviePosterMoscowPage.getReport(afishaPage));
        logger.info("The functionality meets the requirements");

        logger.info("end: test_01");
    }

    /**
     * test_02
     */
    @Ignore
    @Test
    public void test_02() {
        logger.info("start: test_02");
        afishaPage = PageFactory.initElements(driver, AfishaPage.class);
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
        moviePosterMoscowPage = PageFactory.initElements(driver, MoviePosterMoscowPage.class);
        moviePosterMoscowPage.checkPageTitle("Киноафиша Москвы");
        Assert.assertTrue(moviePosterMoscowPage.getReport(afishaPage));
        logger.info("The functionality meets the requirements");
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