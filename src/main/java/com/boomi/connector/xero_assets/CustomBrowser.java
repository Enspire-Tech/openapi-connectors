package com.boomi.connector.xero_assets;

import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectDefinitions;
import com.boomi.connector.api.ui.AllowedValue;
import com.boomi.connector.api.ui.BrowseField;
import com.boomi.connector.api.ui.DataType;
import com.boomi.connector.blackboard.CustomOperationConnection;
import com.boomi.connector.openapi.OpenAPIBrowser;
import com.boomi.util.LogUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomBrowser extends OpenAPIBrowser {

    private static final Logger LOG = LogUtil.getLogger(CustomOperationConnection.class);

    public CustomBrowser (CustomConnection conn) {
        super(conn);
    }

    public ObjectDefinitions getObjectDefinitions(String objectTypeId, Collection<ObjectDefinitionRole> roles) {
        ObjectDefinitions definitions = super.getObjectDefinitions(objectTypeId, roles);
        definitions.getOperationFields().add(createTenantListField());
        return definitions;
    }

    public BrowseField createTenantListField() {
        BrowseField listField = new BrowseField();
        listField.setId("tenantId");
        listField.setLabel("Xero Tenant ID");
        listField.setType(DataType.STRING);
        ArrayList<ArrayList<String>> tenantIds;
        tenantIds = getTenentIds();
        for (ArrayList<String> tenantData : tenantIds) {
            String tenantName = tenantData.get(0);
            String tenantId = tenantData.get(1);
            AllowedValue allowedValue = new AllowedValue();
            String labelString = String.format("%s: %s", tenantName, tenantId);
            allowedValue.setLabel(labelString);
            allowedValue.setValue(tenantId);
            listField.getAllowedValues().add(allowedValue);
        }
        return listField;
    }

    private ArrayList<ArrayList<String>> getTenentIds() {
        BufferedReader reader = null;
        HttpsURLConnection connection = null;
        ArrayList<ArrayList<String>> tenantIds = new ArrayList<>();

        try {
            String tenantUrl = "https://api.xero.com/connections";
            String bearerToken = getConnection().getOAuthContext().getOAuth2Token(false).getAccessToken();
            URL url = new URL(tenantUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringWriter out = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String response = out.toString();

            Pattern tenantIdPattern = Pattern.compile(".*?\"tenantId\"\\s*:\\s*\"([^\"]+)\".*?");
            Pattern tenantNamePattern = Pattern.compile(".*?\"tenantName\"\\s*:\\s*\"([^\"]+)\".*?");

            Matcher idMatcher = tenantIdPattern.matcher(response);
            Matcher nameMatcher = tenantNamePattern.matcher(response);
            while(idMatcher.find() && nameMatcher.find()) {
                String tenantName = nameMatcher.group(1);
                String tenantId = idMatcher.group(1);
                ArrayList<String> tenantData = new ArrayList<>();
                tenantData.add(tenantName);
                tenantData.add(tenantId);
                tenantIds.add(tenantData);
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to retrieve Xero Tenant ID", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.log(Level.WARNING, "Unable to close buffered reader for token api call", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return tenantIds;
    }
}
