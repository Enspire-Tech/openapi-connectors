package com.boomi.connector.interzoid;

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
    private static final String SPEC = "";
    final String[] SPECS = {
            "https://oas.interzoid.com/api/getcompanymatchadvanced.json",
            "https://oas.interzoid.com/api/getfullnamematch.json",
            "https://oas.interzoid.com/api/getfullnameparsedmatch.json",
            "https://oas.interzoid.com/api/getaddressmatchadvanced.json",
            "https://oas.interzoid.com/api/getcitymatch.json",
            "https://oas.interzoid.com/api/getcountrymatch.json",
            "https://oas.interzoid.com/api/getstateabbreviation.json",
            "https://oas.interzoid.com/api/getcitystandard.json",
            "https://oas.interzoid.com/api/getcountrystandard.json",
            "https://oas.interzoid.com/api/getemailinfo.json",
            "https://oas.interzoid.com/api/globalpageload.json",
            "https://oas.interzoid.com/api/getcurrencyrate.json",
            "https://oas.interzoid.com/api/convertcurrency.json",
            "https://oas.interzoid.com/api/getcryptoprice.json",
            "https://oas.interzoid.com/api/getmetalprice.json",
            "https://oas.interzoid.com/api/getglobaltime.json",
            "https://oas.interzoid.com/api/getglobalnumberinfo.json",
            "https://oas.interzoid.com/api/lookupareacode.json",
            "https://oas.interzoid.com/api/getareacodefromnumber.json",
            "https://oas.interzoid.com/api/getweathercity.json",
            "https://oas.interzoid.com/api/getweatherzip.json",
            "https://oas.interzoid.com/api/getweatherlatlong.json",
            "https://oas.interzoid.com/api/getzipinfo.json"
    };

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
        for (String api : SPECS) {
            Map connProps = new HashMap<String, Object>() {{
                put("spec", api);
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
    }

    @Test
    public void testProfiles() throws Exception {

        LOG.log(Level.INFO, "starting testprofiles\n*******************");

        int endPointCount = 0;
        int errorCount = 0;
        int stackoverflowCount = 0;
        int otherErrorCount = 0;

        CustomConnector connector = new CustomConnector();
        for (String api : SPECS) {
            Map connProps = new HashMap<String, Object>() {{
                put("spec", api);
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
                            if (!outputExists && httpMethod.contains("GET")) {
                                String message = String.format(
                                        "No output for path: %s, http method: %s, operation id: %s",
                                        path, httpMethod, operationId);
                                System.err.println(message);
                                errorCount++;
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
            LOG.log(Level.INFO, "Api: " + api + " / method: " + httpMethod);
            LOG.log(Level.INFO, errorCount + " out of " + endPointCount + " endpoints failed.");
            if (errorCount > 0) {
                LOG.log(Level.INFO, stackoverflowCount + " of those are stackoverflow errors.");
                LOG.log(Level.INFO, otherErrorCount + " of those are other errors.");
            }
            LOG.log(Level.INFO, "***************************************************");

        }
        }
    }

}
