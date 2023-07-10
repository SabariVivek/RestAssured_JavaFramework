package com.spotify.oauth2.api.validations;

import com.spotify.oauth2.api.applicationApi.PlaylistsAPI;
import com.spotify.oauth2.components.TestBase;
import com.spotify.oauth2.components.assertion.AssertionUtils;
import com.spotify.oauth2.components.enums.StatusCode;
import com.spotify.oauth2.pojo.PlayList.PlayList;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class PlaylistValidation extends TestBase {


    public void createAPlaylist(Response response, PlayList pojoBuilder) {
        assertStatusCode(response.getStatusCode(), StatusCode.CODE_201);
        assertContentType(response, ContentType.JSON);
        responseSchemaValidation(response);

        //Jsonpath verifications...
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put("name", pojoBuilder.getName());
                put("description", pojoBuilder.getDescription());
                put("public", pojoBuilder.get_public());
            }
        };
        AssertionUtils.assertExpectedValuesWithJsonPath(response, map);
    }

    public void getAPlaylist(Response response, PlayList pojoBuilder) {
        assertStatusCode(response.getStatusCode(), StatusCode.CODE_200);
        assertContentType(response, ContentType.JSON);
        responseSchemaValidation(response);

        //Jsonpath verifications...
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put("name", pojoBuilder.getName());
                put("description", pojoBuilder.getDescription());
                put("public", pojoBuilder.get_public());
            }
        };
        AssertionUtils.assertExpectedValuesWithJsonPath(response, map);
    }

    public void updateAPlaylist(String createdPlayList, PlayList pojoBuilder) {
        Response response = PlaylistsAPI.put(createdPlayList, pojoBuilder);
        assertStatusCode(response.getStatusCode(), StatusCode.CODE_200);
    }

    public void createAPlaylistWithoutName(Response response) {
        assertStatusCode(response.getStatusCode(), StatusCode.CODE_400);
        assertContentType(response, ContentType.JSON);
        responseSchemaValidation(response);
    }

    public void createAPlaylistWithExpiredToken(Response response) {
        assertStatusCode(response.getStatusCode(), StatusCode.CODE_401);
        assertContentType(response, ContentType.JSON);
        responseSchemaValidation(response);
    }
}