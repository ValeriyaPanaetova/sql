package ru.netology.mode;

import lombok.SneakyThrows;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DbUtils {
    @SneakyThrows
    public static String getVerificationCode() {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes);";
        String verificationCode = "";
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                PreparedStatement preparedStatement = conn.prepareStatement(codeSQL);
        ) {
            val code = runner.query(conn, codeSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        }
        return verificationCode;
    }
    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }


    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardsInfo {
        String cardNumber;
    }

    @SneakyThrows
    public static void cleanData() {
        var runner = new QueryRunner();
        var cleanCards = "DELETE FROM cards;";
        var cleanAuthCodes = "DELETE FROM auth_codes;";
        var cleanCardTransactions = "DELETE FROM card_transactions;";
        var cleanUsers = "DELETE FROM users";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                PreparedStatement preparedStatement = conn.prepareStatement(cleanCards);
                PreparedStatement preparedStatement1 = conn.prepareStatement(cleanAuthCodes);
                PreparedStatement preparedStatement2 = conn.prepareStatement(cleanCardTransactions);
                PreparedStatement preparedStatement3 = conn.prepareStatement(cleanUsers);
        ) {
            runner.update(conn, cleanCards);
            runner.update(conn, cleanAuthCodes);
            runner.update(conn, cleanCardTransactions);
            runner.update(conn, cleanUsers);
        }
    }
}