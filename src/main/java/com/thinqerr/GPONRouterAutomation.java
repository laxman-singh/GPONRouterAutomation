package com.thinqerr;

import java.time.Duration;

import io.goodforgod.graalvm.hint.annotation.DynamicProxyHint;
import io.goodforgod.graalvm.hint.annotation.NativeImageHint;
import io.goodforgod.graalvm.hint.annotation.NativeImageOptions;
import io.goodforgod.graalvm.hint.annotation.ReflectionHint;
import io.goodforgod.graalvm.hint.annotation.ReflectionHints;
import io.goodforgod.graalvm.hint.annotation.ResourceHint;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.manager.SeleniumManagerJsonOutput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@ReflectionHints(
        {
                @ReflectionHint(types = SeleniumManagerJsonOutput.class),
                @ReflectionHint(typeNames = "org.openqa.selenium.manager.SeleniumManagerJsonOutput$Log"),
                @ReflectionHint(typeNames = "org.openqa.selenium.manager.SeleniumManagerJsonOutput$Result"),
        }
)
@NativeImageHint(
        entrypoint = GPONRouterAutomation.class,
        name = "router-automation",
        options = {
                NativeImageOptions.ENABLE_HTTP,
                NativeImageOptions.QUICK_BUILD,
                NativeImageOptions.NO_FALLBACK
        },
        optionNames = {
                "--initialize-at-build-time=org.slf4j.impl.SimpleLogger,org.slf4j.impl.StaticLoggerBinder," +
                        "io.netty.util.internal.logging.Slf4JLoggerFactory,io.netty.channel.MultithreadEventLoopGroup," +
                        "io.netty.util.internal.logging.InternalLoggerFactory,org.slf4j.LoggerFactory",
                "--initialize-at-run-time=io.netty.channel.kqueue,io.netty.handler.ssl.BouncyCastleAlpnSslUtils",
                "-march=compatibility",
                "--gc=G1"
        }
)
@DynamicProxyHint(
        value = {
                @DynamicProxyHint.Configuration(
                        interfaces = java.lang.reflect.Proxy.class
                )
        }
)
@ResourceHint(
        include = {
                ".*selenium-manager",
                ".*ahc-default.properties"
        }
)

public class GPONRouterAutomation {

    public static void main(String[] args) {
        System.out.println("Executing GPON Router Automation Task..........!!");

        FirefoxOptions op = new FirefoxOptions();
        op.addArguments("-headless");
        op.setLogLevel(FirefoxDriverLogLevel.ERROR);

        FirefoxDriverService service = new GeckoDriverService.Builder()
                .withLogOutput(System.out)
                .build();

        WebDriver driver = new FirefoxDriver(service, op);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("http://192.168.1.1/");

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.name("loginBT")).click();
        driver.findElement(By.xpath("/html/body/div/section/div[1]/div/div[1]/ul[5]/li[1]/a")).click();
        driver.findElement(By.xpath("/html/body/div/section/div[1]/div/div[1]/ul[5]/li[6]/a")).click();

        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(2));
        w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        driver.findElement(By.id("do_reboot")).click();

        driver.switchTo().alert().accept();
        driver.close();
        System.out.println("Hurry, mission accomplished ..........!!");
    }
}