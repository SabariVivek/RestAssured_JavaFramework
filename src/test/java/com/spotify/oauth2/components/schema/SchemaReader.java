package com.spotify.oauth2.components.schema;

import static com.spotify.oauth2.components.TestBase.schemaFile;

public class SchemaReader {

    public static String readSchemaFile() {
        return schemaFile.filePath();
    }
}