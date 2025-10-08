package org.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditUserPage extends AbstractPage {

  public EditUserPage(WebDriver webDriver) {
    super(webDriver);
  }

  public void editUser(String username, String password, String email, String role)
      throws InterruptedException {
    setText(By.id("username"), username);
    setText(By.id("password"), password);
    setText(By.id("email"), email);
    selectByText(By.id("role"), role);
    click(By.id("saveUser"));
  }

  public void addUser(String username, String password, String email, String role)
      throws InterruptedException {
    editUser(username, password, email, role);
  }
  //    public void validateUser() {
  //        setText(By.id("username"), "");
  //        setText(By.id("password"), "");
  //        setText(By.id("email"), "");
  //        selectByValue(By.id("role"), Role.ROLE_USER.name());
  //        click(By.id("submit"));
  //        assertFieldError(By.id("username"));
  //        assertFieldError(By.id("password"));
  //        assertFieldError(By.id("email"));
  //        users();
  //    }
}
