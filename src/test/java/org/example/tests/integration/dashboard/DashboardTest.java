package org.example.tests.integration.dashboard;

import org.example.config.Config;
import org.example.data.DashboardTestData;
import org.example.pages.DashboardPage;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import utils.DataFactory;
import utils.DateFactory;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;
    private String minDate;
    private String maxDate;

    @BeforeAll
    void authenticate() {
        dashboardPage = new DashboardPage();
        dashboardPage.openAuthenticated();
        minDate = dashboardPage.getDateMin();
        maxDate = dashboardPage.getDateMax();
    }

    @BeforeEach
    void prepareForm() {
        dashboardPage.dismissSuccessModalIfVisible();
        dashboardPage.open();
        dashboardPage.waitUntilLoaded();
        minDate = dashboardPage.getDateMin();
        maxDate = dashboardPage.getDateMax();
    }

    @Test
    @DisplayName("Главная открывается после авторизации")
    void dashboardOpensAfterLogin() {
        assertEquals(
                Config.BASE_URL + "/",
                url(),
                "Ожидался URL главной страницы"
        );
        assertTrue(
                dashboardPage.isVisible(DashboardPage.FORM),
                "Форма создания задачи должна быть видима"
        );
    }

    @Test
    @DisplayName("Форма создания задачи содержит все поля")
    void createTaskFormContainsAllFields() {
        assertTrue(dashboardPage.isVisible(DashboardPage.TITLE_INPUT));
        assertTrue(dashboardPage.isVisible(DashboardPage.DESCRIPTION_INPUT));
        assertTrue(dashboardPage.isVisible(DashboardPage.DATE_INPUT));
        assertTrue(dashboardPage.isVisible(DashboardPage.TIME_INPUT));
        assertTrue(dashboardPage.isVisible(DashboardPage.SUBMIT_BUTTON));
        assertTrue(dashboardPage.isVisible(DashboardPage.RESET_BUTTON));
        assertTrue(dashboardPage.isVisible(DashboardPage.SEARCH_INPUT));
    }

    @Test
    @DisplayName("Диапазон даты — от сегодня до 7 дней вперёд")
    void dateRangeIsLimitedToSevenDays() {
        assertEquals(
                DashboardTestData.DATE_RANGE_DAYS,
                DateFactory.daysBetween(minDate, maxDate),
                "Разница между min и max датой должна быть 7 дней"
        );
    }

    @Test
    @DisplayName("Диапазон времени — от 06:00 до 23:00")
    void timeRangeIsLimited() {
        assertEquals(DashboardTestData.TIME_MIN, dashboardPage.getTimeMin());
        assertEquals(DashboardTestData.TIME_MAX, dashboardPage.getTimeMax());
    }

    @Test
    @DisplayName("Успешное создание задачи со всеми полями")
    void successfulTaskCreationWithAllFields() {
        String title = "Task " + System.currentTimeMillis();

        dashboardPage.createTaskExpectingSuccess(
                title,
                DataFactory.generateDescription(),
                minDate,
                DashboardTestData.VALID_TIME
        );

        assertTrue(
                dashboardPage.isTaskVisible(title),
                "Созданная задача должна отображаться в списке"
        );
    }

    @Test
    @DisplayName("Успешное создание задачи без описания")
    void successfulTaskCreationWithoutDescription() {
        String title = "NoDesc " + System.currentTimeMillis();

        dashboardPage.createTaskExpectingSuccess(
                title,
                DataFactory.generateDescription(),
                minDate,
                DashboardTestData.VALID_TIME
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Успешное создание задачи на минимально допустимую дату")
    void successfulTaskCreationOnMinDate() {
        String title = "MinDate " + System.currentTimeMillis();

        dashboardPage.createTaskExpectingSuccess(
                title,
                DataFactory.generateDescription(),
                minDate,
                DashboardTestData.VALID_TIME
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Успешное создание задачи на максимально допустимую дату")
    void successfulTaskCreationOnMaxDate() {
        String title = "MaxDate " + System.currentTimeMillis();

        dashboardPage.createTaskExpectingSuccess(
                title,
                maxDate,
                DashboardTestData.VALID_TIME
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Успешное создание задачи с заголовком из 1 символа")
    void successfulTaskCreationWithMinTitleLength() {
        String title = DataFactory.generateText(DashboardTestData.TITLE_MIN_LENGTH);

        dashboardPage.createTaskExpectingSuccess(
                title,
                minDate,
                DashboardTestData.VALID_TIME
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Успешное создание задачи с заголовком из 50 символов")
    void successfulTaskCreationWithMaxTitleLength() {
        String title = DataFactory.generateText(DashboardTestData.TITLE_MAX_LENGTH);

        dashboardPage.createTaskExpectingSuccess(
                title,
                minDate,
                DashboardTestData.VALID_TIME
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Успешное создание задачи с описанием из 1 символа")
    void successfulTaskCreationWithMinDescriptionLength() {
        String title = "MinDesc " + System.currentTimeMillis();

        dashboardPage.createTaskExpectingSuccess(
                title,
                DataFactory.generateText(DashboardTestData.DESCRIPTION_MIN_LENGTH),
                minDate,
                DashboardTestData.VALID_TIME
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Успешное создание задачи с описанием из 200 символов")
    void successfulTaskCreationWithMaxDescriptionLength() {
        String title = "MaxDesc " + System.currentTimeMillis();

        dashboardPage.createTaskExpectingSuccess(
                title,
                DataFactory.generateText(DashboardTestData.DESCRIPTION_MAX_LENGTH),
                minDate,
                DashboardTestData.VALID_TIME
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Успешное создание задачи на минимально допустимое время")
    void successfulTaskCreationOnMinTime() {
        String title = "TimeMin " + System.currentTimeMillis();

        dashboardPage.createTaskExpectingSuccess(
                title,
                minDate,
                DashboardTestData.TIME_MIN
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Успешное создание задачи на максимальное допустимое время")
    void successfulTaskCreationOnMaxTime() {
        String title = "TimeMax " + System.currentTimeMillis();

        dashboardPage.createTaskExpectingSuccess(
                title,
                maxDate,
                DashboardTestData.TIME_MAX
        );

        assertTrue(dashboardPage.isTaskVisible(title));
    }

    @Test
    @DisplayName("Негативный сценарий: пустая дата")
    void negativeEmptyDate() {
        dashboardPage.fillTitle("Test task");
        dashboardPage.fillTime(DashboardTestData.VALID_TIME);
        dashboardPage.submitExpectingError();

        assertTrue(
                dashboardPage.getError().contains(DashboardTestData.EMPTY_FIELD_ERROR),
                "Ожидалась ошибка о пустом обязательном поле"
        );
    }

    @Test
    @DisplayName("Негативный сценарий: дата в прошлом")
    void negativePastDate() {
        String title = "Past " + System.currentTimeMillis();
        dashboardPage.fillTitle(title);
        dashboardPage.fillDate("2020-01-01");
        dashboardPage.fillTime(DashboardTestData.VALID_TIME);
        dashboardPage.submitExpectingValidationFailure(title);
    }

    @Test
    @DisplayName("Негативный сценарий: дата более чем на 7 дней вперёд")
    void negativeDateBeyondAllowedRange() {
        String title = "Future " + System.currentTimeMillis();
        dashboardPage.fillTitle(title);
        dashboardPage.fillDate("2030-01-01");
        dashboardPage.fillTime(DashboardTestData.VALID_TIME);
        dashboardPage.submitExpectingValidationFailure(title);
    }

    @Test
    @DisplayName("Негативный сценарий: описание длиннее 200 символов")
    void negativeDescriptionTooLong() {
        String title = "LongDesc " + System.currentTimeMillis();
        dashboardPage.fillTitle(title);
        dashboardPage.fillDescription(DataFactory.generateLongDescription());
        dashboardPage.fillDate(minDate);
        dashboardPage.fillTime(DashboardTestData.VALID_TIME);
        dashboardPage.submitExpectingValidationFailure(title);

        if (dashboardPage.isVisible(DashboardPage.ERROR_BLOCK)) {
            assertTrue(
                    dashboardPage.getError().contains(DashboardTestData.EMPTY_FIELD_ERROR),
                    "Ожидалась ошибка при слишком длинном описании"
            );
        }
    }
}
