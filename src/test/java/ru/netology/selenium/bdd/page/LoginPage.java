package ru.netology.selenium.bdd.page;

import ru.netology.selenium.bdd.data.DataGenerations;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {

    public VerificationPage validLogin(DataGenerations.AuthInfo info){
        $x("//span[@data-test-id='login'] //input[@class='input__control']").setValue(info.getLogin());
        $x("//span[@data-test-id='password'] //input[@class='input__control']").setValue(info.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        return new  VerificationPage();
    }


}
