package com.spotify.oauth2.components.datadriventesting;

import static com.spotify.oauth2.components.TestBase.excelFile;

public class ExcelReader {

    public static String readExcelName() {
        return excelFile.fileName();
    }

    public static String readExcelTab() {
        return excelFile.tab();
    }
}