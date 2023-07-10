package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.components.RestResource;
import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistsAPI {

    public static Response post(Object requestPlayList) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserID() + PLAYLISTS, getToken(), requestPlayList);
    }

    public static Response post(String token, Object requestPlaylist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserID() + PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playListId) {
        return RestResource.get(PLAYLISTS + "/" + playListId, getToken());
    }

    public static Response put(String playListId, Object requestPlaylist) {
        return RestResource.put(PLAYLISTS + "/" + playListId, getToken(), requestPlaylist);
    }
}