package com.spotify.oauth2.components.datadriventesting;

import org.testng.annotations.Test;

public class ExcelTest {

    @ExcelPath(fileName = "Data", tab = "PlayList")
    @Test
    public void methodName() throws Exception {
        System.out.println(ExcelUtils.getExcelDataAsListOfMap(ExcelReader.readExcelName(), ExcelReader.readExcelTab()));
    }
}