package org.example.config;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import io.github.bonigarcia.wdm.WebDriverManager;

@ComponentScan(basePackages = "org.selenium")
public class SeleniumConfig {
    @Bean
    public WebDriver getWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless=new");
        options.addArguments("window-size=1200x800");
        options.addArguments("no-sandbox");
        options.addArguments("proxy-server='direct://'");
        options.addArguments("proxy-bypass-list=*");
        options.addArguments("remote-allow-origins=*");
        ChromeDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        return chromeDriver;
    }
}
