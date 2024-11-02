package ru.topAcademy.data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static java.lang.System.getProperty;
import static java.sql.DriverManager.getConnection;
import static org.openqa.selenium.remote.http.FormEncodedData.getData;


public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");

    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentEntity {
        private String id;
        private int amount;
        private Timestamp created;
        private String status;
        private String transaction_id;
    }

    @SneakyThrows
    public static List<PaymentEntity> getPayments() {
        var conn = getConn();
        var sqlQuery = "SELECT * FROM payment_entity ORDER BY created DESC;";
        ResultSetHandler<List<PaymentEntity>> resultHandler = new BeanListHandler<>(PaymentEntity.class);
        return runner.query(conn, sqlQuery, resultHandler);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Credit_Request_Entity {
        private String id;
        private String bank_id;
        private Timestamp created;
        private String status;
    }

    @SneakyThrows
    public static String getCreditsRequest() {
        var statusSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConn();
        return (runner.query(conn, statusSql, new ScalarHandler<>()));
    }
//        var sqlQuery = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
//        ResultSetHandler<List<Credit_Request_Entity>> resultHandler = new BeanListHandler<>(Credit_Request_Entity.class);
//        var conn = getConn();
//        return runner.query(conn, sqlQuery, resultHandler);
//            }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderEntity {
        private String id;
        private Timestamp created;
        private String credit_id;
        private String payment_id;
    }

    @SneakyThrows
    public static List<OrderEntity> getOrders() {
        var conn = getConn();
        var sqlQuery = "SELECT * FROM order_entity ORDER BY created DESC;";
        ResultSetHandler<List<OrderEntity>> resultHandler = new BeanListHandler<>(OrderEntity.class);
        return runner.query(conn, sqlQuery, resultHandler);
    }
}
