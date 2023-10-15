package ru.netology;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.mode.DataHelper;
import ru.netology.mode.DbUtils;
import ru.netology.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.mode.DbUtils.cleanDatabase;

public class DbInteraction {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    void shouldSuccessFullLogin() throws SQLException {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisiblity();
        var verificationCode = DbUtils.getVerifacationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }
}