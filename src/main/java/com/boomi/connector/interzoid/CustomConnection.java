package com.boomi.connector.interzoid;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;
import org.apache.commons.lang3.StringUtils;

public class CustomConnection extends OpenAPIConnection {

    public CustomConnection(BrowseContext context) {
        super(context);
    }
}
