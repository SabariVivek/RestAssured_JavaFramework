package com.spotify.oauth2.components;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.*;
import static com.spotify.oauth2.components.GenericMethods.*;
import static io.restassured.RestAssured.given;

public class RestResource {

    /**
     * Post method --> To create a resource to the target system...
     */
    public static Response post(String path, String accessToken, Object requestPlaylist) {
        Response response = given(getRequestSpec()).
                body(requestPlaylist).
                relaxedHTTPSValidation().
                auth().oauth2(accessToken).
                when().post(path).
                then().spec(getResponseSpec().contentType(ContentType.JSON)).
                extract().
                response();

        printMethodTypeInExtentReport(Method.POST);
        requestPrinterInExtentReport(requestPlaylist);
        responsePrinterInExtentReport(response);
        return response;
    }

    /**
     * Post method --> specifically used for Token Manager...
     */
    public static Response postAccount(HashMap<String, String> formParams) {
        return given(getAccountRequestSpec()).
                relaxedHTTPSValidation().
                formParams(formParams).
                when().post().
                then().spec(getResponseSpec()).
                extract().response();
    }

    /**
     * Get method --> To fetch the data from the target system...
     */
    public static Response get(String path, String accessToken) {
        Response response = given(getRequestSpec()).relaxedHTTPSValidation().
                auth().oauth2(accessToken).
                when().get(path).
                then().spec(getResponseSpec().contentType(ContentType.JSON)).
                extract().response();

        printMethodTypeInExtentReport(Method.GET);
        responsePrinterInExtentReport(response);
        return response;
    }

    /**
     * Put method --> To update the data in the target system...
     */
    public static Response put(String path, String accessToken, Object requestPlaylist) {
        Response response = given(getRequestSpec()).
                body(requestPlaylist).relaxedHTTPSValidation().
                auth().oauth2(accessToken).
                when().put(path).
                then().spec(getResponseSpec()).
                extract().response();

        printMethodTypeInExtentReport(Method.PUT);
        requestPrinterInExtentReport(requestPlaylist);
        responsePrinterInExtentReport(response);
        return response;
    }
}