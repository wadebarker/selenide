package org.example.tests.integration.auth;

import org.example.config.Config;
import org.example.data.LoginTestData;
import org.example.pages.LoginPage;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Успешная авторизация")
    void successfulLogin() {

        loginPage.login(
                Config.LoginCredentials.EMAIL,
                Config.LoginCredentials.PASSWORD
        );

        String currentUrl = url();

        assertTrue(
                currentUrl.equals(Config.BASE_URL + "/"),
                "Ожидался редирект на главную страницу или профиль. Текущий URL: "
                        + currentUrl
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("negativeLoginCases")
    @DisplayName("Негативные сценарии логина")
    void negativeLogin(Map<String, String> caseData) {

        loginPage.login(
                caseData.get("email"),
                caseData.get("password")
        );

        String actualError = loginPage.getError();

        assertTrue(
                actualError.contains(caseData.get("error")),
                "Ожидалась ошибка: "
                        + caseData.get("error")
                        + ", фактически получено: "
                        + actualError
        );
    }

    static Stream<Map<String, String>> negativeLoginCases() {
        return LoginTestData.NEGATIVE_LOGIN_CASES.stream();
    }
}