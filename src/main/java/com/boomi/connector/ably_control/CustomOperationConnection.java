package com.boomi.connector.ably_control;

import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;
import org.apache.commons.codec.binary.Base64;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    private static final String CUSTOM_AUTH_CREDENTIALS_PROPERTY = "customAuthCredentials";

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }

    @Override
    public String getCustomAuthCredentials() {
        String apiKey = getContext().getConnectionProperties().getProperty(CUSTOM_AUTH_CREDENTIALS_PROPERTY);
        return "Bearer " + apiKey;
    }
}