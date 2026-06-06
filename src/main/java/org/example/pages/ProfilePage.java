package org.example.pages;

import com.codeborne.selenide.SetValueMethod;
import com.codeborne.selenide.SetValueOptions;
import org.example.config.Config;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class ProfilePage extends BasePage {

    public static final String NAV = ".NavLine_nav__JR5gH";
    public static final String PERSONAL_INFO_TAB = NAV + " a[href='/profile']";
    public static final String AUTHORIZATION_TAB = NAV + " a[href='/profile/authorization']";
    public static final String ACTIVE_TAB = NAV + " .NavLine_active__w2KGW";

    private static final String PARENT = ".Personal_wrapper__osRLL";

    public static final String USER_NAME = PARENT + " .Personal_name__l3BBs";
    public static final String AVATAR = PARENT + " .Personal_avatar__u21J8 img";

    public static final String FORM = PARENT + " .Personal_form__ll2V6";

    public static final String SURNAME_INPUT = FORM + " input[name='surname']";
    public static final String NAME_INPUT = FORM + " input[name='name']";
    public static final String PATRONYMIC_INPUT = FORM + " input[name='patronymic']";
    public static final String DATE_OF_BIRTH_INPUT = FORM + " input[name='dateOfBirth']";
    public static final String SEX_SELECT = FORM + " button[name='sex']";
    public static final String PHONE_INPUT = FORM + " input[name='phone']";
    public static final String SAVE_BUTTON = FORM + " button[type='submit']";

    @Override
    protected String getUrl() {
        return Config.BASE_URL + "/profile";
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
        webdriver().shouldHave(urlContaining("/profile"), Duration.ofSeconds(15));
        $(FORM).shouldBe(visible, Duration.ofSeconds(30));
        $(USER_NAME).shouldBe(visible);
    }

    public String getUserName() {
        return $(USER_NAME).shouldBe(visible).text().trim();
    }

    public String getActiveTabText() {
        return $(ACTIVE_TAB).shouldBe(visible).text().trim();
    }

    public boolean isPersonalInfoTabActive() {
        return $(PERSONAL_INFO_TAB).getAttribute("class").contains("NavLine_active__w2KGW");
    }

    public String getInputValue(String locator) {
        return $(locator).shouldBe(visible).getValue();
    }

    public String getSelectedSex() {
        return $(SEX_SELECT).shouldBe(visible).$(".Personal_selected-value__nkG4k").text().trim();
    }

    public void fillPersonalInfo(
            String surname,
            String name,
            String patronymic,
            String dateOfBirth,
            String phone
    ) {
        fill(SURNAME_INPUT, surname);
        fill(NAME_INPUT, name);
        fill(PATRONYMIC_INPUT, patronymic);
        fillDateOfBirth(dateOfBirth);
        fill(PHONE_INPUT, phone);
    }

    public void fillDateOfBirth(String dateOfBirth) {
        $(DATE_OF_BIRTH_INPUT).shouldBe(visible).setValue(
                SetValueOptions.withText(dateOfBirth).usingMethod(SetValueMethod.JS)
        );
    }

    public void selectSex(String sex) {
        click(SEX_SELECT);
        $$(".Personal_custom-select__QLCXn button").findBy(text(sex)).shouldBe(visible).click();
    }

    public void save() {
        click(SAVE_BUTTON);
    }
}
