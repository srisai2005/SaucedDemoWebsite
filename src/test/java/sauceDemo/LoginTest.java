package sauceDemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    WebDriver driver;

    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void validLoginTest() {
        setup();

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    void invalidPasswordTest() {
        setup();

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        assertEquals(
            "Epic sadface: Username and password did not match any user in this service",
            errorMsg
        );
    }

    @Test
    void emptyFieldsTest() {
        setup();

        driver.findElement(By.id("login-button")).click();

        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        assertEquals("Epic sadface: Username is required", errorMsg);
    }

    @Test
    void emptyPasswordTest() {
        setup();

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();

        String errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();

        assertEquals("Epic sadface: Password is required", errorMsg);
    }
}
