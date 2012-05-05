package com.epam.mobile.exampleproject.tests.architecture.foundation;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Created by IntelliJ IDEA.
 * User: ab83625
 * Date: 10.11.2010
 * To change this template use File | Settings | File Templates.
 */
public class SeleniumDriverContext {
    public static final String BROWSER_IE = "*iexplore";
    public static final String BROWSER_FF = "*firefox";
    public static final String BROWSER_CH = "*chrome";

    private static SeleniumDriverContext context;
    private static String siteUrl;

    private Selenium browser;
    //private SeleniumServer seleniumServer;

    private SeleniumDriverContext() {
    }

    public static void initInstance(String browserType, String siteURL) {
        context = new SeleniumDriverContext();
        siteUrl = siteURL;
        context.setBrowser(new DefaultSelenium("localhost", 4444, browserType, siteURL));
        context.start();
    }

    public static SeleniumDriverContext getInstance() {
        if (context == null) {
            throw new IllegalStateException("Context is not initialized");
        }
        return context;
    }

    public Selenium getBrowser() {
        if (browser != null) {
            return browser;
        }
        throw new IllegalStateException("WebBrowser is not initialized");
    }

    public String getSiteUrl() {
       return siteUrl;
    }

    public void start() {
        try {
            //seleniumServer = new SeleniumServer();
            //seleniumServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        browser.start();
    }

    public void close() {
        browser.close();
        browser.stop();
        //seleniumServer.stop();
    }

    public void setBrowser(Selenium browser) {
        this.browser = browser;
    }
}