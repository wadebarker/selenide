package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFactory {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    public static String today() {
        return LocalDate.now().format(ISO_DATE);
    }

    public static String daysFromToday(int days) {
        return LocalDate.now().plusDays(days).format(ISO_DATE);
    }

    public static String yesterday() {
        return daysFromToday(-1);
    }

    public static String maxAllowedDate() {
        return daysFromToday(7);
    }

    public static String beyondMaxAllowedDate() {
        return daysFromToday(8);
    }

    public static String dayAfter(String isoDate) {
        return LocalDate.parse(isoDate).plusDays(1).format(ISO_DATE);
    }

    public static String dayBefore(String isoDate) {
        return LocalDate.parse(isoDate).minusDays(1).format(ISO_DATE);
    }

    public static long daysBetween(String from, String to) {
        return java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.parse(from),
                LocalDate.parse(to)
        );
    }
}
