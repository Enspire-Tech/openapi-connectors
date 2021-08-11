package com.boomi.connector.quickbase;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;

public class CustomConnection extends OpenAPIConnection {

    public CustomConnection(BrowseContext context) {
        super(context);
    }

    @Override
    public String getSpec() {
        return "custom-specification-quickbase.yaml";
    }
}