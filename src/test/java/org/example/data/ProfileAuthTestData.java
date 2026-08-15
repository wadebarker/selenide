package org.example.data;

import org.example.config.Config;
import utils.DataFactory;

import java.util.List;
import java.util.Map;

public class ProfileAuthTestData {

    public static final String EMAIL_SECTION_TITLE = "Почта";
    public static final String PASSWORD_SECTION_TITLE = "Смена пароля";

    public static final List<Map<String, String>> NEGATIVE_CHANGE_EMAIL_CASES = List.of(
            Map.of("title", "empty email",
                    "email", "",
                    "password", Config.LoginCredentials.PASSWORD,
                    "invalid_field", "email"
            ),

            Map.of("title", "empty password",
                    "email", Config.LoginCredentials.EMAIL,
                    "password", "",
                    "invalid_field", "email_password"
            ),

            Map.of("title", "invalid email format",
                    "email", "invalid_email_format",
                    "password", Config.LoginCredentials.PASSWORD,
                    "invalid_field", "email"
            ),

            Map.of("title", "email too short",
                    "email", DataFactory.generateShortEmail(),
                    "password", Config.LoginCredentials.PASSWORD,
                    "invalid_field", "email"
            ),

            Map.of("title", "email too long",
                    "email", DataFactory.generateLongEmail(),
                    "password", Config.LoginCredentials.PASSWORD,
                    "invalid_field", "email"
            ),

            Map.of("title", "wrong password",
                    "email", DataFactory.generateEmail(),
                    "password", DataFactory.generatePassword(),
                    "invalid_field", "email,email_password",
                    "validation_mode", "any"
            )
    );

    public static final List<Map<String, String>> NEGATIVE_CHANGE_PASSWORD_CASES = List.of(
            Map.of("title", "empty current password",
                    "current_password", "",
                    "new_password", DataFactory.generatePassword(),
                    "confirm_password", DataFactory.generatePassword(),
                    "invalid_field", "current_password"
            ),

            Map.of("title", "empty new password",
                    "current_password", Config.LoginCredentials.PASSWORD,
                    "new_password", "",
                    "confirm_password", DataFactory.generatePassword(),
                    "invalid_field", "new_password"
            ),

            Map.of("title", "empty confirm password",
                    "current_password", Config.LoginCredentials.PASSWORD,
                    "new_password", DataFactory.generatePassword(),
                    "confirm_password", "",
                    "invalid_field", "confirm_password"
            ),

            Map.of("title", "wrong current password",
                    "current_password", DataFactory.generatePassword(),
                    "new_password", DataFactory.generatePassword(),
                    "confirm_password", DataFactory.generatePassword(),
                    "invalid_field", "current_password,new_password,confirm_password",
                    "validation_mode", "any"
            ),

            mismatchPasswordCase(),

            shortPasswordCase(),

            longPasswordCase()
    );

    private static Map<String, String> mismatchPasswordCase() {
        String password = DataFactory.generatePassword();
        return Map.of(
                "title", "passwords mismatch",
                "current_password", Config.LoginCredentials.PASSWORD,
                "new_password", password,
                "confirm_password", password + "123",
                "invalid_field", "new_password,confirm_password"
        );
    }

    private static Map<String, String> shortPasswordCase() {
        String password = DataFactory.generateShortPassword();
        return Map.of(
                "title", "new password too short",
                "current_password", Config.LoginCredentials.PASSWORD,
                "new_password", password,
                "confirm_password", password,
                "invalid_field", "new_password,confirm_password"
        );
    }

    private static Map<String, String> longPasswordCase() {
        String password = DataFactory.generateLongPassword();
        return Map.of(
                "title", "new password too long",
                "current_password", Config.LoginCredentials.PASSWORD,
                "new_password", password,
                "confirm_password", password,
                "invalid_field", "new_password,confirm_password"
        );
    }
}
