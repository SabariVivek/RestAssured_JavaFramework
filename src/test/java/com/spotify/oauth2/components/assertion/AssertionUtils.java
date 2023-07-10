package com.spotify.oauth2.components.assertion;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.spotify.oauth2.components.TestBase;
import io.restassured.response.Response;

import java.util.*;

import static com.spotify.oauth2.components.GenericMethods.logTitleInExtentReport;

public class AssertionUtils {

    public static void assertExpectedValuesWithJsonPath(Response response, Map<String, Object> expectedValuesMap) {

        List<AssertionKeys> actualValuesMap = new ArrayList<>();

        //Table headers...
        actualValuesMap.add(new AssertionKeys("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        boolean allMatched = true;

        //Iterate to extract value from response using jsonpath...
        Set<String> jsonPaths = expectedValuesMap.keySet();
        for (String jsonPath : jsonPaths) {
            Optional<Object> actualValue = Optional.ofNullable(response.jsonPath().get(jsonPath));
            if (actualValue.isPresent()) {
                Object value = actualValue.get();

                //Assert actual and expected values...
                if (value.toString().equals(expectedValuesMap.get(jsonPath).toString()))

                    //If value is matched then add details...
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "MATCHED ✅"));
                else {

                    //If single assertion is failed then to update final result as failure...
                    allMatched = false;
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "NOT_MATCHED ❌"));
                }
            }
            //If jsonpath does not exist in the response...
            else {
                allMatched = false;
                actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), "VALUE_NOT_FOUND", "NOT_MATCHED ❌"));
            }
        }

        //To log the details in a tabular format in extent report...
        logTitleInExtentReport("JSON PATH VALIDATIONS");
        String[][] finalAssertionsMap = actualValuesMap.stream().map(assertions -> new String[]{assertions.getJsonPath(), String.valueOf(assertions.getExpectedValue()), String.valueOf(assertions.getActualValue()), assertions.getResult()}).toArray(String[][]::new);

        if (allMatched) {
            TestBase.child.get().pass(MarkupHelper.createTable(finalAssertionsMap));
        } else {
            TestBase.child.get().fail(MarkupHelper.createTable(finalAssertionsMap));
        }
    }
}