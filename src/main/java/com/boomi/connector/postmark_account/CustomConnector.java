package com.boomi.connector.postmark_account;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.Browser;
import com.boomi.connector.api.Operation;
import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIBrowser;
import com.boomi.connector.util.BaseConnector;


public class  CustomConnector extends BaseConnector {

    public Browser createBrowser(BrowseContext browseContext) {
        return new OpenAPIBrowser(new CustomConnection(browseContext));
    }

    public Operation createExecuteOperation(final OperationContext operationContext){
        return new CustomOperation(new CustomOperationConnection(operationContext));
    }
}
