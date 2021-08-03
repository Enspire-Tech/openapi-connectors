package com.boomi.connector.sage_x3_data_integration;

import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    private static final String CUSTOM_AUTH_CREDENTIALS_PROPERTY = "customAuthCredentials";

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }

    /**
     * Allows the user to enter only the API Key.
     *
     * @return the authorization header string
     */
    @Override
    public String getCustomAuthCredentials() {
        String apiKey = getContext().getConnectionProperties().getProperty(CUSTOM_AUTH_CREDENTIALS_PROPERTY);
        return "Token token=" + apiKey;
    }

}
