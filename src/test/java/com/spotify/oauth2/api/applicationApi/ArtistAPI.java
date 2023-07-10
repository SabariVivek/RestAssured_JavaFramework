package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.components.RestResource;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.ARTISTS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class ArtistAPI {

    public static Response get(String artistID) {
        return RestResource.get(ARTISTS + "/" + artistID, getToken());
    }
}