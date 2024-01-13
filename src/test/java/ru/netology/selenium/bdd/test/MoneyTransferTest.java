package ru.netology.selenium.bdd.test;

import com.codeborne.selenide.Selenide;

import org.junit.jupiter.api.Test;
import ru.netology.selenium.bdd.data.DataHelper;
import ru.netology.selenium.bdd.page.DashboardPage;
import ru.netology.selenium.bdd.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


    public class MoneyTransferTest {
        private int amountValid = 500;
        private int amountInvalid = 30000;

        private DashboardPage shouldOpenDashboardPage() {
            open("http://localhost:9999");
            Selenide.clearBrowserCookies();
            Selenide.clearBrowserLocalStorage();
            var loginPage = new LoginPage();
            var authInfo = DataHelper.getAuthInfo();
            var verificationPage = loginPage.validLogin(authInfo);
            var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
            return verificationPage.validVerify(verificationCode);
        }

        @Test
        void shouldTransferMoneyFromCard2toCard1() {
            DashboardPage dashboardPage = shouldOpenDashboardPage();
            dashboardPage.dashboardPageVisible();
            int expected1 = dashboardPage.getBalanceCard1() + amountValid;
            int expected2 = dashboardPage.getBalanceCard2() - amountValid;
            var moneyTransfer = dashboardPage.card1();
            moneyTransfer.moneyTransferVisible();
            moneyTransfer.setTransferAmount(amountValid);
            moneyTransfer.setFrom(DataHelper.getCardNumber2());
            moneyTransfer.doTransfer();
            assertEquals(expected1, dashboardPage.getBalanceCard1());
            assertEquals(expected2, dashboardPage.getBalanceCard2());
        }

        @Test
        void shouldTransferMoneyFromCard1toCard2() {
            Selenide.clearBrowserCookies();
            Selenide.clearBrowserLocalStorage();
            DashboardPage dashboardPage = shouldOpenDashboardPage();
            dashboardPage.dashboardPageVisible();
            int expected1 = dashboardPage.getBalanceCard2() + amountValid;
            int expected2 = dashboardPage.getBalanceCard1() - amountValid;
            var moneyTransfer = dashboardPage.card2();
            moneyTransfer.moneyTransferVisible();
            moneyTransfer.setTransferAmount(amountValid);
            moneyTransfer.setFrom(DataHelper.getCardNumber1());
            moneyTransfer.doTransfer();
            assertEquals(expected1, dashboardPage.getBalanceCard2());
            assertEquals(expected2, dashboardPage.getBalanceCard1());
        }

        @Test
        void shouldTransferInvalidAmountFromCard2toCard1() {
            DashboardPage dashboardPage = shouldOpenDashboardPage();
            dashboardPage.dashboardPageVisible();
            var moneyTransfer = dashboardPage.card1();
            moneyTransfer.moneyTransferVisible();
            moneyTransfer.setTransferAmount(amountInvalid);
            moneyTransfer.setFrom(DataHelper.getCardNumber2());
            moneyTransfer.doTransfer();
            moneyTransfer.errorTransfer();
        }
    }


