package org.example.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.config.Config;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

public class DashboardPage extends BasePage {

    private static final String PARENT = ".CreateTodo_wrapper__6mEMZ";

    public static final String FORM = PARENT + " .CreateTodo_form__Vir_s";
    public static final String FORM_TITLE = PARENT + " .CreateTodo_title__wakkE";
    public static final String TITLE_INPUT = FORM + " input[name='title']";
    public static final String DESCRIPTION_INPUT = FORM + " textarea[name='description']";
    public static final String DATE_INPUT = FORM + " #date-input-create";
    public static final String TIME_INPUT = FORM + " #time-input-create-how";
    public static final String SUBMIT_BUTTON = FORM + " button[type='submit']";
    public static final String RESET_BUTTON = FORM + " button[type='reset']";
    public static final String ERROR_BLOCK = PARENT + " span.text-red-600";
    public static final String SUCCESS_MODAL = ".Success_overlay__gWfKU";
    public static final String SUCCESS_OK_BUTTON = ".Success_ok__y8HHg";
    public static final String SUCCESS_TITLE = ".Success_title__wgy0b";

    public static final String SEARCH_INPUT = ".Header_search-input__d5jfb";
    public static final String HOME_LINK = ".Header_list__YowdC a[href='/']";
    public static final String PROFILE_LINK = ".Header_list__YowdC a[href='/profile']";
    public static final String LOGOUT_BUTTON = ".Header_logout__vw_E5";
    public static final String TODO_ITEM = ".TodoItem_content__J_7bo";

    @Override
    protected String getUrl() {
        return Config.BASE_URL + "/";
    }

    public void openAuthenticated() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginExpectingSuccess(
                Config.LoginCredentials.EMAIL,
                Config.LoginCredentials.PASSWORD
        );
        waitUntilLoaded();
    }

    public void waitUntilLoaded() {
        webdriver().shouldHave(url(Config.BASE_URL + "/"), Duration.ofSeconds(15));
        $(FORM).shouldBe(visible, Duration.ofSeconds(30));
        $(FORM_TITLE).shouldHave(text("Добавить задачу"));
    }

    public void fillTitle(String title) {
        fillReactField(TITLE_INPUT, title, "HTMLInputElement");
    }

    public void fillDescription(String description) {
        fillReactField(DESCRIPTION_INPUT, description, "HTMLTextAreaElement");
    }

    public void fillDate(String date) {
        fillReactField(DATE_INPUT, date, "HTMLInputElement");
    }

    public void fillTime(String time) {
        fillReactField(TIME_INPUT, time, "HTMLInputElement");
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

    public void createTask(String title, String description, String date, String time) {
        fillTitle(title);
        if (description != null && !description.isEmpty()) {
            fillDescription(description);
        }
        fillDate(date);
        fillTime(time);
        submit();
    }

    public void createTask(String title, String date, String time) {
        createTask(title, null, date, time);
    }

    public void submit() {
        click(SUBMIT_BUTTON);
    }

    public void dismissSuccessModalIfVisible() {
        if ($(SUCCESS_MODAL).is(visible)) {
            click(SUCCESS_OK_BUTTON);
            $(SUCCESS_MODAL).shouldNotBe(visible);
        }
    }

    public boolean isSuccessModalVisible() {
        return isVisible(SUCCESS_MODAL);
    }

    public String getSuccessMessage() {
        return $(SUCCESS_TITLE).shouldBe(visible).text().trim();
    }

    public void resetForm() {
        dismissSuccessModalIfVisible();
        click(RESET_BUTTON);
    }

    public String getError() {
        return getErrorText(ERROR_BLOCK);
    }

    public boolean isTaskVisible(String title) {
        return $$(TODO_ITEM).findBy(text(title)).is(visible);
    }

    public String getDateMin() {
        return $(DATE_INPUT).shouldBe(visible).getAttribute("min");
    }

    public String getDateMax() {
        return $(DATE_INPUT).shouldBe(visible).getAttribute("max");
    }

    public void createTaskExpectingSuccess(String title, String description, String date, String time) {
        createTask(title, description, date, time);
        $(SUCCESS_MODAL).shouldBe(visible, Duration.ofSeconds(10));
        dismissSuccessModalIfVisible();
        waitUntilTaskVisible(title);
    }

    public void createTaskExpectingSuccess(String title, String date, String time) {
        createTaskExpectingSuccess(title, null, date, time);
    }

    public void waitUntilTaskVisible(String title) {
        $$(TODO_ITEM).findBy(text(title)).shouldBe(visible, Duration.ofSeconds(10));
    }

    public String getTimeMin() {
        return $(TIME_INPUT).shouldBe(visible).getAttribute("min");
    }

    public String getTimeMax() {
        return $(TIME_INPUT).shouldBe(visible).getAttribute("max");
    }

    public void submitExpectingValidationFailure(String taskTitle) {
        submit();
        if ($(SUCCESS_MODAL).is(visible)) {
            dismissSuccessModalIfVisible();
            throw new AssertionError("Expected validation failure but task was created: " + taskTitle);
        }
        if (isVisible(ERROR_BLOCK)) {
            return;
        }
        if (isTaskVisible(taskTitle)) {
            throw new AssertionError("Задача не должна появиться в списке: " + taskTitle);
        }
    }

    public void submitExpectingError() {
        submit();
        if ($(SUCCESS_MODAL).is(visible)) {
            throw new AssertionError("Expected validation error but success modal appeared");
        }
        $(ERROR_BLOCK).shouldBe(visible, Duration.ofSeconds(5));
    }

    public String getInputValue(String locator) {
        return $(locator).shouldBe(visible).getValue();
    }
}

