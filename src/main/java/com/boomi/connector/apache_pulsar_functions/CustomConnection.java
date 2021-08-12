package com.boomi.connector.apache_pulsar_functions;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;

public class CustomConnection extends OpenAPIConnection {

    public CustomConnection(BrowseContext context) {
        super(context);
    }

    @Override
    public String getSpec() {
        return "custom-specification-apache_pulsar_functions.yaml";
    }
}