package com.boomi.connector.azure_data_lake_storage;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;

public class CustomConnection extends OpenAPIConnection {

    public CustomConnection(BrowseContext context) {
        super(context);
    }

    @Override
    public String getSpec() {
        return "specification-azure_data_lake_storage.yaml";
    }

}