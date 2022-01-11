package com.boomi.connector.guru;

import com.boomi.connector.api.ObjectData;
import com.boomi.connector.openapi.OpenAPIOperation;
import com.boomi.connector.openapi.OpenAPIOperationConnection;
import com.boomi.util.CollectionUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CustomOperation extends OpenAPIOperation {

    protected CustomOperation(CustomOperationConnection connection) {
        super(connection);
    }
}
