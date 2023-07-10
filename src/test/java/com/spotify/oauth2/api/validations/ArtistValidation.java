package com.spotify.oauth2.api.validations;

import com.spotify.oauth2.components.TestBase;
import com.spotify.oauth2.components.assertion.AssertionUtils;
import com.spotify.oauth2.components.enums.StatusCode;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ArtistValidation extends TestBase {

    public void getAnArtist_Validations(Response response){

        //Basic asserts...
        assertStatusCode(response.getStatusCode(), StatusCode.CODE_200);
        assertContentType(response, ContentType.JSON);
        responseSchemaValidation(response);

        //Jsonpath verifications...
        Map<String, Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("name", "Pitbull");
        expectedValueMap.put("type", "artist");
        AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
    }
}