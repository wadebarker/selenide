package org.example.tests.profile;

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
    @DisplayName("Ввод и сохранение почты (проверка на уровне DOM)")
    void successfulEmailUpdateDomLevel() {
        final String testEmail = "test.user@example.com";
        final String confirmPassword = "currentPassword123";

        authPage.setEmail(testEmail);
        authPage.setEmailPassword(confirmPassword);
        authPage.submitEmailForm();

        // Перезагрузим страницу и проверим DOM-значения аналогично ProfileTest
        authPage.open();
        authPage.waitUntilLoaded();

        assertEquals(
                testEmail,
                authPage.getInputValue(ProfileAuthPage.EMAIL_INPUT),
                "Ожидалось сохранённое значение email в поле"
        );
        assertEquals(
                confirmPassword,
                authPage.getInputValue(ProfileAuthPage.EMAIL_PASSWORD_INPUT),
                "Ожидалось значение в поле подтверждения пароля для почты"
        );
    }
}