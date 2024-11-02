package ru.topAcademy.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class Form {
    private final SelenideElement header = $("[id=root] > .App_appContainer__3jRx1 > h2");
    private final SelenideElement image = $(".Order_cardPreview__47B2k");
    private final SelenideElement buttonPay = $(".button");
    private final SelenideElement buttonCredit = $(".button+.button");
    private final SelenideElement headingCard = $("[id=root] > .App_appContainer__3jRx1 > h3");
    private final SelenideElement form = $(".form");
    private final SelenideElement inputNumber = $("fieldset > div > span > span > span > input.input__control");
    private final SelenideElement inputMonth = $("fieldset > div + div > span > span .input__control");
    private final SelenideElement inputYear = $("fieldset > div + div > span > span + span > span .input__control");
    private final SelenideElement inputHolder = $("fieldset > div + div + div > span > span .input__control");
    private final SelenideElement inputCvc = $("fieldset > div + div + div > span > span + span > span .input__control");
    private final SelenideElement buttonContinue = $("fieldset > div + div + div + div > .button");

    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");

    private final SelenideElement errorMessageNumber = $("fieldset > div > span > span .input__sub");
    private final SelenideElement errorMessageMonth = $("fieldset > div + div > span > span .input__sub");
    private final SelenideElement errorMessageYear = $("fieldset > div + div > span > span + span > span > span .input__sub");
    private final SelenideElement errorMessageHolder = $("fieldset > div + div + div > span > span .input__sub");
    private final SelenideElement errorMessageCvc = $("fieldset > div + div + div > span > span + span > span .input__sub");

    public Form() {
        header.shouldBe(visible);
        buttonPay.shouldBe(visible);
        buttonCredit.shouldBe(visible);

        form.shouldBe(visible);
        successNotification.should(Condition.hidden);
        errorNotification.should(Condition.hidden);
    }

    public void sendFormValid(String cardNumber, String month, String year, String holder, String cvc) {
        inputNumber.setValue(cardNumber);
        inputMonth.setValue(month);
        inputYear.setValue(year);
        inputHolder.setValue(holder);
        inputCvc.setValue(cvc);
        buttonContinue.click();
    }

    public void successNotification() {
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
        successNotification.should(Condition.cssClass("notification_visible"));
        successNotification.should(text("Успешно"));
        successNotification.should(text("Операция одобрена Банком."));

    }

    public void errorNotification() {
        errorNotification.shouldBe(visible, Duration.ofSeconds(15));
        errorNotification.shouldBe(text("Ошибка"));
        errorNotification.shouldBe(text("Ошибка! Банк отказал в проведении операции."));
    }

    public void sendFormCardNumberOnly(String cardNumber) {
        inputNumber.setValue(cardNumber);
        buttonContinue.click();
    }
    public void sendFormNotCardNumberOnly(String month, String year, String holder, String cvc) {
        inputMonth.setValue(month);
        inputYear.setValue(year);
        inputHolder.setValue(holder);
        inputCvc.setValue(cvc);
        buttonContinue.click();
    }
    public void sendFormNotHolderOnly(String cardNumber,String month, String year, String cvc) {
        inputNumber.setValue(cardNumber);
        inputMonth.setValue(month);
        inputYear.setValue(year);
        inputCvc.setValue(cvc);
        buttonContinue.click();
    }
    public void sendFormNotMonthOnly(String cardNumber, String year, String holder, String cvc) {
        inputNumber.setValue(cardNumber);
        inputYear.setValue(year);
        inputHolder.setValue(holder);
        inputCvc.setValue(cvc);
        buttonContinue.click();
    }
    public void sendFormNotYearOnly(String cardNumber, String month, String holder, String cvc) {
        inputNumber.setValue(cardNumber);
        inputMonth.setValue(month);
        inputHolder.setValue(holder);
        inputCvc.setValue(cvc);
        buttonContinue.click();
    }
    public void sendFormNotCvcOnly(String cardNumber, String month, String year, String holder) {
        inputNumber.setValue(cardNumber);
        inputMonth.setValue(month);
        inputYear.setValue(year);
        inputHolder.setValue(holder);
        buttonContinue.click();
    }
    public void sendFormNotRegistered(String cardNumber, String month, String year, String holder, String cvc) {
        inputNumber.setValue(cardNumber);
        inputMonth.setValue(month);
        inputYear.setValue(year);
        inputHolder.setValue(holder);
        inputCvc.setValue(cvc);
        buttonContinue.click();
    }

    public void sendFormNotYearAndMonth(String cardNumber, String holder, String cvc) {
        inputNumber.setValue(cardNumber);
        inputHolder.setValue(holder);
        inputCvc.setValue(cvc);
        buttonContinue.click();
    }

    public void buttonContinue(){
        buttonContinue.click();
    }


    public void errorNumberIsEmpty() {
        errorMessageNumber.shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(30));

    }

    public void errorNumberIsInvalid() {
        errorMessageNumber.shouldHave(exactText("Неверный формат"), Duration.ofSeconds(30));
    }

    public void errorMonthIsEmpty() {
        errorMessageMonth.shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(30));

    }

    public void errorMonthIsInvalid() {
        errorMessageMonth.shouldHave(exactText("Неверно указан срок действия карты"), Duration.ofSeconds(30));
    }
    public void errorMonthIsInvalidFormat() {
        errorMessageMonth.shouldHave(exactText("Неверный формат"), Duration.ofSeconds(30));
    }

    public void errorYearIsEmpty() {
        errorMessageYear.shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(30));

    }
    public void errorYearIsInvalidFormat() {
        errorMessageYear.shouldHave(exactText("Неверный формат"), Duration.ofSeconds(30));
    }

    public void errorYearIsInvalid() {
        errorMessageYear.shouldHave(exactText("Истёк срок действия карты"), Duration.ofSeconds(30));
    }
    public void errorYearIsWrong() {
        errorMessageYear.shouldHave(exactText("Неверно указан срок действия карты"), Duration.ofSeconds(30));
    }


    public void errorHolderIsEmpty() {
        errorMessageHolder.shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(30));

    }

    public void errorHolderIsInvalid() {
        errorMessageHolder.shouldHave(exactText("Неверный формат"), Duration.ofSeconds(30));
    }
        public void errorCvcIsEmpty() {
            errorMessageCvc.shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(30));

        }

        public void errorCvcIsInvalid() {
            errorMessageCvc.shouldHave(exactText("Неверный формат"), Duration.ofSeconds(30));
        }

    }
