package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public void verifyDashboardPage() {
        heading.shouldBe(visible);
    }

    public int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public int getCard1Balance() {
        val text = cards.first().text();
        int balance = extractBalance(text);
        return balance;
    }

    public int getCard2Balance() {
        val text = cards.get(1).text();
        int balance = extractBalance(text);
        return balance;
    }

    public TransferPage topUpCard1() {
        $$("[data-test-id=action-deposit]").get(0).click();
        return new TransferPage();
    }

    public TransferPage topUpCard2() {
        $$("[data-test-id=action-deposit]").get(1).click();
        return new TransferPage();
    }
}