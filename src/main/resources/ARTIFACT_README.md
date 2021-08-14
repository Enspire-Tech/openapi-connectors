# Boomi OpenAPI Connectors
These are branded connectors built for the Boomi platform with the Boomi OpenAPI SDK. 
You can find each connector's artifacts in their subdirectory.
See below for outstanding issues.
* [PagerDuty](#pagerduty)
  
* [Twitter v2](#twitterv2)

* [CircleCI](#circleci)

* [Firecracker](#firecracker)

* [Quickbase](#quickbase)

* [Apache Pulsar Functions](#apache_pulsar_functions)

* [Aquarius](#aquarius)


---
<a name="pagerduty"></a>
## PagerDuty

Documentation: https://developer.pagerduty.com/api-reference/

Specification: https://raw.githubusercontent.com/PagerDuty/api-schema/main/reference/REST/openapiv3.json

**10 out of 193 endpoints are failing.**

The following operations are not supported at this time:
* listAuditRecords
* listEscalationPolicyAuditRecords
* listSchedulesAuditRecords 
* listServiceAuditRecords
* listTeamsAuditRecords
* listUsersAuditRecords
* createScheduleOverride 
* getServiceIntegration 
* createServiceIntegration 
* updateServiceIntegration


### Issues
1. Property type is null. 
    + Schema references to parameter components are not being processed.
    + Affected operations: 
        * listAuditRecords
        * listEscalationPolicyAuditRecords
        * listSchedulesAuditRecords
        * listServiceAuditRecords
        * listTeamsAuditRecords
        * listUsersAuditRecords

2. Unsupported type: ARRAY
    + Responses with a schema type of array are not supported.
    + Affected operations:
        * crateScheduleOverride
    
3. Stackoverflow error
    + Circular references are causing stack overflow errors.
    + Affecting operations:
        * getServiceIntegration
        * createServiceIntegration
        * updateServiceIntegration
    

---   

<a name="twitterv2"></a>
## Twitter API v2

Documentation: https://developer.twitter.com/en/docs/twitter-api/early-access

Specification: https://api.twitter.com/labs/2/openapi.json

**4 out of 7 endpoints are failing.**

The following operations are not supported at this time:
* getTweetById
* getUserById
* getUserByUsername
* getOpenAPISpec

### Issues
1. Unsupported Parameter Type: null
   + References inside of parameter schemas are not being resolved.
   + Affected operations:
      * getTweetById
      * getUserById
      * getUserByUsername
2. Unsupported type: STRING
   + In the response, the schema type is "string".
   + Affected operation:
      * getOpenAPISpec   
   
---   

<a name="circleci"></a>
## CircleCI

Documentation: https://circleci.com/docs/api/v2/  

**6 out of 41 endpoints are failing.**

The following operations are not supported at this time:
* getCollaborations
* getTests
* getJobDetails
* getPipelineByNumber
* getJobDetails
* getCollaborations   

### Issues
1. Unsupported Parameter Type: null
   + The path parameter has no type. It contains an empty schema. ***"schema": {}***
   + Affected operations:
      * getCollaborations
      * getTests
      * getJobDetails
      * getPipelineByNumber
      * getJobDetails
2. Unsupported type: ARRAY
    + The response content type is array.
    + Affected operations:
        * getCollaborations

---   

<a name="firecracker"></a>
## Firecracker

Documentation: https://github.com/firecracker-microvm/firecracker

Specification: https://raw.githubusercontent.com/firecracker-microvm/firecracker/main/src/api_server/swagger/firecracker.yaml

**All 26 endpoints are passing.**

---   

<a name="quickbase"></a>
## Quickbase

Documentation: https://developer.quickbase.com/

**9 out of 35 endpoints failing.**

The following operations are not supported at this time:
* getAppEvents
* getAppTables
* getTableReports
* getFields
* getFieldsUsage
* getFieldUsage
* deleteApp
* deleteFields
* deleteRecords

### Issues
1. Unsupported type: ARRAY
    + The response content type is array, which is not a supported response type.
    + Affected operations:
      * getAppEvents
      * getAppTables
      * getTableReports
      * getFields
      * getFieldsUsage
      * getFieldUsage

2. DELETE requests should not return a request body.
    + According to OpenAPI specifications, DELETE requests should not include a request body.
    + Affected operations:
      * deleteApp
      * deleteFields
      * deleteRecords

---   

<a name="apache_pulsar_functions"></a>
## Apache Pulsar Functions
Documentation: https://pulsar.apache.org/functions-rest-api/

**5 out of 19 endpoints are failing.**

The following operations are not supported at this time:
* getConnectorsList
* listFunctions
* registerFunction
* triggerFunction
* updateFunction

### Issues
1. Unsupported type: ARRAY
   + The response content type is array, which is not a supported response type.
   + Affected operations:
      * getConnectorsList
2. Unsupported type: STRING
   + The response content type is string, which is not a supported response type.
   + Affected operations:
      * listFunctions
3. Schema can't be null
   + The request body has a content type of form-data. Only JSON request bodies are supported.
   + Affected operations:
     * registerFunction
     * triggerFunction
     * updateFunction

---   

<a name="aquarius"></a>
## Aquarius

Documentation: https://docs.oceanprotocol.com/references/aquarius/

** 2 out of 11 endpoints are failing.**

The following operations are not supported at this time:
* /api/v1/aquarius/assets/ddo/encrypt
* /api/v1/aquarius/assets/ddo/encryptashex

### Issues
1. Schema can't be nul
    + The request body has a content type of octet-stream. Only JSON request bodies are supported.
    + Affected operations:
      * /api/v1/aquarius/assets/ddo/encrypt
      * /api/v1/aquarius/assets/ddo/encryptashex

---

