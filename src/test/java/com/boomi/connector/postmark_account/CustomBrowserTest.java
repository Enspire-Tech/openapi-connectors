package com.boomi.connector.postmark_account;

import com.boomi.connector.api.*;
import com.boomi.connector.openapi.OpenAPIBrowser;
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

public class CustomBrowserTest {

    private static final Logger LOG = LogUtil.getLogger(CustomBrowserTest.class);
    private static final String SPEC = "custom-specification-postmark_account.yaml";
    final Map<String, Object> connProps = new HashMap<String, Object>() {{
        put("spec", SPEC);
    }};

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
                browser.getObjectTypes();
            } catch (Exception e) {
                if (e.getMessage() == null || !e.getMessage().contains("no types available")) {
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
                        ObjectDefinitions objectDefinitions = browser.getObjectDefinitions(path, roles);
                        boolean outputExists = false;
                        for (ObjectDefinition objectDefinition: objectDefinitions.getDefinitions()) {
                            ContentType contentType = objectDefinition.getOutputType();
                            if (!contentType.toString().contains("NONE")) {
                                outputExists = true;
                            }
                        }
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
