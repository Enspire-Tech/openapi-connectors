package com.boomi.connector.hubspot_events;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;
import org.apache.commons.lang3.StringUtils;

public class CustomConnection extends OpenAPIConnection {

    private static final String PROPERTY_SPEC  = "spec";
    private static final String CUSTOM_SPEC = "custom-specification-hubspot_events.yaml";

    public CustomConnection(BrowseContext context) {
        super(context);
    }

    @Override
    public String getSpec() {

        String spec = getContext().getConnectionProperties().getProperty(PROPERTY_SPEC);
        if (StringUtils.isBlank(spec)) {
            spec = CUSTOM_SPEC;
        }
        return spec;
    }
}
