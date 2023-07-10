package com.spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/properties/config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getClientID() {
        String prop = properties.getProperty("client_id");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"Client ID\" is not specified in the config.properties file");
    }

    public String getClientSecret() {
        String prop = properties.getProperty("client_secret");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"Client Secret\" is not specified in the config.properties file");
    }

    public String getRefreshToken() {
        String prop = properties.getProperty("refresh_token");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"Refresh Token\" is not specified in the config.properties file");
    }

    public String getGrantType() {
        String prop = properties.getProperty("grant_type");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"Grant Type\" is not specified in the config.properties file");
    }

    public String getUserID() {
        String prop = properties.getProperty("user_id");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"User ID\" is not specified in the config.properties file");
    }

    public String getSpotifyBaseURI() {
        String prop = properties.getProperty("spotify_base_uri");
        if (prop != null) return prop;
        else
            throw new RuntimeException("Property \"Spotify - Base URI\" is not specified in the config.properties file");
    }

    public String getSpotifyAccountBaseURI() {
        String prop = properties.getProperty("spotify_account_base_url");
        if (prop != null) return prop;
        else
            throw new RuntimeException("Property \"Spotify Account - Base URI\" is not specified in the config.properties file");
    }

    public int getRetryCount() {
        int prop = Integer.parseInt(properties.getProperty("retryCount"));
        if (properties.getProperty("retryCount") != null) return prop;
        else throw new RuntimeException("Property \"Retry Count\" is not specified in the config.properties file");
    }

    public String getAutomaticIssueCreation_ONorOFF() {
        String prop = properties.getProperty("automatic_Issue_Creation_In_JIRA");
        if (prop != null) return prop;
        else
            throw new RuntimeException("Property \"Automatic issue creation in jira (ON or OFF)\" is not specified in the config.properties file");
    }

    public String getJiraURL() {
        String prop = properties.getProperty("jiraURL");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"Jira - URL\" is not specified in the config.properties file");
    }

    public String getJiraUserName() {
        String prop = properties.getProperty("jiraUserName");
        if (prop != null) return prop;
        else throw new RuntimeException("Property \"Jira - Username\" is not specified in the config.properties file");
    }

    public String getJiraSecretKey() {
        String prop = properties.getProperty("jiraSecretKey");
        if (prop != null) return prop;
        else
            throw new RuntimeException("Property \"Jira - Secret Key\" is not specified in the config.properties file");
    }

    public String getBookingBaseURI() {
        String prop = properties.getProperty("booking_base_uri");
        if (prop != null) return prop;
        else
            throw new RuntimeException("Property \"Booking - Base URI\" is not specified in the config.properties file");
    }
}