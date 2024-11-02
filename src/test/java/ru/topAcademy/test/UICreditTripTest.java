package ru.topAcademy.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.topAcademy.data.DataHelper;
import ru.topAcademy.data.SQLHelper;
import ru.topAcademy.page.Form;
import ru.topAcademy.page.Page;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;
import static ru.topAcademy.data.DataHelper.*;
import static ru.topAcademy.data.DataHelper.generateCyrillicName;
import static ru.topAcademy.data.SQLHelper.cleanDatabase;
import static ru.topAcademy.data.SQLHelper.cleanDatabase;

public class UICreditTripTest {
    private static DataHelper.CardInfo cardInfo;
    private static ru.topAcademy.page.Page Page;
    private static ru.topAcademy.page.Form Form;
    private static List<SQLHelper.PaymentEntity> payments;
    private static List<SQLHelper.Credit_Request_Entity> credits;
    private static List<SQLHelper.OrderEntity> orders;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

//    @AfterAll
//    static void teardown() {
//        cleanDatabase();
//    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
        Page = new Page();
        Form = Page.clickCreditButton();
        Form = new Form();
    }

    @Story("Success payment")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void shouldSuccessPayment() {
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
        assertEquals("APPROVED", SQLHelper.getCreditsRequest());

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

        Form.buttonContinue();
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
        assertEquals("DECLINED", SQLHelper.getCreditsRequest());

    }

    @Story("15 digit card number")
    @Test
    public void shouldSend15DigitCardNumber() {

        var number = DataHelper.generateInvalidCardNumberInclude15Digit();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(number, cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNumberIsInvalid();
    }

    @Story("17 digit card number")
    @Test
    public void shouldSend17DigitCardNumber() {

        var number = DataHelper.generateInvalidCardNumberInclude17Digit();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(number, cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNotification();
    }

    @Story("Random symbols card number")
    @Test
    public void shouldSendRandomSymbolCardNumber() {

        var number = DataHelper.generateRandomSymbols();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(number, cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNumberIsInvalid();
    }

    @Story("Nulls card number")
    @Test
    public void shouldSendNullsCardNumber() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid("0000 0000 0000 0000", cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNotification();
    }

    @Story("Valid Holder Lower Case")
    @Test
    public void shouldSendValidHolderLowerCase() {

        var holder = DataHelper.generateValidHolderLowerCase();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), holder, cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Valid Holder Upper Case")
    @Test
    public void shouldSendValidHolderUpperCase() {

        var holder = DataHelper.generateValidHolderUpperCase();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), holder, cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Cyrillic")
    @Test
    public void shouldSendHolderCyrillic() {

        var holder = DataHelper.generateCyrillicName();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), holder, cardInfo.getCvc());
        Form.errorNotification();
    }

    @Story("Digit")
    @Test
    public void shouldSendHolderDigit() {

        var holder = DataHelper.generateInvalidCardNumberInclude17Digit();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), holder, cardInfo.getCvc());
        Form.errorNotification();
    }

    @Story("RandomSymbol")
    @Test
    public void shouldSendRandomSymbol() {

        var holder = DataHelper.generateRandomSymbols();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), holder, cardInfo.getCvc());
        Form.errorNotification();
    }


    @Story("Invalid month 13")
    @Test
    public void shouldSendMonth13() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), "13", cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsInvalid();
    }

    @Story("Valid month 12")
    @Test
    public void shouldSendMonth12() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), "12", cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Valid month 01")
    @Test
    public void shouldSendMonth01() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), "01", cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Valid month 00")
    @Test
    public void shouldSendMonth00() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), "00", cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorNotification();
    }

    @Story("Invalid month 4")
    @Test
    public void shouldSendMonthM() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), "4", cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsInvalidFormat();
    }

    @Story("Invalid month 09/24")
    @Test
    public void shouldSendMonth09Year24() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), "09", "24", cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorMonthIsInvalid();
    }

    @Story("Invalid month 012")
    @Test
    public void shouldSendMonthMMM() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), "012", cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Invalid year G")
    @Test
    public void shouldSendYear2() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), "2", cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorYearIsInvalidFormat();
    }

    @Story("Invalid year GGG")
    @Test
    public void shouldSendYear255() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), "255", cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Invalid year 23")
    @Test
    public void shouldSendYear23() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), "23", cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorYearIsInvalid();
    }

    @Story("Invalid year +6")
    @Test
    public void shouldSendYearPlusSix() {

        var cardInfo = DataHelper.getApprovedCardSixYears();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.errorYearIsWrong();
    }

    @Story("Valid year +5")
    @Test
    public void shouldSendYearPlusFive() {

        var cardInfo = DataHelper.getApprovedCardFiveYears();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Valid month -1 year +6")
    @Test
    public void shouldSendMonthMinusOneYearPlusSix() {

        var cardInfo = DataHelper.getApprovedCardMonthMinusOneSixYears();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), cardInfo.getCvc());
        Form.successNotification();
    }

    @Story("Invalid cvc 1 digit")
    @Test
    public void shouldSendCvc1Digit() {

        var Cvc = DataHelper.generateInvalidCvcInclude1Digit();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), Cvc);
        Form.errorCvcIsInvalid();
    }

    @Story("Invalid cvc 2 digit")
    @Test
    public void shouldSendCvc2Digit() {

        var Cvc = DataHelper.generateInvalidCvcInclude2Digit();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), Cvc);
        Form.errorCvcIsInvalid();
    }

    @Story("Invalid cvc 4 digit")
    @Test
    public void shouldSendCvc4Digit() {

        var Cvc = DataHelper.generateInvalidCvcInclude4Digit();
        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), Cvc);
        Form.successNotification();
    }

    @Story("Invalid cvc Nulls")
    @Test
    public void shouldSendCvcNulls() {

        var cardInfo = DataHelper.getApprovedCard();
        Form.sendFormValid(cardInfo.getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getHolder(), "000");
        Form.errorCvcIsInvalid();
    }
}


