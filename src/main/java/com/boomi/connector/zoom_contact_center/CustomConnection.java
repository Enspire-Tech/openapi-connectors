package com.boomi.connector.zoom_contact_center;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;
import org.apache.commons.lang3.StringUtils;

public class CustomConnection extends OpenAPIConnection {

    private static final String PROPERTY_SPEC  = "spec";
    private static final String CUSTOM_SPEC = "custom-specification-zoom_contact_center.json";

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
