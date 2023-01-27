package ru.netology.pageobject.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.pageobject.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final String locatorOne = "[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] button";
    private final String locatorTwo = "[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] button";
    private SelenideElement firstBalanceButton = $(locatorOne);
    private SelenideElement secondBalanceButton = $(locatorTwo);
    private final String idOne = locatorOne.substring(locatorOne.indexOf('\'') + 1, locatorOne.lastIndexOf('\''));
    private final String idTwo = locatorTwo.substring(locatorTwo.indexOf('\'') + 1, locatorTwo.lastIndexOf('\''));

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public RefillPage getCardBalanceButton() {
        DataHelper.CardValueSum = getCardBalance(idOne) + getCardBalance(idTwo);
        if (getCardBalance(idOne) >= getCardBalance(idTwo)) {
            secondBalanceButton.click();
        } else {
            firstBalanceButton.click();
        }
        return new RefillPage();
    }

    public int getCardBalance(String id) {
        val text = cards.find(Condition.attribute("data-test-id", id)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
    public int mustBeBalanceOne (){
        val balanceOne = (DataHelper.CardValueSum + (getCardBalance(idOne) - getCardBalance(idTwo)))/2;
        return balanceOne;
    }
    public int mustBeBalanceTwo (){
        val balanceTwo = (DataHelper.CardValueSum - (getCardBalance(idOne) - getCardBalance(idTwo)))/2;
        return balanceTwo;
    }
    public int getCardBalanceFirst() {
        return getCardBalance(idOne);
    }
    public int getCardBalanceSecond() {
        return getCardBalance(idTwo);
    }
}
