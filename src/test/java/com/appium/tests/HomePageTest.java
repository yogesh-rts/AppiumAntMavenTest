package com.appium.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {

    @Test(description = "[C3]")
    public void testHomePageText() throws InterruptedException {
        boolean isTextDisplayed = driver.findElement(By.xpath("//android.widget.TextView[@text='Hello World!']")).isDisplayed();
        Thread.sleep(2000);
        Assert.assertTrue(isTextDisplayed);
        System.out.println("Test is completed successfully");
    }
}
