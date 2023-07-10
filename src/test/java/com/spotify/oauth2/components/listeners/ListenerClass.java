package com.spotify.oauth2.components.listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.spotify.oauth2.components.TestBase;
import com.spotify.oauth2.components.miscellaneous.JiraOperations;
import com.spotify.oauth2.utils.ConfigLoader;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.Arrays;

public class ListenerClass extends TestListenerAdapter {

    JiraOperations jiraOps = new JiraOperations();

    /**
     * 1. Printing stacktrace into the Extent Report...
     * 2. Jira issue creation code
     */
    @Override
    public void onTestFailure(ITestResult result) {

        // 1...
        TestBase.child.get().fail(MarkupHelper.createLabel(result.getThrowable().getMessage(), ExtentColor.RED));

        String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());
        stackTrace = stackTrace.replaceAll(",", "<br>");
        String formattedTrace = "<details>\n" +
                "<summary>Click here to see the Exception Logs</summary>\n"
                + stackTrace + "\n" +
                "</details>";
        TestBase.child.get().fail(formattedTrace);

        // 2...
        String automaticIssueCreationInJira = ConfigLoader.getInstance().getAutomaticIssueCreation_ONorOFF();
        if (automaticIssueCreationInJira.trim().equalsIgnoreCase("ON")) {
            String issueSummary = "Automation Test Failed - " + result.getMethod().getMethodName();
            String issueDescription = "Test data to be passed here...";
            String issueNumber = null;
            try {
                issueNumber = jiraOps.createJiraIssue("10000", "10005", issueSummary, issueDescription, "3", "Spotify", "60a54c315d67f2006960fc83");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            System.out.println("Jira - Defect ID : " + issueNumber);
        }
    }
}