package com.spotify.oauth2.components.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import static com.spotify.oauth2.components.GenericMethods.date;
import static com.spotify.oauth2.components.GenericMethods.time;

public class ExtentManager {

    private static ExtentReports extent;

    public static String Path = System.getProperty("user.dir") + "/Reports/" + date() + "/" + "Test-Report (" + time() + ").html";

    public static ExtentReports getInstance() {
        if (null == extent) createInstance(Path);
        return extent;
    }

    private static void createInstance(String fileName) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

        //------ Extent Spark Report Configuration ------//
        htmlReporter.viewConfigurer().viewOrder().as(new ViewName[]{ViewName.TEST, ViewName.CATEGORY, ViewName.DEVICE, ViewName.DASHBOARD,}).apply();

        htmlReporter.config().setTimelineEnabled(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Rest API - Automation Report");
        htmlReporter.config().setDocumentTitle("Automation Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
}