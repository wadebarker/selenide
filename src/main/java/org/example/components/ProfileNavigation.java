package org.example.components;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfileNavigation {

    public static final String NAV = ".NavLine_nav__JR5gH";
    public static final String PERSONAL_INFO_TAB = NAV + " a[href='/profile']";
    public static final String AUTHORIZATION_TAB = NAV + " a[href='/profile/authorization']";
    public static final String ACTIVE_TAB = NAV + " .NavLine_active__w2KGW";

    public void openPersonalInfoTab() {
        $(PERSONAL_INFO_TAB).shouldBe(visible).click();
    }

    public void openAuthorizationTab() {
        $(AUTHORIZATION_TAB).shouldBe(visible).click();
    }

    public String getActiveTabText() {
        return $(ACTIVE_TAB).shouldBe(visible).text().trim();
    }
}
