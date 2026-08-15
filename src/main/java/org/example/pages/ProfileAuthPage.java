package org.example.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.config.Config;

import java.time.Duration;
import java.util.Arrays;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ProfileAuthPage extends BasePage {

    private static final String ERROR_CLASS = "Authorization_red__1O3oo";

    public static final String NAV = ".NavLine_nav__JR5gH";
    public static final String PERSONAL_INFO_TAB = NAV + " a[href='/profile']";
    public static final String AUTHORIZATION_TAB = NAV + " a[href='/profile/authorization']";
    public static final String ACTIVE_TAB = NAV + " .NavLine_active__w2KGW";

    private static final String PARENT = ".Authorization_wrapper__vtNRF";
    private static final String CONTAINER = PARENT + " .Authorization_container__gFYDn";

    public static final String EMAIL_CONTAINER = CONTAINER + ":nth-of-type(1)";
    public static final String EMAIL_SECTION = EMAIL_CONTAINER + " h2";
    public static final String EMAIL_INPUT = EMAIL_CONTAINER + " input[name='email']";
    public static final String EMAIL_PASSWORD_INPUT = EMAIL_CONTAINER + " input#pass-id-email";
    public static final String EMAIL_SAVE_BUTTON = EMAIL_CONTAINER + " .Authorization_buttons__Jfl4_ button";

    public static final String PASSWORD_CONTAINER = CONTAINER + ":nth-of-type(2)";
    public static final String CHANGE_PASSWORD_SECTION = PASSWORD_CONTAINER + " h2";
    public static final String CURRENT_PASSWORD_INPUT = PASSWORD_CONTAINER + " input[name='password']";
    public static final String NEW_PASSWORD_INPUT = PASSWORD_CONTAINER + " input[name='newpass']";
    public static final String CHECK_NEW_PASSWORD_INPUT = PASSWORD_CONTAINER + " input[name='checkNewPass']";
    public static final String PASSWORD_SAVE_BUTTON = PASSWORD_CONTAINER + " .Authorization_buttons__Jfl4_ button";

    @Override
    protected String getUrl() {
        return Config.BASE_URL + "/profile/authorization";
    }

    public void openAuthenticated() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginExpectingSuccess(
                Config.LoginCredentials.EMAIL,
                Config.LoginCredentials.PASSWORD
        );
        open();
        waitUntilLoaded();
    }

    public void waitUntilLoaded() {
        webdriver().shouldHave(urlContaining("/profile/authorization"), Duration.ofSeconds(15));
        $(PARENT).shouldBe(visible, Duration.ofSeconds(30));
        $(EMAIL_SECTION).shouldBe(visible);
        $(CHANGE_PASSWORD_SECTION).shouldBe(visible);
    }

    public void reload() {
        open();
        waitUntilLoaded();
    }

    public String getInputValue(String locator) {
        return $(locator).shouldBe(visible).getValue();
    }

    public String getActiveTabText() {
        return $(ACTIVE_TAB).shouldBe(visible).text().trim();
    }

    public boolean isAuthorizationTabActive() {
        return $(AUTHORIZATION_TAB).getAttribute("class").contains("NavLine_active__w2KGW");
    }

    public String getEmailSectionTitle() {
        return $(EMAIL_SECTION).shouldBe(visible).text().trim();
    }

    public String getPasswordSectionTitle() {
        return $(CHANGE_PASSWORD_SECTION).shouldBe(visible).text().trim();
    }

    public boolean isFieldInvalid(String field) {
        return hasErrorClass(resolveFieldLocator(field));
    }

    public boolean areFieldsInvalid(String... fields) {
        return Arrays.stream(fields).allMatch(this::isFieldInvalid);
    }

    public boolean isAnyFieldInvalid(String... fields) {
        return Arrays.stream(fields).anyMatch(this::isFieldInvalid);
    }

    public boolean matchesValidation(String invalidFieldsSpec, String validationMode) {
        String[] fields = invalidFieldsSpec.split(",");
        return "any".equals(validationMode)
                ? isAnyFieldInvalid(fields)
                : areFieldsInvalid(fields);
    }

    public void changeEmail(String email, String password) {
        setEmail(email);
        setEmailPassword(password);
        submitEmailForm();
    }

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        setCurrentPassword(currentPassword);
        setNewPassword(newPassword);
        setCheckNewPassword(confirmPassword);
        submitPasswordForm();
    }

    public void setEmail(String email) {
        fillReactField(EMAIL_INPUT, email, "HTMLInputElement");
    }

    public void setEmailPassword(String password) {
        fillReactField(EMAIL_PASSWORD_INPUT, password, "HTMLInputElement");
    }

    public void setCurrentPassword(String password) {
        fillReactField(CURRENT_PASSWORD_INPUT, password, "HTMLInputElement");
    }

    public void setNewPassword(String password) {
        fillReactField(NEW_PASSWORD_INPUT, password, "HTMLInputElement");
    }

    public void setCheckNewPassword(String password) {
        fillReactField(CHECK_NEW_PASSWORD_INPUT, password, "HTMLInputElement");
    }

    public void submitEmailForm() {
        click(EMAIL_SAVE_BUTTON);
    }

    public void submitPasswordForm() {
        click(PASSWORD_SAVE_BUTTON);
    }

    private void fillReactField(String locator, String value, String prototype) {
        SelenideElement element = $(locator).shouldBe(visible);
        executeJavaScript(
                "const element = arguments[0];"
                        + "const value = arguments[1];"
                        + "const prototype = arguments[2];"
                        + "const setter = Object.getOwnPropertyDescriptor("
                        + "window[prototype].prototype, 'value').set;"
                        + "setter.call(element, value);"
                        + "element.dispatchEvent(new Event('input', { bubbles: true }));"
                        + "element.dispatchEvent(new Event('change', { bubbles: true }));",
                element,
                value,
                prototype
        );
    }

    private boolean hasErrorClass(String locator) {
        String cssClass = $(locator).shouldBe(visible).getAttribute("class");
        return cssClass != null && cssClass.contains(ERROR_CLASS);
    }

    private String resolveFieldLocator(String field) {
        return switch (field) {
            case "email" -> EMAIL_INPUT;
            case "email_password" -> EMAIL_PASSWORD_INPUT;
            case "current_password" -> CURRENT_PASSWORD_INPUT;
            case "new_password" -> NEW_PASSWORD_INPUT;
            case "confirm_password" -> CHECK_NEW_PASSWORD_INPUT;
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        };
    }
}
