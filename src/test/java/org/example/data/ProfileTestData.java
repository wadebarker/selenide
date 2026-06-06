package org.example.data;

import java.util.Map;

public class ProfileTestData {

    public static final String EXPECTED_USER_NAME = "Vadim Zviagintse";
    public static final String PERSONAL_INFO_TAB = "Личная информация";
    public static final String AUTHORIZATION_TAB = "Авторизация";

    public static final Map<String, String> VALID_PERSONAL_INFO = Map.of(
            "surname", "Zviagintse",
            "name", "Vadim",
            "patronymic", "Anatolievich",
            "dateOfBirth", "2000-02-29",
            "sex", "Мужской",
            "phone", "+7 (999) 123-45-67"
    );
}
