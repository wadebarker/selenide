package org.example.data;

import utils.DataFactory;

import java.util.List;
import java.util.Map;

public class RegisterTestData {

    public static final List<Map<String, String>> NEGATIVE_REGISTER_CASES = List.of(
            Map.of("title", "Поле ввода почты не заполнено",
                    "email", "",
                    "password", DataFactory.generatePassword(),
                    "confirm_password", DataFactory.generatePassword(),
                    "error_message", "Почта - обязательное поле"
            ),

            Map.of("title", "Поле ввода пароля не заполнено",
                    "email", DataFactory.generateEmail(),
                    "password", "",
                    "confirm_password", DataFactory.generatePassword(),
                    "error_message", "Пароль - обязательное поле"
            ),

            Map.of("title", "Поле ввода подтверждения пароля не заполнено",
                    "email", DataFactory.generateEmail(),
                    "password", DataFactory.generatePassword(),
                    "confirm_password", "",
                    "error_message", "Повторите пароль"
            ),

            Map.of("title", "Невалидный email", "email",
                    "invalid_email",
                    "password", DataFactory.generatePassword(),
                    "confirm_password", DataFactory.generatePassword(),
                    "error_message", "Введите правильную почту"
            ),

            Map.of("title", "Email слишком короткий",
                    "email", DataFactory.generateShortEmail(),
                    "password", DataFactory.generatePassword(),
                    "confirm_password", DataFactory.generatePassword(),
                    "error_message", "Введите правильную почту"
            ),

            Map.of("title", "Email слишком длинный",
                    "email", DataFactory.generateLongEmail(),
                    "password", DataFactory.generatePassword(),
                    "confirm_password", DataFactory.generatePassword(),
                    "error_message", "Максимум 50 символов"
            ),

            Map.of("title", "Пароль слишком короткий",
                    "email", DataFactory.generateEmail(),
                    "password", DataFactory.generateShortPassword(),
                    "confirm_password", DataFactory.generatePassword(),
                    "error_message", "Минимум 6 символов"
            ),

            Map.of("title", "Пароль слишком длинный",
                    "email", DataFactory.generateEmail(),
                    "password", DataFactory.generateLongPassword(),
                    "confirm_password", DataFactory.generatePassword(),
                    "error_message", "Максимум 50 символов"
            ),

            Map.of("title", "Пароль и подтверждение не совпадают",
                    "email", DataFactory.generateEmail(),
                    "password", DataFactory.generatePassword(),
                    "confirm_password", DataFactory.generatePassword() + "123",
                    "error_message", "Пароли не совпадают"
            )
    );
}