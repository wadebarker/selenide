package org.example.tests.e2e.profile;

import org.example.config.Config;
import org.example.pages.ProfileAuthPage;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import utils.DataFactory;

import static com.codeborne.selenide.Selenide.$;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProfileAuthDebugTest extends BaseTest {

    private ProfileAuthPage authPage;

    @BeforeAll
    void authenticate() {
        authPage = new ProfileAuthPage();
        authPage.openAuthenticated();
    }

    @Test
    void debugWrongEmailPassword() {
        authPage.reload();
        authPage.changeEmail(DataFactory.generateEmail(), "wrongpass");
        System.out.println("email red: " + authPage.isFieldInvalid("email"));
        System.out.println("pass red: " + authPage.isFieldInvalid("email_password"));
        System.out.println("any red in form: " + $(ProfileAuthPage.EMAIL_CONTAINER).getAttribute("class"));
    }

    @Test
    void debugWrongCurrentPassword() {
        authPage.reload();
        String pwd = DataFactory.generatePassword();
        authPage.changePassword("wrongpass", pwd, pwd);
        System.out.println("current red: " + authPage.isFieldInvalid("current_password"));
        System.out.println("new red: " + authPage.isFieldInvalid("new_password"));
        System.out.println("confirm red: " + authPage.isFieldInvalid("confirm_password"));
    }
}
