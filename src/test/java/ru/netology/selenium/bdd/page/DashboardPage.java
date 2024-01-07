package ru.netology.selenium.bdd.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.selenium.bdd.data.DataGenerations;

import javax.activation.DataContentHandler;
import java.util.concurrent.TransferQueue;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement heading = $x("//h2[@data-test-id='dashboard']");
    private final ElementsCollection cards = $$(".list__item div");
    public DashboardPage(){
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataGenerations.CardInfo cardInfo) {
        var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(15))).getText();
        return extractBalance(text);
    }

    public UpBalanceCardPage selectCardToTransfer(DataGenerations.CardInfo cardInfo){
        cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId())).$("button").click();
        return new UpBalanceCardPage();

    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}



