package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement billFrom = $("[data-test-id=from] input");
    private SelenideElement buttonTransfer = $("[data-test-id=action-transfer] .button__content");

    public TransferPage() {
        amount.should(visible);
    }

    public void transfer(String amountValue, DataHelper.CardInfo cardInfo) {
        amount.setValue(amountValue);
        billFrom.setValue(String.valueOf(cardInfo));
        buttonTransfer.click();
        new DashboardPage();
    }
}

