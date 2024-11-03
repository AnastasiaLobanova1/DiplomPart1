package ru.topAcademy.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));
    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private final String cardNumber;
        private final String month;
        private final String year;
        private final String holder;
        private final String cvc;
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(getNumberByStatus("APPROVED"), generateMonth(1), generateYear(1), generateValidHolder(), generateValidCVC());
    }
    public static CardInfo getApprovedCardSixYears() {
        return new CardInfo(getNumberByStatus("approved"), generateMonth(1), generateYear(6), generateValidHolder(), generateValidCVC());
    }
    public static CardInfo getApprovedCardFiveYears() {
        return new CardInfo(getNumberByStatus("approved"), generateMonth(1), generateYear(5), generateValidHolder(), generateValidCVC());
    }
    public static CardInfo getApprovedCardMonthMinusOneSixYears() {
        return new CardInfo(getNumberByStatus("approved"), generateMonth(-1), generateYear(6), generateValidHolder(), generateValidCVC());
    }
    public static DataHelper.CardInfo getDeclinedCard() {
        return new DataHelper.CardInfo(getNumberByStatus("declined"), generateMonth(1), generateYear(1), generateValidHolder(), generateValidCVC());
    }

   public static String getNumberByStatus(String status) {
        if (status.equalsIgnoreCase("APPROVED")) {
            return "4444 4444 4444 4441";
        } else if (status.equalsIgnoreCase("DECLINED")) {
            return "4444 4444 4444 4442";
        }
        return null;
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

    private static final List<String> InvalidCardNumberIncludeSpecificSymbols = Arrays.asList("ASqw !@#$ фывук ОДЖ:", "+_)( *&^% :></ SDju");

    public static String generateRandomSymbols() {
        return InvalidCardNumberIncludeSpecificSymbols.get(faker.random().nextInt(InvalidCardNumberIncludeSpecificSymbols.size()));
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

    public static String generateInvalidCvcInclude1Digit() {
        return faker.numerify("#");
    }
    public static String generateInvalidCvcInclude2Digit() {
        return faker.numerify("##");
    }
    public static String generateInvalidCvcInclude4Digit() {
        return faker.numerify("####");


    }
    public static String generateNullsNumberCard() {
        return ("0000 0000 0000 0000");


    }



    }
