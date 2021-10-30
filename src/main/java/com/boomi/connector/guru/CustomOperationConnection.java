package com.boomi.connector.guru;

import com.boomi.common.rest.authentication.AuthenticationType;
import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;

import java.net.MalformedURLException;
import java.net.URL;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }

    @Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.BASIC;
    }

    @Override
    public URL getUrl() {
        try {
            return new URL("https://api.getguru.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
