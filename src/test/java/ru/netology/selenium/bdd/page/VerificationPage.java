package ru.netology.selenium.bdd.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.selenium.bdd.data.DataGenerations;

import static com.codeborne.selenide.Selenide.$x;

public class VerificationPage {
    private SelenideElement codeField = $x("//span[@data-test-id='code'] //input[@class='input__control']");
    private SelenideElement veifyButton = $x("//button[@data-test-id='action-verify']");


    public VerificationPage(){
        codeField.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataGenerations.VerificationCode verificationCode){
        codeField.setValue(verificationCode.getCode());
        veifyButton.click();
        return new DashboardPage();
    }


}
