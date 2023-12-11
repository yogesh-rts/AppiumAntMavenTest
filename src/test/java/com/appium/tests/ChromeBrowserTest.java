package com.appium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChromeBrowserTest extends BaseClass {

    @Test(enabled = false)
    public void testInputNameAsFifa() throws InterruptedException {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("FIFA");
        Thread.sleep(2000);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Test is completed successfully");
    }

    @Test(enabled = false)
    public void testInputNameAsMessi() throws InterruptedException {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("Lionel Messi - The GOAT");
        Thread.sleep(2000);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Test is completed successfully");
    }

    @Test(enabled = false)
    public void testInputNameAsCR7() throws InterruptedException {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("CR7 - GOAT");
        Thread.sleep(2000);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Test is completed successfully");
    }

    @Test(enabled = false)
    public void testInputNameAsQatarFifa() throws InterruptedException {
        driver.get("https://google.com");
        driver.findElement(By.name("q")).sendKeys("FIFA Qatar 2022");
        Thread.sleep(2000);
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        System.out.println("Test is completed successfully");
    }

    @Test(description = "[C1]")
    public void testGreetingText() throws InterruptedException {
        boolean isTextDisplayed = driver.findElement(By.xpath("//android.widget.TextView[@text='Hello World!']")).isDisplayed();
        String textShown = driver.findElement(By.xpath("//android.widget.TextView[@text='Hello World!']")).getText();
        Thread.sleep(2000);
        Assert.assertTrue(isTextDisplayed);
        Assert.assertEquals(textShown, "Hello Word!");
        System.out.println("Test is completed successfully");
    }

    @Test(description = "[C4]")
    public void testPageText() throws InterruptedException {
        boolean isTextDisplayed = driver.findElement(By.xpath("//android.widget.TextView[@text='Hello World!']")).isDisplayed();
        String textShown = driver.findElement(By.xpath("//android.widget.TextView[@text='Hello World!']")).getText();
        Thread.sleep(2000);
        Assert.assertTrue(isTextDisplayed);
        Assert.assertEquals(textShown, "Hello World!");
        System.out.println("Test is completed successfully");
    }
}
