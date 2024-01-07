package ru.netology.selenium.bdd.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.selenium.bdd.data.DataGenerations;
import ru.netology.selenium.bdd.page.DashboardPage;
import ru.netology.selenium.bdd.page.LoginPage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.selenium.bdd.data.DataGenerations.*;


class TransferFromCard {

    DashboardPage dashboardPage;
    CardInfo firstCardInfo;
    CardInfo secondeCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataGenerations.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataGenerations.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondeCardInfo = getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondeCardInfo);
    }

    @Test
    void testuser() {
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceOneCard = firstCardBalance - amount;
        var expectedBalanceOneTwo = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondeCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceOneCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceTwoCard = dashboardPage.getCardBalance(secondeCardInfo);
        assertAll(() -> assertEquals(expectedBalanceOneCard, actualBalanceOneCard),
                () -> assertEquals(expectedBalanceOneTwo, actualBalanceTwoCard));


    }

    @Test
    void testuser1() {
        var amount = generateValidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), secondeCardInfo);
        transferPage.findErrorMessage("Выполнена попытка перевода суммы, превышающей остаток на карте списания");
        assertAll(() -> assertEquals(firstCardBalance, dashboardPage.getCardBalance(firstCardInfo)),
                () -> assertEquals(secondCardBalance, dashboardPage.getCardBalance(secondeCardInfo)));

    }
}

