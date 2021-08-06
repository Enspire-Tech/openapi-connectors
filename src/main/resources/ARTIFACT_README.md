# OpenAPI Connectors
These are branded connectors built for the Boomi platform with the Boomi OpenAPI SDK. 
You can find each connector's artifacts in their subdirectory.
See below for outstanding issues.
* [PagerDuty](#pagerduty)
  
* [Twitter v2](#twitterv2)

* [CircleCI](#circleci)
   

---
<a name="pagerduty"></a>
## PagerDuty

Documentation: https://developer.pagerduty.com/api-reference/

Specification: https://raw.githubusercontent.com/PagerDuty/api-schema/main/reference/REST/openapiv3.json

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
    

      
   

