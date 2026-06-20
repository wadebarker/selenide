package org.example.tests.integration.profile;

import org.example.config.Config;
import org.example.data.ProfileTestData;
import org.example.pages.ProfilePage;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.codeborne.selenide.WebDriverRunner.url;
import static org.example.data.ProfileTestData.VALID_PERSONAL_INFO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileTest extends BaseTest {

    private ProfilePage profilePage;

    @BeforeAll
    void authenticate() {
        profilePage = new ProfilePage();
        profilePage.openAuthenticated();
    }

    @Test
    @DisplayName("Профиль открывается после авторизации")
    void profileOpensAfterLogin() {
        assertEquals(
                Config.BASE_URL + "/profile",
                url(),
                "Ожидался URL страницы профиля"
        );
        assertTrue(
                profilePage.isVisible(ProfilePage.FORM),
                "Форма личной информации должна быть видима"
        );
    }

    @Test
    @DisplayName("Отображается имя пользователя")
    void displaysUserName() {
        assertEquals(
                ProfileTestData.EXPECTED_USER_NAME,
                profilePage.getUserName(),
                "Имя пользователя не совпадает с ожидаемым"
        );
    }

    @Test
    @DisplayName("Навигация профиля")
    void profileNavigation() {
        assertTrue(
                profilePage.isPersonalInfoTabActive(),
                "Вкладка «Личная информация» должна быть активной"
        );
        assertTrue(
                profilePage.isVisible(ProfilePage.AUTHORIZATION_TAB),
                "Вкладка «Авторизация» должна быть видима"
        );
        assertEquals(
                ProfileTestData.PERSONAL_INFO_TAB,
                profilePage.getActiveTabText(),
                "Активной должна быть вкладка «Личная информация»"
        );
    }

    @Test
    @DisplayName("Форма личной информации содержит все поля")
    void personalInfoFormContainsAllFields() {
        assertTrue(profilePage.isVisible(ProfilePage.SURNAME_INPUT));
        assertTrue(profilePage.isVisible(ProfilePage.NAME_INPUT));
        assertTrue(profilePage.isVisible(ProfilePage.PATRONYMIC_INPUT));
        assertTrue(profilePage.isVisible(ProfilePage.DATE_OF_BIRTH_INPUT));
        assertTrue(profilePage.isVisible(ProfilePage.SEX_SELECT));
        assertTrue(profilePage.isVisible(ProfilePage.PHONE_INPUT));
        assertTrue(profilePage.isVisible(ProfilePage.SAVE_BUTTON));
    }

    @Test
    @DisplayName("Успешное сохранение личной информации")
    void successfulPersonalInfoUpdate() {
        profilePage.fillPersonalInfo(
                VALID_PERSONAL_INFO.get("surname"),
                VALID_PERSONAL_INFO.get("name"),
                VALID_PERSONAL_INFO.get("patronymic"),
                VALID_PERSONAL_INFO.get("dateOfBirth"),
                VALID_PERSONAL_INFO.get("phone")
        );
        profilePage.selectSex(VALID_PERSONAL_INFO.get("sex"));
        profilePage.save();
        profilePage.open();
        profilePage.waitUntilLoaded();

        assertEquals(
                VALID_PERSONAL_INFO.get("surname"),
                profilePage.getInputValue(ProfilePage.SURNAME_INPUT)
        );
        assertEquals(
                VALID_PERSONAL_INFO.get("name"),
                profilePage.getInputValue(ProfilePage.NAME_INPUT)
        );
        assertEquals(
                VALID_PERSONAL_INFO.get("patronymic"),
                profilePage.getInputValue(ProfilePage.PATRONYMIC_INPUT)
        );
        assertEquals(
                VALID_PERSONAL_INFO.get("dateOfBirth"),
                profilePage.getInputValue(ProfilePage.DATE_OF_BIRTH_INPUT)
        );
        assertEquals(
                VALID_PERSONAL_INFO.get("phone"),
                profilePage.getInputValue(ProfilePage.PHONE_INPUT)
        );
        assertEquals(
                VALID_PERSONAL_INFO.get("sex"),
                profilePage.getSelectedSex()
        );
    }
}
