package org.example.pages;

import org.example.config.Config;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class RegisterPage extends BasePage {

    private static final String PARENT = ".Register_content__MmAGw";

    public static final String EMAIL_INPUT = PARENT + " input[type='email']";
    public static final String PASSWORD_INPUT = PARENT + " #register-pass";
    public static final String CONFIRM_PASSWORD_INPUT = PARENT + " #register-pass-two";
    public static final String SUBMIT_BUTTON = PARENT + " button[type='submit']";
    public static final String ERROR_BLOCK = PARENT + " .text-red-500";

    @Override
    protected String getUrl() {
        return Config.BASE_URL + "/auth/register";
    }

    public void register(String email, String password, String confirmPassword) {
        open();
        fill(EMAIL_INPUT, email);
        fill(PASSWORD_INPUT, password);
        fill(CONFIRM_PASSWORD_INPUT, confirmPassword);
        click(SUBMIT_BUTTON);
    }

    public void registerExpectingSuccess(String email, String password, String confirmPassword) {
        register(email, password, confirmPassword);
        webdriver().shouldNotHave(urlContaining("/auth/register"), Duration.ofSeconds(15));
    }

    public String getError() {
        return getErrorText(ERROR_BLOCK);
    }
}