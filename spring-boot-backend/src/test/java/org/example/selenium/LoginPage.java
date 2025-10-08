package org.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {
  public LoginPage(WebDriver webDriver) {
    super(webDriver);
  }

  public void login(String username, String password) {
    setText(By.id("username"), username);
    setText(By.id("password"), password);
    click(By.id("login"));
    //        assertTitle("Users");
  }
}
