package org.example.tests.e2e.profile;

import org.example.components.Header;
import org.example.config.Config;
import org.example.pages.DashboardPage;
import org.example.pages.LoginPage;
import org.example.pages.ProfileAuthPage;
import org.example.pages.RegisterPage;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DataFactory;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileChangePasswordTest extends BaseTest {

    @Test
    @DisplayName("E2E: смена пароля и вход с новым паролем")
    void changePasswordAndLoginWithNewPassword() {
        String email = DataFactory.generateEmail();
        String password = DataFactory.generatePassword();
        String newPassword = DataFactory.generatePassword();

        new RegisterPage().registerExpectingSuccess(email, password, password);

        ProfileAuthPage authPage = new ProfileAuthPage();
        authPage.open();
        authPage.waitUntilLoaded();
        authPage.changePassword(password, newPassword);

        new Header().logout();

        new LoginPage().loginExpectingSuccess(email, newPassword);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.waitUntilLoaded();

        assertEquals(
                Config.BASE_URL + "/",
                url(),
                "После входа с новым паролем ожидалась главная страница"
        );
        assertTrue(
                new Header().isLoggedIn(),
                "Пользователь должен быть авторизован после входа с новым паролем"
        );
    }
}
