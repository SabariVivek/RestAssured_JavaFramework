package com.spotify.oauth2.components;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.gson.Gson;
import com.spotify.oauth2.components.enums.StatusCode;
import com.spotify.oauth2.components.schema.SchemaReader;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GenericMethods {

    public static String methodType = "";

    public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
        TestBase.child.get().info(MarkupHelper.createLabel("<<< BASIC VALIDATIONS >>>", ExtentColor.BROWN));
        assertThat(actualStatusCode, equalTo(statusCode.code));
        pass("Successfully verified the \"Status Code - " + statusCode.code + "\"");
    }

    public void assertContentType(Response response, ContentType contentType) {
        String responseContentType = response.getContentType();
        if (responseContentType.contains(contentType.toString())) {
            pass("Successfully verified the Content Type - \"" + contentType + "\"");
        } else {
            fail("Content Type value is not matched with the expected one - \"" + contentType + "\"");
        }
    }

    public void assertHeader(Response response, String headerName, String headerValue) {
        String actualHeaderValue = response.header(headerName);
        assertThat(actualHeaderValue, equalTo(headerValue));
        pass("Successfully verified the Header - \"" + actualHeaderValue + "\"");
    }

    public void assertHeaders(Response response, Map<Object, Object> hashMap) {
        List<Object> headersList = new ArrayList<>();
        Headers headers = response.headers();
        Map<Object, Object> responseHeaders = new HashMap<>();

        for (Header header : headers) {
            responseHeaders.put(header.getName(), header.getValue());
        }

        for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
            if (responseHeaders.entrySet().contains(entry)) {
                headersList.add(entry.getKey());
            } else {
                fail("For the given request, unable to find the Header - \"" + entry.getKey() + "\"");
            }
        }

        pass("Successfully verified the below \"Headers\" with its value : ");
        TestBase.child.get().pass(MarkupHelper.createOrderedList(headersList));
    }

    public void responseSchemaValidation(Response response) {
        TestBase.child.get().info(MarkupHelper.createLabel("<<< SCHEMA VALIDATION >>>", ExtentColor.BROWN));
        response.then().body(matchesJsonSchemaInClasspath(SchemaReader.readSchemaFile()));
        System.out.println("<<<<< RESPONSE - SCHEMA VALIDATION COMPLETED SUCCESSFULLY >>>>>");
        pass("Successfully validated the \"Schema\" against the \"Response Body\"");
    }

    public static void printMethodTypeInExtentReport(Method method){
        pass("Method Type : " + "<a href='" + method.name() + "'>" + method.name() + "</a>");

        //This will help us to categorize method types in Extent Report...
        methodType(method.name());
    }

    public static void requestPrinterInExtentReport(Object requestPayload) {
        TestBase.child.get().info(MarkupHelper.createLabel("<<< REQUEST BODY >>>", ExtentColor.BROWN));
        if (requestPayload.toString().trim().equals("")) {
            pass("NO RESPONSE BODY !!!");
        } else {
            TestBase.child.get().pass(MarkupHelper.createCodeBlock(new Gson().toJson(requestPayload), CodeLanguage.JSON));
        }
    }

    public static void responsePrinterInExtentReport(Response response) {
        TestBase.child.get().info(MarkupHelper.createLabel("<<< RESPONSE HEADERS >>>", ExtentColor.BROWN));
        logHeaders(response);

        TestBase.child.get().info(MarkupHelper.createLabel("<<< RESPONSE BODY >>>", ExtentColor.BROWN));
        if (response.asString().trim().equals("")) {
            pass("NO RESPONSE BODY !!!");
        } else {
            TestBase.child.get().pass(MarkupHelper.createCodeBlock(response.asPrettyString(), CodeLanguage.JSON));
        }
    }

    public static void fail(String Description) {
        TestBase.child.get().fail(Description);
    }

    public void warning(String Description) {
        TestBase.child.get().warning(Description);
    }

    public static void pass(String description) {
        TestBase.child.get().pass(description);
    }

    public void info(String description) {
        TestBase.child.get().info(description);
    }

    public static void logHeaders(Response response) {

        String tableAppender = "";
        for (Header header : response.getHeaders()) {
            String headerName = header.getName();
            String headerValue = header.getValue();

            tableAppender = tableAppender + "<tr>\n" +
                    "    <td>" + headerName + "</td>\n" +
                    "    <td>" + headerValue + "</td>\n" +
                    "</tr>";
        }

        String headerCode = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<details>\n" +
                "<summary>Click here to view the \"Response Headers\"</summary>\n" +
                "<table>\n" +
                "<br>" +
                "  <tr>\n" +
                "    <th>Header</th>\n" +
                "    <th>Value</th>\n" +
                "  </tr>\n" +
                "  " + tableAppender +
                "</table>\n" +
                "</details>\n" +
                "</body>\n" +
                "</html>\n";

        TestBase.child.get().info(headerCode);
    }

    public static void logTitleInExtentReport(String message) {
        TestBase.child.get().info(MarkupHelper.createLabel("<<< " + message + " >>>", ExtentColor.BROWN));
    }

    public static String date() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy EEE");
        return dateFormat.format(date);
    }

    public static String time() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
        return dateFormat.format(date);
    }

    public static void methodType(String method) {
        switch (method.toUpperCase()) {
            case "GET":
                methodType = "GET";
                break;
            case "POST":
                methodType = "POST";
                break;
            case "PUT":
                methodType = "PUT";
                break;
            case "PATCH":
                methodType = "PATCH";
                break;
            case "DELETE":
                methodType = "DELETE";
                break;
        }
    }
}