package com.boomi.connector.pipedrive;

import com.boomi.connector.api.ObjectData;
import com.boomi.connector.openapi.OpenAPIOperation;
import com.boomi.util.CollectionUtil;
import com.boomi.util.StringUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CustomOperation extends OpenAPIOperation {

    private static final String CUSTOM_HEADERS_PROPERTY = "customHeaders";

    protected CustomOperation(CustomOperationConnection connection) {
        super(connection);
    }

    protected Iterable<Map.Entry<String, String>> getParameters(ObjectData data) {
        Iterable<Map.Entry<String, String>> originalParameters = super.getParameters(data);
        Iterator<Map.Entry<String ,String>> originalParameterIterator = originalParameters.iterator();

        ArrayList<Map.Entry<String, String>> parameterList = new ArrayList<>();

        //add api key query
        String apiKey = getContext().getConnectionProperties().getProperty("apiKey");
        if (StringUtil.isNotBlank(apiKey)) {
            parameterList.add(new AbstractMap.SimpleEntry<>("Authorization","Api-Key " + apiKey));
        }

        //add original parameters
        while (originalParameterIterator.hasNext()) {
            parameterList.add(originalParameterIterator.next());
        }

        return parameterList;
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

        //add api key header
        String apiKey = getContext().getConnectionProperties().getProperty("apiKey");
        if (StringUtil.isNotBlank(apiKey)) {
            headerList.add(new AbstractMap.SimpleEntry<>("Authorization","Api-Key " + apiKey));
        }

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
