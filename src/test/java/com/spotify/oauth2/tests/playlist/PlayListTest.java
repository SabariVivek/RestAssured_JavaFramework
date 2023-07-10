package com.spotify.oauth2.tests.playlist;

import com.spotify.oauth2.api.applicationApi.PlaylistsAPI;
import com.spotify.oauth2.api.validations.PlaylistValidation;
import com.spotify.oauth2.components.TestBase;
import com.spotify.oauth2.components.schema.SchemaPath;
import com.spotify.oauth2.pojo.PlayList.CreatedPlayList;
import com.spotify.oauth2.pojo.PlayList.PlayList;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.randomNumber;

public class PlayListTest extends TestBase {

    PlayList playlist = new PlayList();
    CreatedPlayList createdPlaylist = new CreatedPlayList();
    PlaylistValidation playlistValidation = new PlaylistValidation();

    /**
     * Creating a playlist with the valid data...
     */
    @SchemaPath(filePath = "schemas/playlist/CreateNewPlaylist.json")
    @Test(priority = -3)
    public void createAPlaylist() {
        Response response = PlaylistsAPI.post(playlist);
        playlistValidation.createAPlaylist(response, playlist);

        //Setting the created playlist ID to POJO...
        createdPlaylist.setCreatedPlaylist(response.jsonPath().get("id").toString());
    }

    /**
     * Getting a playlist details which we created above...
     */
    @SchemaPath(filePath = "schemas/playlist/GetPlaylist.json")
    @Test(priority = -2)
    public void getAPlaylist() {
        Response response = PlaylistsAPI.get(createdPlaylist.getCreatedPlaylist());
        playlistValidation.getAPlaylist(response, playlist);
    }

    /**
     * Updating a playlist data which we created above...
     */
    @Test(priority = -1)
    public void updateAPlaylist() {
        playlist = new PlayList().toBuilder().name("Sabari Vivek").build();
        playlistValidation.updateAPlaylist(createdPlaylist.getCreatedPlaylist(), playlist);
    }

    /**
     * Creating a playlist without the playlist name...
     */
    @SchemaPath(filePath = "schemas/playlist/CreatePlaylist_Negative.json")
    @Test
    public void createAPlaylistWithoutName() {
        playlist = new PlayList().toBuilder().name("").build();
        Response response = PlaylistsAPI.post(playlist);
        playlistValidation.createAPlaylistWithoutName(response);
    }

    /**
     * Creating a playlist with the expired token...
     */
    @SchemaPath(filePath = "schemas/playlist/CreatePlaylist_Negative.json")
    @Test
    public void createAPlaylistWithExpiredToken() {
        Response response = PlaylistsAPI.post(randomNumber(5), playlist);
        playlistValidation.createAPlaylistWithExpiredToken(response);
    }
}