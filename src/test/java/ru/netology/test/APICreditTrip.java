package ru.netology.test.Api;

import com.google.gson.Gson;
import io.qameta.allure.Story;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class APICreditTrip {
    private static DataHelper.CardInfo cardInfo;
    private static final Gson gson = new Gson();
    private static final RequestSpecification spec = new RequestSpecBuilder().setBaseUri("http://localhost").setPort(9999)
            .setAccept(ContentType.JSON).setContentType(ContentType.JSON).log(LogDetail.ALL).build();
    private static final String creditUrl = "/credit";
    private static List<SQLHelper.PaymentEntity> payments;
    private static List<SQLHelper.CreditRequestEntity> credits;
    private static List<SQLHelper.OrderEntity> orders;


    @Story("HappyPath")
    @Test
    public void shouldHappyPath() {
        cardInfo = DataHelper.getApprovedCard();
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(creditUrl)
                .then().statusCode(200);

        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditsRequest();
        orders = SQLHelper.getOrders();
        assertEquals(0, payments.size());
        assertEquals(1, credits.size());
        assertEquals(1, orders.size());

        assertTrue(credits.get(0).getStatus().equalsIgnoreCase("approved"));
        assertEquals(credits.get(0).getBank_id(), orders.get(0).getPayment_id());
        assertEquals(credits.get(0).getId(), orders.get(0).getCredit_id());
    }

    @Story("SadPath")
    @Test
    public void shouldSadPath() {
        cardInfo = DataHelper.getDeclinedCard();
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(creditUrl)
                .then().statusCode(200);

        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditsRequest();
        orders = SQLHelper.getOrders();
        assertEquals(0, payments.size());
        assertEquals(1, credits.size());
        assertEquals(1, orders.size());

        assertTrue(credits.get(0).getStatus().equalsIgnoreCase("declined"));
        assertEquals(credits.get(0).getBank_id(), orders.get(0).getPayment_id());
        assertEquals(credits.get(0).getId(), orders.get(0).getCredit_id());
    }

    @Story("Пустое body запроса")
    @Test
    public void shouldStatus400WithEmptyBody() {
        cardInfo = DataHelper.getApprovedCard();
        given().spec(spec)
                .when().post(creditUrl)
                .then().statusCode(400);

        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditsRequest();
        orders = SQLHelper.getOrders();
        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Story("Пустое значение у атрибута number в body запроса")
    @Test
    public void shouldStatus400WithEmptyNumber() {
        cardInfo = new DataHelper.CardInfo(null, DataHelper.generateMonth(1), DataHelper.generateYear(2),
                DataHelper.generateValidHolder(), DataHelper.generateValidCVC());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(creditUrl)
                .then().statusCode(400);

        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditsRequest();
        orders = SQLHelper.getOrders();
        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Story("Пустое значение у атрибута month в body запроса")
    @Test
    public void shouldStatus400WithEmptyMonth() {
        cardInfo = new DataHelper.CardInfo(DataHelper.getNumberByStatus("approved"), null, DataHelper.generateYear(2),
                DataHelper.generateValidHolder(), DataHelper.generateValidCVC());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(creditUrl)
                .then().statusCode(400);

        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditsRequest();
        orders = SQLHelper.getOrders();
        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Story("Пустое значение у атрибута year в body запроса")
    @Test
    public void shouldStatus400WithEmptyYear() {
        cardInfo = new DataHelper.CardInfo(DataHelper.getNumberByStatus("approved"), DataHelper.generateMonth(1), null,
                DataHelper.generateValidHolder(), DataHelper.generateValidCVC());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(creditUrl)
                .then().statusCode(400);

        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditsRequest();
        orders = SQLHelper.getOrders();
        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Story("Пустое значение у атрибута holder в body запроса")
    @Test
    public void shouldStatus400WithEmptyHolder() {
        cardInfo = new DataHelper.CardInfo(DataHelper.getNumberByStatus("approved"), DataHelper.generateMonth(1),
                DataHelper.generateYear(2), null, DataHelper.generateValidCVC());
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(creditUrl)
                .then().statusCode(400);

        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditsRequest();
        orders = SQLHelper.getOrders();
        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Story("Пустое значение у атрибута cvc в body запроса")
    @Test
    public void shouldStatus400WithEmptyCvc() {
        cardInfo = new DataHelper.CardInfo(DataHelper.getNumberByStatus("approved"), DataHelper.generateMonth(1),
                DataHelper.generateYear(2), DataHelper.generateValidHolder(), null);
        var body = gson.toJson(cardInfo);
        given().spec(spec).body(body)
                .when().post(creditUrl)
                .then().statusCode(400);

        payments = SQLHelper.getPayments();
        credits = SQLHelper.getCreditsRequest();
        orders = SQLHelper.getOrders();
        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }
}

