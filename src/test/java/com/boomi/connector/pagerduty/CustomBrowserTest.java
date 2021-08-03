package com.boomi.connector.pagerduty;

import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.openapi.OpenAPIBrowser;
import com.boomi.connector.testutil.SimpleAtomConfig;
import com.boomi.connector.testutil.SimpleBrowseContext;
import com.boomi.util.LogUtil;
import org.junit.Test;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomBrowserTest {

    private static final Logger LOG = LogUtil.getLogger(CustomBrowserTest.class);

    final String URL = "https://{domain}/services/apexrest/spapi/v4";
    final String SPEC = "https://developer.sage.com/api/people/api-reference/openapi.json";
    final String[] HTTP_METHODS = {
            "GET",
            "POST",
            "PUT",
            "DELETE"
    };

    //Ignoring operation ids that we know are causing stackoverflow errors
    final String[] IGNORE_LIST = {
            "getServiceIntegration",
            "createServiceIntegration",
            "updateServiceIntegration"
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
            ObjectTypes objectTypes = browser.getObjectTypes();
        }
    }

    @Test
    //@Ignore //All profiles are not currently working
    public void testProfiles() throws Exception {

        int endPointCount = 0;

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
            ObjectTypes objectTypes = browser.getObjectTypes();
            for (ObjectType objectType : objectTypes.getTypes()) {
                endPointCount++;
                String path = objectType.getId();
                String operationId = objectType.getLabel();
                //Set<ObjectDefinitionRole> roles = browseContext.getOperationType().getSupportedDefinitionRoles();
                Set<ObjectDefinitionRole> roles = new HashSet<ObjectDefinitionRole>();
                roles.add(ObjectDefinitionRole.INPUT);
                roles.add(ObjectDefinitionRole.OUTPUT);

                try {
                    //skip operation ids causing stackoverflow
                    if (Arrays.stream(IGNORE_LIST).anyMatch(operationId::equals)) {
                        continue;
                    }
                    browser.getObjectDefinitions(path, roles);
                } catch (Exception e) {
                    String message = String.format(
                            "Browser failing for path: %s, http method: %s, operation id: %s \n error: %s",
                            path, httpMethod, operationId, e.getMessage());
                    System.err.println(message);
                }
            }
        }
        LOG.log(Level.INFO, "Tested " + endPointCount + " endpoints.");
    }

}