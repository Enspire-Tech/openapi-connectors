package com.boomi.connector.aws_amplify_backend;

import com.boomi.common.rest.authentication.AuthenticationType;
import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    private final String AUTHENTICATION_TYPE = "AWS";
    private final String AWS_SERVICE = "amplifybackend";

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }

    public boolean getPreemptive() {
        return true;
    }

    @Override
    public String getAWSService() {
        return AWS_SERVICE;
    }

    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.fromValue(AUTHENTICATION_TYPE);
    }
}
