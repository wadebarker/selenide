package org.example.tests.integration.profile;

import org.example.config.Config;
import org.example.data.ProfileAuthTestData;
import org.example.pages.ProfileAuthPage;
import org.example.pages.RegisterPage;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.DataFactory;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileAuthNegativeTest extends BaseTest {

    private ProfileAuthPage authPage;

    @BeforeAll
    void registerFreshUser() {
        String email = DataFactory.generateEmail();
        new RegisterPage().registerExpectingSuccess(
                email,
                Config.LoginCredentials.PASSWORD,
                Config.LoginCredentials.PASSWORD
        );
        authPage = new ProfileAuthPage();
        authPage.open();
        authPage.waitUntilLoaded();
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("negativeChangeEmailCases")
    @DisplayName("Негативные сценарии смены почты")
    void negativeChangeEmail(Map<String, String> caseData) {
        authPage.reload();
        authPage.changeEmail(
                caseData.get("email"),
                caseData.get("password")
        );

        assertTrue(
                authPage.matchesValidation(
                        caseData.get("invalid_field"),
                        caseData.getOrDefault("validation_mode", "all")
                ),
                "Ожидалась подсветка невалидных полей: " + caseData.get("invalid_field")
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("negativeChangePasswordCases")
    @DisplayName("Негативные сценарии смены пароля")
    void negativeChangePassword(Map<String, String> caseData) {
        authPage.reload();
        authPage.changePassword(
                caseData.get("current_password"),
                caseData.get("new_password"),
                caseData.get("confirm_password")
        );

        assertTrue(
                authPage.matchesValidation(
                        caseData.get("invalid_field"),
                        caseData.getOrDefault("validation_mode", "all")
                ),
                "Ожидалась подсветка невалидных полей: " + caseData.get("invalid_field")
        );
    }

    static Stream<Map<String, String>> negativeChangeEmailCases() {
        return ProfileAuthTestData.NEGATIVE_CHANGE_EMAIL_CASES.stream();
    }

    static Stream<Map<String, String>> negativeChangePasswordCases() {
        return ProfileAuthTestData.NEGATIVE_CHANGE_PASSWORD_CASES.stream();
    }
}
