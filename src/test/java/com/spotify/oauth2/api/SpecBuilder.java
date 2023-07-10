package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.oauth2.api.Route.*;
import static com.spotify.oauth2.components.GenericMethods.logTitleInExtentReport;
import static com.spotify.oauth2.components.GenericMethods.pass;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec() {
        String baseURI = ConfigLoader.getInstance().getSpotifyBaseURI();
        logTitleInExtentReport("<<< BASIC INFO >>>");
        pass("Base URI : " + "<a href='" + baseURI + "'>" + baseURI + "</a>");
        pass("End Point : " + "<a href='" + BASE_PATH + "'>" + BASE_PATH + "</a>");

        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().getSpotifyBaseURI()).
                setBasePath(BASE_PATH).
                log(LogDetail.ALL).
                build();
    }

    public static RequestSpecification getAccountRequestSpec() {
        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().getSpotifyAccountBaseURI()).
                setBasePath(API + TOKEN).
                setContentType(ContentType.URLENC).
                log(LogDetail.ALL).
                build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).
                build();
    }
}