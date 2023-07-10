package com.spotify.oauth2.tests.artist;

import com.spotify.oauth2.api.applicationApi.ArtistAPI;
import com.spotify.oauth2.api.validations.ArtistValidation;
import com.spotify.oauth2.components.TestBase;
import com.spotify.oauth2.components.datadriventesting.ExcelPath;
import com.spotify.oauth2.components.datadriventesting.ExcelReader;
import com.spotify.oauth2.components.datadriventesting.ExcelUtils;
import com.spotify.oauth2.components.schema.SchemaPath;
import com.spotify.oauth2.utils.DataLoader;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ArtistTest extends TestBase {

    ArtistValidation artist = new ArtistValidation();

    /**
     * Getting an Artist from Spotify...
     */
    @SchemaPath(filePath = "schemas/artist/GetAnArtist.json")
    @Test
    public void getAnArtist() throws Exception {
        Response response = ArtistAPI.get(DataLoader.getInstance().getArtistID());
        artist.getAnArtist_Validations(response);
    }
}