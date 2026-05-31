package org.example.pages;

import org.example.config.Config;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {

    private static final String PARENT = ".Login_content__AK6sN";

    public static final String EMAIL_INPUT = PARENT + " input[type='email']";
    public static final String PASSWORD_INPUT = PARENT + " input[type='password']";
    public static final String SUBMIT_BUTTON = PARENT + " button[type='submit']";
    public static final String RESET_BUTTON = PARENT + " button[type='reset']";
    public static final String ERROR_BLOCK = PARENT + " .text-red-500";

    @Override
    protected String getUrl() {
        return Config.BASE_URL + "/auth/login";
    }

    public void login(String email, String password) {
        open();
        fill(EMAIL_INPUT, email);
        fill(PASSWORD_INPUT, password);
        click(SUBMIT_BUTTON);
    }

    public String getError() {
        return getErrorText(ERROR_BLOCK);
    }
}