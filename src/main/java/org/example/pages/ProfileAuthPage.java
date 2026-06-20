package org.example.pages;

import org.example.config.Config;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ProfileAuthPage extends BasePage {

    public static final String NAV = ".NavLine_nav__JR5gH";
    public static final String PERSONAL_INFO_TAB = NAV + " a[href='/profile']";
    public static final String AUTHORIZATION_TAB = NAV + " a[href='/profile/authorization']";
    public static final String ACTIVE_TAB = NAV + " .NavLine_active__w2KGW";

    private static final String PARENT = ".Authorization_wrapper__vtNRF";
    private static final String CONTAINER = PARENT + " .Authorization_container__gFYDn";

    // Секция "Почта" — первый контейнер
    public static final String EMAIL_CONTAINER = CONTAINER + ":nth-of-type(1)";
    public static final String EMAIL_SECTION = EMAIL_CONTAINER + " h2"; // заголовок "Почта"
    public static final String EMAIL_INPUT = EMAIL_CONTAINER + " input[name='email']";
    public static final String EMAIL_PASSWORD_INPUT = EMAIL_CONTAINER + " input#pass-id-email";
    public static final String EMAIL_SAVE_BUTTON = EMAIL_CONTAINER + " button[type='button'], " + EMAIL_CONTAINER + " button";

    // Секция "Смена пароля" — второй контейнер
    public static final String PASSWORD_CONTAINER = CONTAINER + ":nth-of-type(2)";
    public static final String CHANGE_PASSWORD_SECTION = PASSWORD_CONTAINER + " h2"; // заголовок "Смена пароля"
    public static final String CURRENT_PASSWORD_INPUT = PASSWORD_CONTAINER + " input[name='password']"; // id='asdf'
    public static final String NEW_PASSWORD_INPUT = PASSWORD_CONTAINER + " input[name='newpass']";     // id='1asdf1234'
    public static final String CHECK_NEW_PASSWORD_INPUT = PASSWORD_CONTAINER + " input[name='checkNewPass']"; // id='asdflkjwq'
    public static final String PASSWORD_SAVE_BUTTON = PASSWORD_CONTAINER + " button[type='button'], " + PASSWORD_CONTAINER + " button";

    @Override
    protected String getUrl() {
        return Config.BASE_URL + "/profile/authorization";
    }

    /**
     * Открываем страницу без предварительной аутентификации.
     */
    public void open() {
        super.open();
    }

    /**
     * Открываем страницу в аутентифицированном контексте.
     * Повторяем подход ProfilePage.openAuthenticated: логинимся через LoginPage, затем открываем профиль и ждём загрузки.
     */
    public void openAuthenticated() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginExpectingSuccess(
                Config.LoginCredentials.EMAIL,
                Config.LoginCredentials.PASSWORD
        );
        open();
        waitUntilLoaded();
    }

    /**
     * Ожидание загрузки вкладки авторизации.
     */
    public void waitUntilLoaded() {
        webdriver().shouldHave(urlContaining("/profile/authorization"), Duration.ofSeconds(15));
        $(PARENT).shouldBe(visible, Duration.ofSeconds(30));
        $(EMAIL_SECTION).shouldBe(visible);
        $(CHANGE_PASSWORD_SECTION).shouldBe(visible);
    }

    public String getInputValue(String locator) {
        return $(locator).shouldBe(visible).getValue();
    }

    // --- Взаимодействие с секцией "Почта" ---

    public void setEmail(String email) {
        $(EMAIL_INPUT).shouldBe(visible).setValue(email);
    }

    public void setEmailPassword(String password) {
        $(EMAIL_PASSWORD_INPUT).shouldBe(visible).setValue(password);
    }

    public void submitEmailForm() {
        $(EMAIL_SAVE_BUTTON).shouldBe(visible).click();
    }

    // --- Взаимодействие с секцией "Смена пароля" ---

    public void setCurrentPassword(String password) {
        $(CURRENT_PASSWORD_INPUT).shouldBe(visible).setValue(password);
    }

    public void setNewPassword(String password) {
        $(NEW_PASSWORD_INPUT).shouldBe(visible).setValue(password);
    }

    public void setCheckNewPassword(String password) {
        $(CHECK_NEW_PASSWORD_INPUT).shouldBe(visible).setValue(password);
    }

    public void submitPasswordForm() {
        $(PASSWORD_SAVE_BUTTON).shouldBe(visible).click();
    }
}