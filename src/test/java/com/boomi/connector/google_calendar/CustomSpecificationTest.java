package com.boomi.connector.google_calendar;

import com.boomi.util.LogUtil;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.logging.Logger;

public class CustomSpecificationTest {

    private static final Logger LOG = LogUtil.getLogger(CustomSpecificationTest.class);

    private final String SPEC = "https://raw.githubusercontent.com/APIs-guru/openapi-directory/main/APIs/googleapis.com/calendar/v3/openapi.yaml";


    @Test
    public void testForPathLevelParameters() {
        SwaggerParseResult result = new OpenAPIParser().readLocation(SPEC, null, null);
        OpenAPI openAPI = result.getOpenAPI();

        if (result.getMessages() != null) result.getMessages().forEach(System.err::println); // validation errors and warnings

        if (openAPI != null) {
            for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
                PathItem pathItem = entry.getValue();
                if (pathItem.getParameters() != null && pathItem.getParameters().size() > 0) {
                    String message = String.format(
                            "*WARNING* Path level parameters for path: %s.",
                            entry.getKey());
                    System.err.println(message);

                }
            }
        } else {
            Assert.fail("Couldn't parse API");
        }
    }

    @Test
    public void testForResponseRanges() {
        SwaggerParseResult result = new OpenAPIParser().readLocation(SPEC, null, null);
        OpenAPI openAPI = result.getOpenAPI();

        if (result.getMessages() != null) result.getMessages().forEach(System.err::println); // validation errors and warnings

        if (openAPI != null) {
            for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
                PathItem pathItem = entry.getValue();
                for (Operation operation : pathItem.readOperationsMap().values()) {
                    for (String responseCode : operation.getResponses().keySet()) {
                        if (!responseCode.matches("^[0-9]+$")) {
                            String message = String.format(
                                    "*ERROR* Non numeric response code for path: %s.",
                                    entry.getKey());
                            System.err.println(message);
                        }
                    }
                }
            }
        } else {
            Assert.fail("Couldn't parse API");
        }
    }

}
