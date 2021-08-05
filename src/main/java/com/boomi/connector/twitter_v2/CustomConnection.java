package com.boomi.connector.twitter_v2;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;

public class CustomConnection extends OpenAPIConnection {

    public CustomConnection(BrowseContext context) {
        super(context);
    }
}