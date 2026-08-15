package org.example.components;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Header {

    public static final String SEARCH_INPUT = ".Header_search-input__d5jfb";
    public static final String HOME_LINK = ".Header_list__YowdC a[href='/']";
    public static final String PROFILE_LINK = ".Header_list__YowdC a[href='/profile']";
    public static final String LOGOUT_BUTTON = ".Header_logout__vw_E5";
    public static final String LOGIN_LINK = ".Header_list__YowdC a[href='/auth/login']";
    public static final String REGISTER_LINK = ".Header_list__YowdC a[href='/auth/register']";

    public void logout() {
        $(LOGOUT_BUTTON).shouldBe(visible).click();
        $(LOGOUT_BUTTON).shouldNotBe(visible, Duration.ofSeconds(15));
    }

    public void goToProfile() {
        $(PROFILE_LINK).shouldBe(visible).click();
    }

    public void goToHome() {
        $(HOME_LINK).shouldBe(visible).click();
    }

    public boolean isLoggedIn() {
        return $(LOGOUT_BUTTON).is(visible);
    }
}
