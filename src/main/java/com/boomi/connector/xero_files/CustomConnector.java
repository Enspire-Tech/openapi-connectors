package com.boomi.connector.xero_files;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.Browser;
import com.boomi.connector.api.Operation;
import com.boomi.connector.api.OperationContext;
import com.boomi.connector.util.BaseConnector;

public class  CustomConnector extends BaseConnector {

    public Browser createBrowser(BrowseContext browseContext) {
        return new CustomBrowser(new CustomConnection(browseContext));
    }

    public Operation createExecuteOperation(final OperationContext operationContext){
        return new CustomOperation(new CustomOperationConnection(operationContext));
    }
}
