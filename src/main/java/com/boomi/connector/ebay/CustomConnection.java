package com.boomi.connector.ebay;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.ConnectorException;
import com.boomi.connector.api.PropertyMap;
import com.boomi.connector.openapi.OpenAPIConnection;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;

public class CustomConnection extends OpenAPIConnection {

    private static final String PROPERTY_SPEC  = "spec";

    public CustomConnection(BrowseContext context) {
        super(context);
    }

    @Override
    public String getSpec() {
        CustomContext customContext = new CustomContext(getContext());
        return customContext.getSpec();
    }
}
