package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;


public class Page {
    private final SelenideElement header = $("[id=root] > .App_appContainer__3jRx1 > h2");
    private final SelenideElement image = $(".Order_cardPreview__47B2k");
    private final SelenideElement buttonPay = $("[class='button button_size_m button_theme_alfa-on-white']");
    private final SelenideElement buttonCredit = $(".button+.button");
    private final SelenideElement headingCard = $("[id=root] > .App_appContainer__3jRx1 > h3");
    private final SelenideElement form = $(".form");
    private final SelenideElement successNotification = $("[class='notification notification_status_ok notification_has-closer notification_stick-to_right notification_theme_alfa-on-white']");
    private final SelenideElement errorNotification = $("[class='notification notification_status_error notification_has-closer notification_stick-to_right notification_theme_alfa-on-white']");


    public Page() {
        header.shouldBe(visible);
        image.shouldBe(visible);
        buttonPay.shouldBe(visible);
        buttonCredit.shouldBe(visible);
        headingCard.shouldBe(hidden);
        form.shouldBe(hidden);
        successNotification.shouldBe(hidden);
        errorNotification.shouldBe(hidden);


    }

    public Form clickCreditButton() {
        buttonCredit.click();
        headingCard.shouldBe(visible, text("Кредит по данным карты"));
        return new Form();
    }
    public Form clickPayButton() {
        buttonPay.click();
        headingCard.shouldBe(visible, text("Оплата по карте"));
        return new Form();
    }


}