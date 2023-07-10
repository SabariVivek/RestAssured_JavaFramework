package com.spotify.oauth2.utils;

import java.util.Properties;

public class DataLoader {

    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/properties/data.properties");
    }

    public static DataLoader getInstance() {
        if (dataLoader == null) {
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getGetMethodPlayListID() {
        String prop = properties.getProperty("get_playlist_id");
        if (prop != null)
            return prop;
        else throw new RuntimeException("Property \"Get Playlist ID\" is not specified in the config.properties file");
    }

    public String getUpdateMethodPlayListID() {
        String prop = properties.getProperty("update_playlist_id");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property \"Update Playlist ID\" is not specified in the config.properties file");
    }

    public String getArtistID() {
        String prop = properties.getProperty("artist_id");
        if (prop != null)
            return prop;
        else
            throw new RuntimeException("Property \"Artist ID\" is not specified in the config.properties file");
    }
}