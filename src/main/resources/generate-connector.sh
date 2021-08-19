#!/bin/bash

CONNECTOR_NAME=$1
SPEC_PATH=$2

parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
pushd $parent_path

#GENERATE SRC JAVA

mkdir -p ../java/com/boomi/connector/${CONNECTOR_NAME}

cat <<EOM >../java/com/boomi/connector/${CONNECTOR_NAME}/CustomConnection.java
package com.boomi.connector.${CONNECTOR_NAME};

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.openapi.OpenAPIConnection;
import org.apache.commons.lang3.StringUtils;

public class CustomConnection extends OpenAPIConnection {

    private static final String PROPERTY_SPEC  = "spec";
    private static final String CUSTOM_SPEC = "custom-specification-${CONNECTOR_NAME}.yaml";

    public CustomConnection(BrowseContext context) {
        super(context);
    }

    @Override
    public String getSpec() {

        String spec = getContext().getConnectionProperties().getProperty(PROPERTY_SPEC);
        if (StringUtils.isBlank(spec)) {
            spec = CUSTOM_SPEC;
        }
        return spec;
    }
}
EOM

cat <<EOM >../java/com/boomi/connector/${CONNECTOR_NAME}/CustomConnector.java
package com.boomi.connector.${CONNECTOR_NAME};

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
EOM

cat <<EOM >../java/com/boomi/connector/${CONNECTOR_NAME}/CustomOperation.java
package com.boomi.connector.${CONNECTOR_NAME};

import com.boomi.connector.api.ObjectData;
import com.boomi.connector.openapi.OpenAPIOperation;
import com.boomi.util.CollectionUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CustomOperation extends OpenAPIOperation {

    private static final String CUSTOM_HEADERS_PROPERTY = "customHeaders";

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
        ArrayList<Map.Entry<String, String>> headerList = new ArrayList<Map.Entry<String, String>>();

        //add other custom headers
        while (originalHeaderIterator.hasNext()) {
            headerList.add(originalHeaderIterator.next());
        }
        if (!CollectionUtil.isEmpty(customHeaders)) {
            for (String key : customHeaders.keySet()) {
                headerList.add(new AbstractMap.SimpleEntry<>(key, customHeaders.get(key)));
            }
        }
        Iterable<Map.Entry<String, String>> headers = headerList;
        return headers;
    }
}
EOM

cat <<EOM >../java/com/boomi/connector/${CONNECTOR_NAME}/CustomOperationConnection.java
package com.boomi.connector.${CONNECTOR_NAME};

import com.boomi.connector.api.OperationContext;
import com.boomi.connector.openapi.OpenAPIOperationConnection;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }
}
EOM


# GENERATE RESOURCES

mkdir -p ../resources/${CONNECTOR_NAME}

cat <<EOM >../resources/${CONNECTOR_NAME}/connector-config-${CONNECTOR_NAME}.xml
<?xml version="1.0" encoding="UTF-8"?>
<GenericConnector sdkApiVersion="2.10.0">
  <connectorClassName>com.boomi.connector.${CONNECTOR_NAME}.CustomConnector</connectorClassName>
</GenericConnector>
EOM

cat <<EOM >../resources/${CONNECTOR_NAME}/connector-descriptor-${CONNECTOR_NAME}.xml
<GenericConnectorDescriptor requireConnectionForBrowse="true" browsingType="any" >
    <description>This connector provides access to the **FILL ME**. For more information, click here: **FILL ME**</description>
    <field id="url" label="Server" type="string">
        <defaultValue></defaultValue>
    </field>

    <field id="spec" label="Override OpenAPI Spec" type="string">
        <helpText>This will override the custom specification included with the connector.</helpText>
    </field>


    <field type="customproperties" label="Request Headers" id="customHeaders" />
    <testConnection method="GET_OBJECT_TYPES" customOperationType="GET" />

    <operation customTypeId="GET" supportsBrowse="true" types="EXECUTE"/>
    <operation customTypeId="POST" supportsBrowse="true" types="EXECUTE"/>
    <operation customTypeId="PUT" supportsBrowse="true" types="EXECUTE"/>
    <operation customTypeId="DELETE" supportsBrowse="true" types="EXECUTE"/>


</GenericConnectorDescriptor>
EOM

if [ -n "$2" ]; then
  cat ${SPEC_PATH} >../resources/${CONNECTOR_NAME}/custom-specification-${CONNECTOR_NAME}.yaml
fi


# GENERATE TESTS

mkdir -p ../../test/java/com/boomi/connector/${CONNECTOR_NAME}

cat <<EOM >../../test/java/com/boomi/connector/${CONNECTOR_NAME}/CustomBrowserTest.java
package com.boomi.connector.${CONNECTOR_NAME};

