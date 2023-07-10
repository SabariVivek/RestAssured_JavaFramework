package com.spotify.oauth2.components;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.spotify.oauth2.components.datadriventesting.ExcelPath;
import com.spotify.oauth2.components.reporting.ExtentManager;
import com.spotify.oauth2.components.schema.SchemaPath;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;

public class TestBase extends GenericMethods {

    public static SchemaPath schemaFile;
    public static ExcelPath excelFile;
    public static ExtentReports extent;
    public static String logFilePath;
    public static FileWriter writer;
    public static String fileName, tabName;

    private static final ThreadLocal<ExtentTest> parentTestThreadLocal = new ThreadLocal<>();
    private final HashMap<String, ExtentTest> extentMap = new HashMap<>();
    public static ThreadLocal<ExtentTest> child = new ThreadLocal<>();
    protected ThreadLocal<String> testNameParameter = new ThreadLocal<>();
    protected static ThreadLocal<String> categoryGroup = new ThreadLocal<>();

    @BeforeSuite
    public void beforeSuite() {
        extent = ExtentManager.getInstance();
        creatingALogFile();
    }

    @BeforeClass
    public void beforeClass() {
        parentTestThreadLocal.set(extent.createTest(getClass().getSimpleName()));
        extentMap.put(getClass().getSimpleName(), parentTestThreadLocal.get());
    }

    @BeforeMethod
    public void beforeMethod(final Method method) {
        schemaFile = method.getAnnotation(SchemaPath.class);
        excelFile = method.getAnnotation(ExcelPath.class);

        setTestCategory();
        child.set(extentMap.get(getClass().getSimpleName()).createNode(method.getName() + dataProviderString()));
        info("<b><<<< Execution Started >>>>></b>");
        System.out.println("<<<<< STARTING TEST : " + method.getName() + " >>>>>");
        System.out.println("<<<<< THREAD ID : " + Thread.currentThread().getId() + " >>>>>");
    }

    @AfterMethod
    public void afterMethod(Method method, ITestResult iTestResult) {
        child.get().assignCategory(categoryGroup.get());
        child.get().assignDevice(methodType);
        info("<b><<<< Execution Ended >>>>></b>");

        try {
            writer = new FileWriter(logFilePath);
            writer.write("<<<<<----- Execution Status ----->>>>>\n");

            if (iTestResult.getStatus() == ITestResult.SUCCESS) {
                writer.write("TC PASSED --> " + method.getName() + "\n");
            } else if (iTestResult.getStatus() == ITestResult.FAILURE) {
                writer.write("TC FAILED --> " + method.getName() + "\n");
            } else if (iTestResult.getStatus() == ITestResult.SKIP) {
                writer.write("TC SKIPPED --> " + method.getName() + "\n");
            }
            writer.close();
        } catch (Exception ignored) {
        }
    }

    @AfterSuite
    public void afterSuite() {
        addInformationToExtentReport();
        extent.flush();
    }

    private void addInformationToExtentReport() {
        try {
            extent.setSystemInfo("OS : ", System.getProperty("os.name"));
            extent.setSystemInfo("OS Architecture : ", System.getProperty("os.arch"));
            extent.setSystemInfo("User Name : ", System.getProperty("user.name"));
            extent.setSystemInfo("Machine Name : ", System.getProperty("machine.name"));
            extent.setSystemInfo("IP Address : ", System.getProperty("machine.address"));
            extent.setSystemInfo("Java Version : ", System.getProperty("java.version"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setTestCategory() {
        try {
            categoryGroup.set(getClass().getSimpleName());
        } catch (ArrayIndexOutOfBoundsException e) {
            categoryGroup.set("unknownCategory");
        }
    }

    private String dataProviderString() {
        if (testNameParameter.get() != null) {
            return " (" + testNameParameter.get() + ")";
        }
        return "";
    }

    public void creatingALogFile() {

        logFilePath = System.getProperty("user.dir") + File.separator + "Logs" + File.separator + date() + File.separator + "LogFile (" + time() + ").txt";

        try {
            File file = new File(logFilePath);
            File dir = new File("Logs" + File.separator + date());

            if ((dir.exists() || dir.mkdir()) && file.createNewFile()) {
                System.out.println("<<<<< Log file created successfully >>>>>");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("<<<<< Unable to create a Log File >>>>>");
        }
    }
}