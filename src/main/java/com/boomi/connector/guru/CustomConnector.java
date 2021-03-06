package com.boomi.connector.guru;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.Browser;
import com.boomi.connector.api.Operation;
import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIBrowser;
import com.boomi.connector.openapi.OpenAPIOperation;
import com.boomi.connector.openapi.OpenAPIOperationConnection;
import com.boomi.connector.util.BaseConnector;


public class  CustomConnector extends BaseConnector {

    public Browser createBrowser(BrowseContext browseContext) {
        return new CustomBrowser(new CustomConnection(browseContext));
    }

    public Operation createExecuteOperation(final OperationContext operationContext){
        CustomOperationConnection operationConnection = new CustomOperationConnection(operationContext);
        return new CustomOperation(operationConnection);
    }
}