import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.openapi.OpenAPIBrowser;
import com.boomi.connector.testutil.SimpleAtomConfig;
import com.boomi.connector.testutil.SimpleBrowseContext;
import com.boomi.util.LogUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomBrowserTest {

    private static final Logger LOG = LogUtil.getLogger(CustomBrowserTest.class);

    final String[] HTTP_METHODS = {
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "PATCH",
            "HEAD",
            "OPTIONS",
            "TRACE"
    };

    @Test
    public void testTypes() throws Exception {
        CustomConnector connector = new CustomConnector();

        Map<String, Object> connProps = new HashMap<String, Object>() {{}};

        for (String httpMethod : HTTP_METHODS) {
            SimpleBrowseContext browseContext = new SimpleBrowseContext(
                    new SimpleAtomConfig(),
                    connector,
                    null,
                    httpMethod,
                    connProps,
                    null
            );

            OpenAPIBrowser browser = (OpenAPIBrowser) connector.createBrowser(browseContext);
            try {
                ObjectTypes objectTypes = browser.getObjectTypes();
            } catch (Exception e) {
                if (e.getMessage() != null && e.getMessage().contains("no types available")) {
                    continue;
                } else {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    @Test
    public void testProfiles() throws Exception {

        int endPointCount = 0;
        int errorCount = 0;
        int stackoverflowCount = 0;
        int otherErrorCount = 0;

        CustomConnector connector = new CustomConnector();

        Map<String, Object> connProps = new HashMap<String, Object>() {{}};
        for (String httpMethod : HTTP_METHODS) {
            SimpleBrowseContext browseContext = new SimpleBrowseContext(
                    new SimpleAtomConfig(),
                    connector,
                    null,
                    httpMethod,
                    connProps,
                    null
            );

            OpenAPIBrowser browser = (OpenAPIBrowser) connector.createBrowser(browseContext);
            ObjectTypes objectTypes = null;
            try {
                objectTypes = browser.getObjectTypes();
            } catch (Exception e) {
                if (e.getMessage() != null && e.getMessage().contains("no types available")) {
                    continue;
                } else {
                    e.printStackTrace();
                    throw e;
                }
            }
            for (ObjectType objectType : objectTypes.getTypes()) {
                endPointCount++;
                String path = objectType.getId();
                String operationId = objectType.getLabel();
                Set<ObjectDefinitionRole> roles = new HashSet<ObjectDefinitionRole>();
                roles.add(ObjectDefinitionRole.INPUT);
                roles.add(ObjectDefinitionRole.OUTPUT);

                try {
                    try {
                        browser.getObjectDefinitions(path, roles);
                    } catch (StackOverflowError e) {
                        System.err.println("Stackoverflow error for operationId " + operationId);
                        errorCount++;
                        stackoverflowCount++;
                        continue;
                    }
                } catch (Exception e) {
                    String message = String.format(
                            "Browser failing for path: %s, http method: %s, operation id: %s \n error: %s",
                            path, httpMethod, operationId, e.getMessage());
                    System.err.println(message);
                    errorCount++;
                    otherErrorCount++;
                }
            }
        }
        LOG.log(Level.INFO, errorCount + " out of " + endPointCount + " endpoints failed.");
        LOG.log(Level.INFO, stackoverflowCount + " of those are stackoverflow errors.");
        LOG.log(Level.INFO, otherErrorCount + " of those are other errors.");
    }

}
EOM

cat <<EOM >../../test/java/com/boomi/connector/${CONNECTOR_NAME}/ValidateXMLTest.java
package com.boomi.connector.${CONNECTOR_NAME};

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ValidateXMLTest {

    @Test
    public void validateConnectorConfig() {
        String xml = "/connector-config-${CONNECTOR_NAME}.xml";
        String xsd = "/genericconnector.xsd";
        try {
            validateAgainstXSD(xml, xsd);
        } catch (Exception e) {
            Assert.fail("Exception: " + e);
        }
    }

    @Test
    public void validateConnectorDescriptor() {
        String xml = "/connector-descriptor-${CONNECTOR_NAME}.xml";
        String xsd = "/genericconnectordesc.xsd";
        try {
            validateAgainstXSD(xml, xsd);
        } catch (Exception e) {
            Assert.fail("Exception: " + e);
        }
    }

    void validateAgainstXSD(String xmlPath, String xsdPath) throws IOException, SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        InputStream xmlStream = this.getClass().getResourceAsStream(xmlPath);

        URL xsdUrl = this.getClass().getResource(xsdPath);

        Schema schema = factory.newSchema(xsdUrl);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xmlStream));

    }
}
EOM

popd

ECHO "Basic file generation complete. Please customize descriptor."



