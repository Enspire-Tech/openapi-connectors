package com.boomi.connector.abuseipdb;

import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }

    public boolean getPreemptive() {
        return true;
    }

}
