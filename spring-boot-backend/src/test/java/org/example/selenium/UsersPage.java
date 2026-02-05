package org.example.selenium;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class UsersPage extends AbstractPage {
  public UsersPage(WebDriver webDriver) {
    super(webDriver);
  }

  public void clickAddUser() {
    click(By.id("addUser"));
    sleep(500);
  }

  public void clickEditUser(String username) {
    click(By.xpath("//button[@id='edit." + username + "']"));
    sleep(500);
  }

  public void deleteUser(String username) {
    click(By.xpath("//button[@id='delete." + username + "']"));
    webDriver.switchTo().alert().accept();
  }

  public void assertUser(String username, String email, String role) {
    try {
      assertEquals(
          username,
          getText(By.xpath("//tr[@id='" + username + "']//td[@id='username']")),
          webDriver.findElement(By.tagName("body")).getText());
      assertEquals(email, getText(By.xpath("//tr[@id='" + username + "']//td[@id='email']")));
      assertEquals(role, getText(By.xpath("//tr[@id='" + username + "']//td[@id='role']")));
    } catch (WebDriverException e) {
      log.warn(webDriver.findElement(By.tagName("body")).getText(), e);
    }
  }

  public void logout() {
    click(By.id("logout"));
    // assertTitle("Login");
  }
}
