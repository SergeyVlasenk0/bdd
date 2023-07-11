package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement actionTransfer = $("[data-test-id=action-transfer]");

    public DashboardPage deposit(int amount, String cards) {
        setAmount(amount);
        setCards(cards);
        actionTransfer.click();
        return new DashboardPage();
    }

    public void setCards(String cards) {
        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        fromField.sendKeys(Keys.BACK_SPACE);
        fromField.setValue(cards);
    }

    public void setAmount(int amount) {
        amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        amountField.sendKeys(Keys.BACK_SPACE);
        amountField.setValue(Integer.toString(amount));
    }
}