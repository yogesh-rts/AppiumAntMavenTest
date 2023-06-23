package com.appium.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseClass {

    @Test(description = "[C9]")
    public void testLoginPageText() throws InterruptedException {
        boolean isTextDisplayed = driver.findElement(By.xpath("//android.widget.TextView[@text='Hello World!']")).isDisplayed();
        Thread.sleep(2000);
        Assert.assertTrue(isTextDisplayed);
        System.out.println("Test is completed successfully");
    }
}
