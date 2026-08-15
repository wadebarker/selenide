package org.example.tests.integration.profile;

import org.example.config.Config;
import org.example.pages.ProfileAuthPage;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileAuthTest extends BaseTest {

    private ProfileAuthPage authPage;

    @BeforeAll
    void authenticate() {
        authPage = new ProfileAuthPage();
        authPage.openAuthenticated();
    }

    @Test
    @DisplayName("Вкладка авторизации открывается после авторизации")
    void authTabOpensAfterLogin() {
        assertEquals(
                Config.BASE_URL + "/profile/authorization",
                url(),
                "Ожидался URL страницы авторизации профиля"
        );
        assertTrue(
                authPage.isVisible(ProfileAuthPage.EMAIL_CONTAINER),
                "Секция 'Почта' должна быть видима"
        );
        assertTrue(
                authPage.isVisible(ProfileAuthPage.PASSWORD_CONTAINER),
                "Секция 'Смена пароля' должна быть видима"
        );
    }

    @Test
    @DisplayName("Секция 'Почта' содержит ожидаемые поля")
    void emailSectionContainsAllFields() {
        assertTrue(authPage.isVisible(ProfileAuthPage.EMAIL_INPUT), "Поле ввода почты должно быть видимо");
        assertTrue(authPage.isVisible(ProfileAuthPage.EMAIL_PASSWORD_INPUT), "Поле подтверждения пароля для почты должно быть видимо");
        assertTrue(authPage.isVisible(ProfileAuthPage.EMAIL_SAVE_BUTTON), "Кнопка сохранения почты должна быть видима");
    }

    @Test
    @DisplayName("Секция 'Смена пароля' содержит ожидаемые поля")
    void changePasswordSectionContainsAllFields() {
        assertTrue(authPage.isVisible(ProfileAuthPage.CURRENT_PASSWORD_INPUT), "Поле текущего пароля должно быть видимо");
        assertTrue(authPage.isVisible(ProfileAuthPage.NEW_PASSWORD_INPUT), "Поле нового пароля должно быть видимо");
        assertTrue(authPage.isVisible(ProfileAuthPage.CHECK_NEW_PASSWORD_INPUT), "Поле повторного пароля должно быть видимо");
        assertTrue(authPage.isVisible(ProfileAuthPage.PASSWORD_SAVE_BUTTON), "Кнопка сохранения пароля должна быть видима");
    }

    @Test
    @DisplayName("Поля секции 'Почта' принимают ввод (проверка на уровне DOM)")
    void emailFieldsAcceptInput() {
        final String testEmail = "vadim_zviagintsev555@mail.ru";
        final String confirmPassword = "Qwerty123";

        authPage.setEmail(testEmail);
        authPage.setEmailPassword(confirmPassword);

        assertEquals(
                testEmail,
                authPage.getInputValue(ProfileAuthPage.EMAIL_INPUT),
                "Ожидалось введённое значение email в поле"
        );
        assertEquals(
                confirmPassword,
                authPage.getInputValue(ProfileAuthPage.EMAIL_PASSWORD_INPUT),
                "Ожидалось значение в поле подтверждения пароля для почты"
        );
    }

    @Test
    @DisplayName("Поля секции 'Смена пароля' принимают ввод (проверка на уровне DOM)")
    void passwordFieldsAcceptInput() {
        final String currentPassword = "Qwerty123";
        final String newPassword = "Qwerty456";

        authPage.setCurrentPassword(currentPassword);
        authPage.setNewPassword(newPassword);
        authPage.setCheckNewPassword(newPassword);

        assertEquals(
                currentPassword,
                authPage.getInputValue(ProfileAuthPage.CURRENT_PASSWORD_INPUT),
                "Ожидалось значение в поле текущего пароля"
        );
        assertEquals(
                newPassword,
                authPage.getInputValue(ProfileAuthPage.NEW_PASSWORD_INPUT),
                "Ожидалось значение в поле нового пароля"
        );
        assertEquals(
                newPassword,
                authPage.getInputValue(ProfileAuthPage.CHECK_NEW_PASSWORD_INPUT),
                "Ожидалось значение в поле повторного пароля"
        );
    }
}