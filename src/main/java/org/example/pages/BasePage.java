package org.example.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage {

    protected abstract String getUrl();

    public void open() {
        Selenide.open(getUrl());
    }

    protected void fill(String locator, String value) {
        $(locator).shouldBe(visible).setValue(value);
    }

    protected void click(String locator) {
        $(locator).shouldBe(visible).click();
    }

    public boolean isVisible(String locator) {
        return $(locator).is(visible);
    }

    protected String getErrorText(String errorLocator) {
        return $(errorLocator).shouldBe(visible).text().trim();
    }
}