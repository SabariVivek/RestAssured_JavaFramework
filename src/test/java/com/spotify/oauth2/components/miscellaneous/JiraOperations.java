package com.spotify.oauth2.components.miscellaneous;

import com.spotify.oauth2.utils.ConfigLoader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Base64;

public class JiraOperations {
    String jiraURL = ConfigLoader.getInstance().getJiraURL();
    String jiraUserName = ConfigLoader.getInstance().getJiraUserName();
    String jiraAccessKey = ConfigLoader.getInstance().getJiraSecretKey();

    /**
     * To create jira issue as Bug...
     */
    public String createJiraIssue(String projectName,
                                  String issueType,
                                  String issueSummary,
                                  String issueDescription,
                                  String priority,
                                  String label,
                                  String assignee) throws Exception {

        String issueId;

        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = jiraURL + "/rest/api/3/issue";
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type", "application/json");

        String encoding = Base64.getEncoder().encodeToString((jiraUserName + ":" + jiraAccessKey).getBytes());
        postRequest.setHeader("Authorization", "Basic " + encoding);

        StringEntity params = new StringEntity(createPayloadForCreateJiraIssue(projectName, issueType, issueSummary, issueDescription, priority, label, assignee));
        postRequest.setEntity(params);
        HttpResponse response = httpClient.execute(postRequest);

        //Converting HTTP Response to String...
        String jsonString = EntityUtils.toString(response.getEntity());

        //Converting String to JSON...
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonString);

        //Extract IssueKey from JSON...
        issueId = (String) json.get("key");

        return issueId;
    }

    /**
     * Add attachment to already created bug/issue in JIRA...
     * Only used for UI Related Screen's...
     */
    public void addAttachmentToJiraIssue(String issueId, String filePath) throws Exception {
        File fileUpload = new File(filePath);

        HttpClient httpClient = HttpClientBuilder.create().build();
        String url = jiraURL + "/rest/api/3/issue/" + issueId + "/attachments";
        HttpPost postRequest = new HttpPost(url);

        String encoding = Base64.getEncoder().encodeToString((jiraUserName + ":" + jiraAccessKey).getBytes());

        postRequest.setHeader("Authorization", "Basic " + encoding);
        postRequest.setHeader("X-Atlassian-Token", "nocheck");

        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        entity.addPart("file", new FileBody(fileUpload));
        postRequest.setEntity(entity.build());
        HttpResponse response = httpClient.execute(postRequest);
        System.out.println(response.getStatusLine());

        if (response.getStatusLine().toString().contains("200 OK")) {
            System.out.println("Attachment uploaded");
        } else {
            System.out.println("Attachment not uploaded");
        }
    }

    /**
     * Creates payload for issue's post request...
     */
    private static String createPayloadForCreateJiraIssue(String projectName,
                                                          String issueType,
                                                          String issueSummary,
                                                          String issueDescription,
                                                          String priority,
                                                          String label,
                                                          String assigneeId) {
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"id\": \"" + projectName + "\"\n" +
                "        },\n" +
                "        \"issuetype\": {\n" +
                "            \"id\": \"" + issueType + "\"\n" +
                "        },\n" +
                "        \"summary\": \"" + issueSummary + "\",\n" +
                "        \"priority\": {\n" +
                "            \"id\": \"" + priority + "\"\n" +
                "        },\n" +
                "        \"description\": {\n" +
                "            \"version\": 1,\n" +
                "            \"type\": \"doc\",\n" +
                "            \"content\": [\n" +
                "                {\n" +
                "                    \"type\": \"paragraph\",\n" +
                "                    \"content\": [\n" +
                "                        {\n" +
                "                            \"type\": \"text\",\n" +
                "                            \"text\": \"" + issueDescription + "\"\n" +
                "                        }\n" +
                "                    ]\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"assignee\": {\n" +
                "            \"id\": \"" + assigneeId + "\"\n" +
                "        },\n" +
                "        \"labels\": [\n" +
                "            \"" + label + "\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";
    }
}