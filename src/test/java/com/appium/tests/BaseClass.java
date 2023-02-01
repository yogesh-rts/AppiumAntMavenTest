package com.appium.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;

public class BaseClass {

    AppiumDriver driver;

    @BeforeTest
    public void setUp() {

        try{

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1.0");
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, "EMULATOR27");
            caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "60");
            /*caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.demoandroidapp");
            caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "MainActivity");
            caps.setCapability(AndroidMobileCapabilityType.ALLOW_TEST_PACKAGES, true);
            caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);*/
            caps.setCapability("noReset", "true");
            caps.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");


            URL url = new URL("http://127.0.0.1:4723/wd/hub");

            driver = new AndroidDriver(url,caps);
          //  driver = new AndroidDriver(url, caps);

        } catch (Exception e) {
            System.out.println("Exception: " + e.getCause());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Test
    public void sampleTest() throws InterruptedException {
        System.out.println("This is a sample test");
    }

    @AfterTest
    public void tearDown() {
        driver.navigate().back();
    }

}
