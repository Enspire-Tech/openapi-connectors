package com.boomi.connector.guru;

import com.boomi.common.rest.authentication.AuthenticationType;
import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class CustomConnection extends OpenAPIConnection {

    private static final String PROPERTY_SPEC  = "spec";
    private static final String CUSTOM_SPEC = "https://api.getguru.com/api/v1/openapi.json";

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
