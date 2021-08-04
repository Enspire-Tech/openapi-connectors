package com.boomi.connector.azure_data_lake_storage;

import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    private static final String ACCOUNT_NAME = "accountName";
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
        String accountName = getContext().getConnectionProperties().getProperty(CUSTOM_AUTH_CREDENTIALS_PROPERTY);
        String sharedKey = getContext().getConnectionProperties().getProperty(CUSTOM_AUTH_CREDENTIALS_PROPERTY);
        return String.format("SharedKey %s:%s", accountName, sharedKey);
    }
}
