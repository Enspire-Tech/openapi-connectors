package com.boomi.connector.amazon_machine_learning;

import com.boomi.connector.api.ObjectData;
import com.boomi.connector.openapi.OpenAPIOperation;
import com.boomi.util.CollectionUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CustomOperation extends OpenAPIOperation {

    private static final String CUSTOM_HEADERS_PROPERTY = "customHeaders";
    private static final String CONTENT_TYPE = "application/x-amz-json-1.1";

    protected CustomOperation(CustomOperationConnection connection) {
        super(connection);
    }

    /**
     * Adds custom headers from Connection page.
     *
     * @return Key value pairs of all headers in the request
     */
    @Override
    protected Iterable<Map.Entry<String, String>> getHeaders(ObjectData data) {
        Iterable<Map.Entry<String, String>> originalHeaders = super.getHeaders(data);


        Map<String, String> customHeaders = getConnection().getContext().getConnectionProperties().getCustomProperties(CUSTOM_HEADERS_PROPERTY);
        Iterator<Map.Entry<String ,String>> originalHeaderIterator = originalHeaders.iterator();
        ArrayList<Map.Entry<String, String>> headerList = new ArrayList<>();

        headerList.add(new AbstractMap.SimpleEntry<>("Content-Type", CONTENT_TYPE));

        //add other custom headers
        while (originalHeaderIterator.hasNext()) {
            headerList.add(originalHeaderIterator.next());
        }
        if (!CollectionUtil.isEmpty(customHeaders)) {
            for (String key : customHeaders.keySet()) {
                headerList.add(new AbstractMap.SimpleEntry<>(key, customHeaders.get(key)));
            }
        }

        return headerList;
    }
}
