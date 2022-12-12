package com.appium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

public class ChromeBrowserTest extends BaseClass{

        @Test
        public void testInputNameAsFifa() throws InterruptedException {
            driver.get("https://google.com");
            driver.findElement(By.name("q")).sendKeys("FIFA");
            Thread.sleep(2000);
            driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
            System.out.println("Test is completed successfully");
        }

    @Test
    public void testInputNameAsMessi() throws InterruptedException {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("Lionel Messi - The GOAT");
        Thread.sleep(2000);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Test is completed successfully");
    }

    @Test
    public void testInputNameAsCR7() throws InterruptedException {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("CR7 - GOAT");
        Thread.sleep(2000);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Test is completed successfully");
    }

    @Test
    public void testInputNameAsQatarFifa() throws InterruptedException {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("FIFA Qatar 2022");
        Thread.sleep(2000);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Test is completed successfully");
    }
}
