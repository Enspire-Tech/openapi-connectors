package com.boomi.connector.twitter_v2;

import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    private static final String CUSTOM_AUTH_CREDENTIALS_PROPERTY = "customAuthCredentials";

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }

    @Override
    public String getCustomAuthCredentials() {
        String bearerToken = getContext().getConnectionProperties().getProperty(CUSTOM_AUTH_CREDENTIALS_PROPERTY);
        return "Bearer " + bearerToken;
    }

}
