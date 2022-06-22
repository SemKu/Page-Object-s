package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransactionFromFirstToSecondCard() {
        val dashboardPage = new DashboardPage();
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.secondBill();
        val infoCard = DataHelper.getFirstCardInfo();
        String amountValue = "10000";
        transferPage.transfer(amountValue, infoCard);
        assertEquals(balanceFirstCard - Integer.parseInt(amountValue), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard + Integer.parseInt(amountValue), dashboardPage.getSecondCardBalance());
    }

    @Test
    void transactionFromSecondToFirstCard() {
        val dashboardPage = new DashboardPage();
        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.firstBill();
        val infoCard = DataHelper.getSecondCardInfo();
        String amountValue = "20001";
        transferPage.transfer(amountValue, infoCard);
        assertEquals(balanceFirstCard + Integer.parseInt(amountValue), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard - Integer.parseInt(amountValue), dashboardPage.getSecondCardBalance());
    }

}
