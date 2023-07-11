package ru.netology.web.test;
import ru.netology.web.data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
  DashboardPage dashboardPage;

  @BeforeEach
  public void setUp() {
//        Configuration.holdBrowserOpen = true;
    open("http://localhost:9999");
    var loginPage = new LoginPageV1();
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getValidCode(authInfo);
    dashboardPage = verificationPage.validVerify(verificationCode);
    balance(dashboardPage);
  }

  public void balance(DashboardPage dashboardPage) {
    int initialBalance = dashboardPage.getCard1Balance();
    int depositBalance = 10_000 - initialBalance;
    if (depositBalance > 0) {
      dashboardPage.topUpCard1().deposit(depositBalance, DataHelper.card2Number());
    } else if (depositBalance < 0) {
      dashboardPage.topUpCard2().deposit(depositBalance, DataHelper.card1Number());
    }
  }

  @Test
  void validTestCard1() {
    int actual1 = dashboardPage.topUpCard1().deposit(2_000, DataHelper.card2Number()).getCard1Balance();
    Assertions.assertEquals(12_000, actual1);
    int actual2 = dashboardPage.getCard2Balance();
    Assertions.assertEquals(8_000, actual2);
  }

  @Test
  void validTestCard2() {
    int actual1 = dashboardPage.topUpCard2().deposit(5_000, DataHelper.card1Number()).getCard2Balance();
    Assertions.assertEquals(15_000, actual1);
    int actual2 = dashboardPage.getCard1Balance();
    Assertions.assertEquals(5_000, actual2);
  }

  @Test
  void validAmount9_999() {
    int actual1 = dashboardPage.topUpCard2().deposit(1, DataHelper.card1Number()).getCard2Balance();
    Assertions.assertEquals(10_001, actual1);
    int actual2 = dashboardPage.getCard1Balance();
    Assertions.assertEquals(9_999, actual2);
  }

  @Test
  void validAmount1() {
    int actual1 = dashboardPage.topUpCard2().deposit(9_999, DataHelper.card1Number()).getCard2Balance();
    Assertions.assertEquals(19_999, actual1);
    int actual2 = dashboardPage.getCard1Balance();
    Assertions.assertEquals(1, actual2);
  }

  @Test
  void validNull() {
    int actual1 = dashboardPage.topUpCard2().deposit(0, DataHelper.card1Number()).getCard2Balance();
    Assertions.assertEquals(10_000, actual1);
    int actual2 = dashboardPage.getCard1Balance();
    Assertions.assertEquals(10_000, actual2);
  }

  @Test
  void validAll() {
    int actual1 = dashboardPage.topUpCard2().deposit(10_000, DataHelper.card1Number()).getCard2Balance();
    Assertions.assertEquals(20_000, actual1);
    int actual2 = dashboardPage.getCard1Balance();
    Assertions.assertEquals(0, actual2);
  }

  @Test
  void moreThanStated() {
    int actual1 = dashboardPage.topUpCard2().deposit(15_000, DataHelper.card1Number()).getCard2Balance();
    Assertions.assertEquals(10_000, actual1);
    int actual2 = dashboardPage.getCard1Balance();
    Assertions.assertEquals(10_000, actual2);
  }
}