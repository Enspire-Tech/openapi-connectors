package com.boomi.connector.quickbase;

import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }

    @Override
    public String getCustomAuthCredentials() {
        String apiKey = getContext().getConnectionProperties().getProperty("customAuthCredentials");
        return "QB-USER-TOKEN " + apiKey;
    }

}
