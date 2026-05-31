package org.example.tests.auth;

import org.example.config.Config;
import org.example.pages.RegisterPage;
import org.example.data.RegisterTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.DataFactory;

import java.util.Map;
import java.util.stream.Stream;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterTest {

    private RegisterPage registerPage;

    @BeforeEach
    void setUp() {
        registerPage = new RegisterPage();
    }

    @Test
    @DisplayName("Успешная регистрация")
    void successfulRegistration() {

        String email = DataFactory.generateEmail();
        String password = DataFactory.generatePassword();

        registerPage.register(
                email,
                password,
                password
        );

        String currentUrl = url();

        assertTrue(
                currentUrl.equals(Config.BASE_URL + "/"),
                "Ожидался редирект после регистрации. Текущий URL: "
                        + currentUrl
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("negativeRegisterCases")
    @DisplayName("Негативные сценарии регистрации")
    void negativeRegistration(Map<String, String> caseData) {

        registerPage.register(
                caseData.get("email"),
                caseData.get("password"),
                caseData.get("confirm_password")
        );

        String actualError = registerPage.getError();

        assertTrue(
                actualError.contains(caseData.get("error_message")),
                "Ожидалась ошибка: "
                        + caseData.get("error_message")
                        + ", фактически получено: "
                        + actualError
        );
    }

    static Stream<Map<String, String>> negativeRegisterCases() {
        return RegisterTestData.NEGATIVE_REGISTER_CASES.stream();
    }
}