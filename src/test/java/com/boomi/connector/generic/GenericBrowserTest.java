package com.boomi.connector.generic;

import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.openapi.OpenAPIBrowser;
import com.boomi.connector.openapi.OpenAPIConnector;
import com.boomi.connector.testutil.SimpleAtomConfig;
import com.boomi.connector.testutil.SimpleBrowseContext;
import com.boomi.util.LogUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericBrowserTest {

    private static final Logger LOG = LogUtil.getLogger(GenericBrowserTest.class);

    final String URL = "https://www.placeholder.com";
    //final String SPEC = "https://raw.githubusercontent.com/Azure/azure-rest-api-specs/main/specification/storage/data-plane/Microsoft.StorageDataLake/stable/2019-10-31/DataLakeStorage.json";
    final String SPEC = "spec.yaml";

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

    @Test
    public void testTypes() throws Exception {
        CustomConnector connector = new CustomConnector();

        Map<String, Object> connProps = new HashMap<String, Object>() {{
            put("url", URL);
            put("spec", SPEC);
        }};

        for (String httpMethod : HTTP_METHODS) {
            SimpleBrowseContext browseContext = new SimpleBrowseContext(
                    new SimpleAtomConfig(),
                    connector,
                    null,
                    httpMethod,
                    connProps,
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

    @Test
    public void testProfiles() throws Exception {

        int endPointCount = 0;
        int errorCount = 0;
        int stackoverflowCount = 0;
        int otherErrorCount = 0;

        CustomConnector connector = new CustomConnector();

        Map<String, Object> connProps = new HashMap<String, Object>() {{
            put("url", URL);
            put("spec", SPEC);
        }};

        for (String httpMethod : HTTP_METHODS) {
            SimpleBrowseContext browseContext = new SimpleBrowseContext(
                    new SimpleAtomConfig(),
                    connector,
                    null,//OperationType.valueOfCaseInsensitive(httpMethod),
                    httpMethod,
                    connProps,
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
        LOG.log(Level.INFO, errorCount + " out of " + endPointCount + " endpoints failed.");
        LOG.log(Level.INFO, stackoverflowCount + " of those are stackoverflow errors.");
        LOG.log(Level.INFO, otherErrorCount + " of those are other errors.");
    }

}