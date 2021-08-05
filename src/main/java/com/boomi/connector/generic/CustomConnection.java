package com.boomi.connector.generic;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.ContentType;
import com.boomi.connector.api.ObjectDefinition;
import com.boomi.connector.openapi.OpenAPIConnection;
import com.boomi.util.LogUtil;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.logging.Logger;
import java.util.regex.Pattern;

public class CustomConnection extends OpenAPIConnection {
    private static final String PROPERTY_SPEC = "spec";
    private static final Pattern SUCCESS_PATTERN = Pattern.compile("2\\d\\d");
    private static final ObjectDefinition NO_CONTENT =
            new ObjectDefinition().withInputType(ContentType.NONE).withOutputType(ContentType.NONE);
    private static final ObjectDefinition UNSTRUCTURED_CONTENT =
            new ObjectDefinition().withInputType(ContentType.BINARY).withOutputType(ContentType.BINARY);
    private static final Logger LOG = LogUtil.getLogger(OpenAPIConnection.class);

    private OpenAPI _api;

    public CustomConnection(BrowseContext context) {
        super(context);
    }

//    @Override
//    public ObjectDefinitions getObjectDefinitions(String path, Collection<ObjectDefinitionRole> roles) {
//        ObjectDefinitions def = new ObjectDefinitions();
//        io.swagger.v3.oas.models.Operation operation = getOperation(path);
//
//        OpenAPISpecContext specContext = OpenApiUtil.getOpenAPISpecContext(getApi(),operation);
//        def.getOperationFields().addAll(specContext.getBrowseFieldByType(OpenApiParameterType.PATH));
//        def.getOperationFields().addAll(specContext.getBrowseFieldByType(OpenApiParameterType.HEADER));
//        def.getOperationFields().addAll(specContext.getBrowseFieldByType(OpenApiParameterType.QUERY));
//
//        for (ObjectDefinitionRole role : roles) {
//            switch (role) {
//                case INPUT:
//                    ObjectDefinition inputDefinition = getInputDefinition(operation).withCookie(
//                            specContext.getCookie().toString());
//                    def.getDefinitions().add(inputDefinition);
//                    break;
//                case OUTPUT:
//                    def.getDefinitions().add(getOutputDefinition(operation));
//                    break;
//                default:
//                    throw new IllegalArgumentException("invalid role: " + role);
//            }
//        }
//        return def;
//    }
//
//    private ObjectDefinition getInputDefinition(io.swagger.v3.oas.models.Operation operation) {
//        if (operation.getRequestBody() == null) {
//            return NO_CONTENT;
//        }
//        if (!HttpRequestUtil.isRequestPayloadSupported(getHttpMethod())) {
//            LOG.warning("The OpenAPI specification should not return a request body for the " + getHttpMethod()
//                    + " request. "
//                    + "GET, HEAD, and DELETE requests cannot have a request body. See RFC-7231 for more information.");
//            return NO_CONTENT;
//        }
//        Schema<?> requestSchema = OpenApiUtil.getSchema(getApi(), operation.getRequestBody());
//        JsonNode schema = OpenApiUtil.buildSchema(requestSchema, getApi());
//        if (schema.isMissingNode()) {
//            // TODO distinction between no content and binary
//            return UNSTRUCTURED_CONTENT;
//        }
//        return JSONUtil.newJsonDefinition(ObjectDefinitionRole.INPUT, schema);
//    }
//
//    private ObjectDefinition getOutputDefinition(Operation operation) {
//        Content content = OpenApiUtil.getContent(operation, SUCCESS_PATTERN);
//        // null is sufficient for v3 but not v2?
//        if (CollectionUtil.isEmpty(content)) {
//            return NO_CONTENT;
//        }
//        if (!OpenApiUtil.isStructured(content)) {
//            return UNSTRUCTURED_CONTENT;
//        }
//        JsonNode schema = OpenApiUtil.buildSchema( OpenApiUtil.getSchema(content), getApi());
//        return JSONUtil.newJsonDefinition(ObjectDefinitionRole.OUTPUT, schema);
//    }
//
//    private synchronized OpenAPI getApi() {
//        if (_api == null) {
//            String spec = getSpec();
//            if (StringUtil.isBlank(spec)) {
//                throw new IllegalArgumentException("spec is required");
//            }
//            //TODO: replace Collections.EMPTY_LIST with  AuthorizationValue for spec based auth
//            StringBuilder errorBuilder = new StringBuilder();
//            errorBuilder.append("Unable to parse spec: " + spec).append("\n");
//            ParseOptions parseOptions = new ParseOptions();
//            SwaggerParseResult result = new OpenAPIParser().readLocation(spec, null, parseOptions);
//            if (result.getOpenAPI() != null) {
//                _api = result.getOpenAPI();
//            }
//
//            if (_api == null) {
//                throw new ConnectorException("Unable to load Open API specification due to: " + errorBuilder.toString());
//            }
//        }
//        return _api;
//    }
}