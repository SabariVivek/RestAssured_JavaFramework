package com.spotify.oauth2.components.listeners;

import com.spotify.oauth2.utils.ConfigLoader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {

    int counter = 1;
    int retryMaxLimit = ConfigLoader.getInstance().getRetryCount();

    @Override
    public boolean retry(ITestResult result) {
        if (counter <= retryMaxLimit) {
            counter++;
            return true;
        }
        return false;
    }
}