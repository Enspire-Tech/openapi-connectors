package com.boomi.connector.ebay;

import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.openapi.OpenAPIBrowser;
import com.boomi.connector.testutil.SimpleAtomConfig;
import com.boomi.connector.testutil.SimpleBrowseContext;
import com.boomi.util.LogUtil;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class  CustomBrowserTest {

    private static final Logger LOG = LogUtil.getLogger(CustomBrowserTest.class);

    final String[] HTTP_METHODS = {
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "PATCH",
            "HEAD",
            "OPTIONS",
            "TRACE"
    };

    private List<Map.Entry<String, String>> getApiOptions() {
        List apiOptions = new ArrayList<Map.Entry<String, String>>();
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_account"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_inventory"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_fulfillment"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_finances"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_marketing"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_negotiation"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_recommendation"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_analytics"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_metadata"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_compliance"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("sellApi", "sell_feed"));
        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("buyApi", "buy_browse"));
        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("buyApi", "buy_marketing"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("commerceApi", "commerce_catalog"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("commerceApi", "commerce_charity"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("commerceApi", "commerce_media"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("commerceApi", "commerce_notification"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("commerceApi", "commerce_taxonomy"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("commerceApi", "commerce_translation"));
//        apiOptions.add(new AbstractMap.SimpleEntry<String, String>("developerApi", "developer_analytics"));
        return apiOptions;
    }

    @Test
    public void testTypes() throws Exception {
        for (Map.Entry<String, String> entry : getApiOptions()) {
            LOG.log(Level.INFO, "Testing types for Api: " + entry.getValue());
            Map connectionProps = new HashMap<String, Object>() {{
                put("apiCategory", entry.getKey());
                put(entry.getKey(), entry.getValue());
            }};

            CustomConnector connector = new CustomConnector();

            for (String httpMethod : HTTP_METHODS) {
                SimpleBrowseContext browseContext = new SimpleBrowseContext(
                        new SimpleAtomConfig(),
                        connector,
                        null,
                        httpMethod,
                        connectionProps,
                        null
                );

                OpenAPIBrowser browser = (OpenAPIBrowser) connector.createBrowser(browseContext);
                try {
                    ObjectTypes objectTypes = browser.getObjectTypes();
                } catch (Exception e) {
                    if (e.getMessage() != null && e.getMessage().contains("no types available")) {
                        continue;
                    } else {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
    }

    @Test
    public void testProfiles() throws Exception {
        LOG.log(Level.INFO, "********************");
        LOG.log(Level.INFO, "Starting testProfiles");

        for (Map.Entry<String, String> entry : getApiOptions()) {

            Map connectionProps = new HashMap<String, Object>() {{
                put("apiCategory", entry.getKey());
                put(entry.getKey(), entry.getValue());
            }};

            int endPointCount = 0;
            int errorCount = 0;
            int stackoverflowCount = 0;
            int otherErrorCount = 0;

            CustomConnector connector = new CustomConnector();

            for (String httpMethod : HTTP_METHODS) {
                SimpleBrowseContext browseContext = new SimpleBrowseContext(
                        new SimpleAtomConfig(),
                        connector,
                        null,
                        httpMethod,
                        connectionProps,
                        null
                );

                OpenAPIBrowser browser = (OpenAPIBrowser) connector.createBrowser(browseContext);
                ObjectTypes objectTypes = null;
                try {
                    objectTypes = browser.getObjectTypes();
                } catch (Exception e) {
                    if (e.getMessage() != null && e.getMessage().contains("no types available")) {
                        continue;
                    } else {
                        e.printStackTrace();
                        throw e;
                    }
                }
                for (ObjectType objectType : objectTypes.getTypes()) {
                    endPointCount++;
                    String path = objectType.getId();
                    String operationId = objectType.getLabel();
                    Set<ObjectDefinitionRole> roles = new HashSet<ObjectDefinitionRole>();
                    roles.add(ObjectDefinitionRole.INPUT);
                    roles.add(ObjectDefinitionRole.OUTPUT);

                    try {
                        try {
                            browser.getObjectDefinitions(path, roles);
                        } catch (StackOverflowError e) {
                            System.err.println("Stackoverflow error for operationId " + operationId);
                            errorCount++;
                            stackoverflowCount++;
                            continue;
                        }
                    } catch (Exception e) {
                        String message = String.format(
                                "Browser failing for path: %s, http method: %s, operation id: %s \n error: %s",
                                path, httpMethod, operationId, e.getMessage());
                        System.err.println(message);
                        errorCount++;
                        otherErrorCount++;
                    }
                }
            }
            LOG.log(Level.INFO, "Api Type: " + entry.getValue());
            LOG.log(Level.INFO, errorCount + " out of " + endPointCount + " endpoints failed.");
            if (errorCount > 0) {
                LOG.log(Level.INFO, stackoverflowCount + " of those are stackoverflow errors.");
                LOG.log(Level.INFO, otherErrorCount + " of those are other errors.");
            }
            LOG.log(Level.INFO, "***************************************************");
        }
    }
}
