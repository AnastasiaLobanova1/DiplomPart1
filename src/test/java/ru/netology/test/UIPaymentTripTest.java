package ru.netology.test;


import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.Form;
import ru.netology.page.Page;
import java.util.List;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class UIPaymentTripTest {
    private static DataHelper.CardInfo cardInfo;
    private static Page Page;
    private static Form Form;
    private static List<SQLHelper.PaymentEntity> payments;
    private static List<SQLHelper.CreditRequestEntity> credits;
    private static List<SQLHelper.OrderEntity> orders;

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
        Page = new Page();
        Form = Page.clickPayButton();
        Form = new Form();
    }

    @Story("Success payment")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void shouldSuccessPayment() {
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();

    }

    @Story("CardNumberOnly")
    @Test
    public void shouldSendFormCardNumberOnly() {
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormCardNumberOnly(cardInfo.getCardNumber());
        Form.errorMonthIsEmpty();
        Form.errorYearIsEmpty();
        Form.errorHolderIsEmpty();
        Form.errorCvcIsEmpty();

    }

    @Story("NotCardNumberOnly")
    @Test
    public void shouldSendFormNotCardNumberOnly() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCardNumberOnly(cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNumberIsEmpty();

    }

    @Story("NotHolderOnly")
    @Test
    public void shouldSendFormNotHolderOnly() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotHolderOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getCvc());
        Form.errorHolderIsEmpty();

    }

    @Story("NotMonthOnly")
    @Test
    public void shouldSendFormNotMonthOnly() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotMonthOnly(cardInfo.getCardNumber(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsEmpty();

    }

    @Story("NotYearOnly")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void shouldSendFormNotYearOnly() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotYearOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorYearIsEmpty();

    }

    @Story("NotCvcOnly")
    @Test
    public void shouldSendFormNotCvcOnly() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCvcOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder());
        Form.errorCvcIsEmpty();

    }

    @Story("Empty")
    @Test
    public void shouldNotSendForm() {

        $("fieldset > div + div + div + div > .button").click();
        Form.errorNumberIsEmpty();
        Form.errorMonthIsEmpty();
        Form.errorYearIsEmpty();
        Form.errorHolderIsEmpty();
        Form.errorCvcIsEmpty();

    }

    @Story("Not registered")
    @Test
    public void shouldSendFormNotRegistered() {

        var cardInfo = DataHelper.getDeclinedCard();
        Form.sendFormNotRegistered(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNotification();

    }

    @Story("15 digit card number")
    @Test
    public void shouldSend15DigitCardNumber() {

        $("fieldset > div > span > span > span > input.input__control").setValue(generateInvalidCardNumberInclude15Digit());
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCardNumberOnly(cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNumberIsInvalid();
    }

    @Story("17 digit card number")
    @Test
    public void shouldSend17DigitCardNumber() {

        $("fieldset > div > span > span > span > input.input__control").setValue(generateInvalidCardNumberInclude17Digit());
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCardNumberOnly(cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNotification();
    }

    @Story("Random symbols card number")
    @Test
    public void shouldSendRandomSymbolCardNumber() {

        $("fieldset > div > span > span > span > input.input__control").setValue(generateInvalidCardNumberIncludeSpecificSymbols());
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCardNumberOnly(cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNumberIsInvalid();
    }

    @Story("Nulls card number")
    @Test
    public void shouldSendNullsCardNumber() {

        $("fieldset > div > span > span > span > input.input__control").setValue("0000 0000 0000 0000");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCardNumberOnly(cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNotification();
    }

    @Story("Valid Holder Lower Case")
    @Test
    public void shouldSendValidHolderLowerCase() {

        $("fieldset > div + div + div > span > span .input__control").setValue(generateValidHolderLowerCase());
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotHolderOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Valid Holder Upper Case")
    @Test
    public void shouldSendValidHolderUpperCase() {

        $("fieldset > div + div + div > span > span .input__control").setValue(generateValidHolderUpperCase());
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotHolderOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Cyrillic")
    @Test
    public void shouldSendHolderCyrillic() {

        $("fieldset > div + div + div > span > span .input__control").setValue(generateCyrillicName());
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotHolderOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getCvc());
        Form.errorNotification();
    }

    @Story("Invalid month 13")
    @Test
    public void shouldSendMonth13() {

        $("fieldset > div + div > span > span .input__control").setValue("13");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotMonthOnly(cardInfo.getCardNumber(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsInvalid();
    }

    @Story("Valid month 12")
    @Test
    public void shouldSendMonth12() {

        $("fieldset > div + div > span > span .input__control").setValue("12");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotMonthOnly(cardInfo.getCardNumber(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Valid month 01")
    @Test
    public void shouldSendMonth01() {

        $("fieldset > div + div > span > span .input__control").setValue("01");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotMonthOnly(cardInfo.getCardNumber(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Valid month 00")
    @Test
    public void shouldSendMonth00() {

        $("fieldset > div + div > span > span .input__control").setValue("00");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotMonthOnly(cardInfo.getCardNumber(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsInvalid();
    }

    @Story("Invalid month 4")
    @Test
    public void shouldSendMonthM() {

        $("fieldset > div + div > span > span .input__control").setValue("4");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotMonthOnly(cardInfo.getCardNumber(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsInvalidFormat();
    }

    @Story("Invalid month 09/24")
    @Test
    public void shouldSendMonth09Year24() {

        $("fieldset > div + div > span > span .input__control").setValue("09");
        $("fieldset > div + div > span > span + span > span .input__control").setValue("24");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotYearAndMonth(cardInfo.getCardNumber(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsInvalid();
    }

    @Story("Invalid month 090")
    @Test
    public void shouldSendMonthMMM() {

        $("fieldset > div + div > span > span .input__control").setValue("012");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotMonthOnly(cardInfo.getCardNumber(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsInvalidFormat();
    }

    @Story("Invalid year G")
    @Test
    public void shouldSendYear2() {

        $("fieldset > div + div > span > span + span > span .input__control").setValue("2");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotYearOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorYearIsInvalidFormat();
    }

    @Story("Invalid year GGG")
    @Test
    public void shouldSendYear090() {

        $("fieldset > div + div > span > span + span > span .input__control").setValue("090");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotYearOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorYearIsInvalidFormat();
    }

    @Story("Invalid year 23")
    @Test
    public void shouldSendYear23() {

        $("fieldset > div + div > span > span + span > span .input__control").setValue("23");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotYearOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorYearIsInvalid();
    }

    @Story("Invalid year 30")
    @Test
    public void shouldSendYear30() {

        $("fieldset > div + div > span > span + span > span .input__control").setValue("30");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotYearOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorYearIsWrong();
    }

    @Story("Valid year 09/30")
    @Test
    public void shouldSendMonth10Year30() {

        $("fieldset > div + div > span > span .input__control").setValue("09");
        $("fieldset > div + div > span > span + span > span .input__control").setValue("30");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotYearAndMonth(cardInfo.getCardNumber(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Invalid cvc 1 digit")
    @Test
    public void shouldSendCvc1Digit() {

        $("fieldset > div + div + div > span > span + span > span .input__control").setValue("3");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCvcOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder());
        Form.errorCvcIsInvalid();
    }

    @Story("Invalid cvc 2 digit")
    @Test
    public void shouldSendCvc2Digit() {

        $("fieldset > div + div + div > span > span + span > span .input__control").setValue("30");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCvcOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder());
        Form.errorCvcIsInvalid();
    }

    @Story("Invalid cvc 4 digit")
    @Test
    public void shouldSendCvc4Digit() {

        $("fieldset > div + div + div > span > span + span > span .input__control").setValue("5432");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCvcOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder());
        Form.errorCvcIsInvalid();
    }

    @Story("Invalid cvc Nulls")
    @Test
    public void shouldSendCvcNulls() {

        $("fieldset > div + div + div > span > span + span > span .input__control").setValue("000");
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormNotCvcOnly(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder());
        Form.errorCvcIsInvalid();
    }
}
