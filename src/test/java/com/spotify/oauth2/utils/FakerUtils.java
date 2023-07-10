package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    static Faker faker = new Faker();

    public static String generateName() {
        return faker.name().username();
    }

    public static String generateDescription() {
        return "I like " + faker.music().genre() + " music";
    }

    public static String randomNumber(int i) {
        return faker.number().digits(i);
    }

    public static Boolean generateRandomBoolean() {
        return faker.bool().bool();
    }

    public static String generateRandomColor() {
        return faker.color().name();
    }
}