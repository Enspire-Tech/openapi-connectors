package com.boomi.connector.guru;

import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.openapi.OpenAPIBrowser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomBrowser extends OpenAPIBrowser {

    private String[] IGNORED_OPERATIONS = {
            "get:/v1/kcs/getKCSApps",
            "put:/v1/kcs/addKCSApp",
            "post:/v1/cards/verifiers/upsertVerifier"
    };

    public CustomBrowser (CustomConnection conn) {
        super(conn);
    }

    // Remove not working operations from browse
    public ObjectTypes getObjectTypes() {
        ObjectTypes objects = super.getObjectTypes();
        List<ObjectType> filteredObjects = new ArrayList<ObjectType>();
        for (ObjectType object : objects.getTypes()) {
            String objectId = object.getLabel();
            if (!Arrays.asList(IGNORED_OPERATIONS).contains(objectId)) {
                filteredObjects.add(object);
            }
        }
        return (new ObjectTypes()).withTypes(filteredObjects);
    }

}
