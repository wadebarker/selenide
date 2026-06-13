package utils;

import net.datafaker.Faker;

public class DataFactory {

    private static final Faker faker = new Faker();

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generatePassword() {
        return faker.internet().password(8, 20, true, true, true);
    }

    public static String generateShortEmail() {
        return generateEmail().substring(0, 5);
    }

    public static String generateLongEmail() {
        return generateEmail() + "x".repeat(50);
    }

    public static String generateShortPassword() {
        return faker.internet().password(1, 5);
    }

    public static String generateLongPassword() {
        return generatePassword() + "x".repeat(50);
    }

    public static String generateText(int length) {
        if (length <= 0) {
            return "";
        }
        return faker.lorem().characters(length);
    }

    public static String generateTitle() {
        return generateText(10);
    }

    public static String generateDescription() {
        return generateText(50);
    }

    public static String generateLongTitle() {
        return generateText(51);
    }

    public static String generateLongDescription() {
        return generateText(201);
    }
}