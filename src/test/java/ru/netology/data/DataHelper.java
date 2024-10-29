package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));

    @Value
    public static class CardInfo {
        private final String cardNumber;
        private final String month;
        private final String year;
        private final String holder;
        private final String cvc;
    }
    public static CardInfo getApprovedCard() {
        return new CardInfo ("4444 4444 4444 4441" , generateMonth(1), generateYear(1),generateValidHolder(), generateValidCVC());
    }
    public static CardInfo getDeclinedCard() {
        return new CardInfo ("4444 4444 4444 4442", generateMonth(1), generateYear(1),generateValidHolder(), generateValidCVC());
    }
    public static String generateMonth(int addMonth) {
        return LocalDate.now().plusMonths(addMonth).format(DateTimeFormatter.ofPattern("MM"));
    }
    public static String generateYear(int addYear) {
        return LocalDate.now().plusYears(addYear).format(DateTimeFormatter.ofPattern("yy"));
    }
    public static String generateValidHolder() {
        return faker.name().fullName();
    }
    public static String generateValidHolderUpperCase() {
        return faker.name().fullName().toUpperCase();
    }
    public static String generateValidHolderLowerCase() {
        return faker.name().fullName().toLowerCase();
    }
    private static final List<String> cyrillicName = Arrays.asList("Иван Иванов", "Федор Федоров", "Кирилл Петров", "Егор Лосев", "Михаил Жидин", "Гена Букин");
    public static String generateCyrillicName() {
        return cyrillicName.get(faker.random().nextInt(cyrillicName.size()));
    }
    public static String generateValidCVC() {
        return faker.numerify("###");
    }
    public static String generateInvalidCardNumberInclude15Digit() {
        return faker.numerify("#### #### #### ###");
    }
    public static String generateInvalidCardNumberInclude17Digit() {
        return faker.numerify("#### #### #### #### #");
    }
    public static String generateInvalidCardNumberIncludeSpecificSymbols() {
        return faker.letterify("???? ???? ???? ????");
    }
}
