package org.example.data;

import org.example.config.Config;
import utils.DataFactory;

import java.util.List;
import java.util.Map;

public class LoginTestData {

    public static final List<Map<String, String>> NEGATIVE_LOGIN_CASES = List.of(
            Map.of("title", "valid email wrong password",
                    "email", Config.LoginCredentials.EMAIL,
                    "password", DataFactory.generatePassword(),
                    "error", "Неверная почта или пароль"
            ),

            Map.of("title", "non existing email",
                    "email", DataFactory.generateEmail(),
                    "password", Config.LoginCredentials.PASSWORD,
                    "error", "Неверная почта или пароль"
            ),

            Map.of("title", "invalid email format",
                    "email", "invalid_email_format",
                    "password", Config.LoginCredentials.PASSWORD,
                    "error", "Введите правильную почту"
            ),

            Map.of("title", "email too short",
                    "email", DataFactory.generateShortEmail(),
                    "password", Config.LoginCredentials.PASSWORD,
                    "error", "Введите правильную почту"
            ),

            Map.of("title", "email too long",
                    "email", DataFactory.generateLongEmail(),
                    "password", Config.LoginCredentials.PASSWORD,
                    "error", "Максимум 50 символов"
            ),

            Map.of("title", "empty email",
                    "email", "",
                    "password", Config.LoginCredentials.PASSWORD,
                    "error", "Почта - обязательное поле"
            ),

            Map.of("title", "empty password",
                    "email", Config.LoginCredentials.EMAIL,
                    "password", "",
                    "error", "Пароль - обязательное поле"
            ),

            Map.of("title", "password too short",
                    "email", Config.LoginCredentials.EMAIL,
                    "password", DataFactory.generateShortPassword(),
                    "error", "Минимум 6 символов"
            ),

            Map.of("title", "password too long",
                    "email", Config.LoginCredentials.EMAIL,
                    "password", DataFactory.generateLongPassword(),
                    "error", "Максимум 50 символов"
            )
    );
}