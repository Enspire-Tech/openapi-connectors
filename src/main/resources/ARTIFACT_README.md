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

* [Provider](#provider)

* [Blackboard](#blackboard)

* [Docusign Esignature](#docusign_esignature)

* [Docusign Click](#docusign_click)

* [Docusign Rooms](#docusign_rooms)

##Summary of Issues
<table>
   <tr><th>Error</th><th>Explanation</th><th>Example</th></tr>
   <tr>
      <td>Property value is null</td>
      <td>Schema references to parameter components are not resolved.</td>
      <td>
         API: Pagerduty, OperationId: listAuditRecords<br>
         <pre>"$ref": "#/components/parameters/audit_method_type/schema"</pre>
      </td>
   </tr>
   <tr>
      <td>Unsupported type: ARRAY</td>
      <td>Responses with a schema type of array are not supported.</td>
      <td>
         API: Pagerduty, OperationId: createScheduleOverride<br>
         <pre>
"responses": {
   "201": {
      "description": "A list of overrides requested and a status code indicating whether they were created or rejected",
         "content": {
            "application/json": {
                "schema": {
                  "type": "array",
         </pre>
      </td>
   </tr>
   <tr>
      <td>Stackoverflow error</td>
      <td>Circular references are causing stack overflow errors.</td>
      <td>
         API: Pagerduty, OperationId: getServiceIntegration<br>
         <pre>
"MatchPredicate": {
  "type": "object",
  "properties": {
    "type": {
      "type": "string",
      "enum": [
        "all",
        "any",
        "not",
        "contains",
        "exactly",
        "regex"
      ]
    },
    "matcher": {
      "type": "string",
      "description": "Required if the type is `contains`, `exactly` or `regex`.",
      "minLength": 1
    },
    "part": {
      "type": "string",
      "description": "The email field that will attempt to use the matcher expression. Required if the type is `contains`, `exactly` or `regex`.",
      "enum": [
        "body",
        "subject",
        "from_addresses"
      ]
    },
    "children": {
      "type": "array",
      "description": "Additional matchers to be run. Must be not empty if the type is `all`, `any`, or `not`.",
      "items": {
        "$ref": "#/components/schemas/MatchPredicate"
      }
         </pre>
      </td>
   </tr>
   <tr>
      <td>Unsupported Parameter Type: null</td>
      <td>References are not followed when validating parameter schema type.</td>
      <td>
         API: Twitter V2, OperationId: findTweetById<br>
         <pre>
"parameters" : [ {
 "name" : "id",
 "in" : "path",
 "description" : "A single Tweet ID.",
 "required" : true,
 "schema" : {
   "$ref" : "#/components/schemas/TweetID"
 }
         </pre>
      </td>
   </tr>
   <tr>
      <td>Unsupported type: STRING</td>
      <td>In the response, the JSON schema type is "string".</td>
      <td>
         API: Twitter V2, OperationId: getOpenApiSpec<br>
         <pre>
"responses" : {
 "200" : {
   "description" : "The request was successful",
   "content" : {
     "application/json" : {
       "schema" : {
         "type" : "string"
         </pre>
      </td>
   </tr>
   <tr>
      <td>Unsupported Parameter Type: null</td>
      <td>The path parameter has no type. It contains an empty schema.</td>
      <td>
         API: CircleCI, OperationId: getTests<br>
         <pre>
"parameters": [
 {
   "in": "path",
   "name": "job-number",
   "description": "The number of the job.",
   "schema": {},
   "required": true,
   "example": "123"
 },
         </pre>
      </td>
   </tr>
   <tr>
      <td>DELETE requests should not return a request body</td>
      <td>According to OpenAPI specifications, GET, DELETE, and HEAD are no longer allowed to have a request body.</td>
      <td>
         API: Quickbase, OperationId: deleteApp<br>
         <pre>
 delete:
   tags:
     - Apps
   summary: Delete an app
   description: Deletes an entire application, including all of the tables and
     data.
   operationId: deleteApp
   requestBody:
     content:
       'application/json':
         schema:
           required:
             - name
           type: object
           properties:
             name:
               type: string
               description: To confirm application deletion we ask for application
                 name.
           example:
             name: Name of an application to delete
     required: false
         </pre>
      </td>
   </tr>
   <tr>
      <td>Schema can't be null</td>
      <td>The request body has a content type of form-data. Only JSON request bodies are supported.</td>
      <td>
         API: Apache Pulsar Functions, OperationId: registerFunction
         <pre>
requestBody:
  content:
    multipart/form-data:
      schema:
        $ref: '#/components/schemas/FunctionConfig'
  required: false
         </pre>
      </td>
   </tr>
</table>


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
1. Property value is null. 
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
        * createScheduleOverride
    
3. Stackoverflow error
    + Circular references are causing stack overflow errors.
    + Affected operations:
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
* findTweetById
* findUserById
* findUserByUsername
* getOpenAPISpec

### Issues
1. Unsupported Parameter Type: null
   + References inside of parameter schemas are not being resolved.
   + Affected operations:
      * findTweetById
      * findUserById
      * findUserByUsername
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
* getJobArtifacts

### Issues
1. Unsupported Parameter Type: null
   + The path parameter has no type. It contains an empty schema. ***"schema": {}***
   + Affected operations:
      * getJobArtifacts
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

**2 out of 11 endpoints are failing.**

The following operations are not supported at this time:
* /api/v1/aquarius/assets/ddo/encrypt
* /api/v1/aquarius/assets/ddo/encryptashex

### Issues
1. Schema can't be null
    + The request body has a content type of octet-stream. Only JSON request bodies are supported.
    + Affected operations:
      * /api/v1/aquarius/assets/ddo/encrypt
      * /api/v1/aquarius/assets/ddo/encryptashex

---

<a name="provider"></a>
## Provider
Documentation: https://github.com/oceanprotocol/provider/blob/main/API.md

**2 out of 7 endpoints are failing.**

The following operations are not supported at this time:
* /api/v1/services/compute POST
* /api/v1/services/download GET

### Issues
1. Unsupported Parameter Type: null
    + The query parameter has no type. It contains an empty schema. ***"schema": {}***
    + Affected operations:
      * /api/v1/services/compute POST
      * /api/v1/services/download GET

--- 

<a name="blackboard"></a>
## Blackboard

Documentation: https://developer.blackboard.com/portal/displayApi

**All 248 endpoints are passing.**

---

<a name="docusign_esignature"></a>
## Docusign Esignature v2.1

Documentation: https://developers.docusign.com/docs/esign-rest-api

### Implementation Notes
1. If using an OAuth 2.0 Authorization Code grant type, the "Preemptive authentication" option must be checked on.
2. If using an OAuth 2.0 JWT Bearer Token grant type:
   1. The public and private key needs to be imported to Boomi as an X509 certificate.
   2. The expiration field requires an "expires in" value in seconds. Usually that should be 3600.
   3. The key "scope" with value "signature impersonation" must be added as an "Extended JWT Claim". 

**51 out of 402 endpoints are failing.**

The following operations are not supported at this time:
* Envelopes_PostEnvelopes
* Recipients_PostRecipients
* EnvelopeTransferRules_PostEnvelopeTransferRules
* PowerForms_PostPowerForm
* Templates_PostTemplates
* Recipients_PostTemplateRecipients
* BrandLogo_PutBrandLogo
* BrandResources_PutBrandResources
* Envelopes_PutEnvelope
* Documents_PutDocuments
* Recipients_PutRecipients
* EnvelopeTransferRules_PutEnvelopeTransferRules
* EnvelopeTransferRules_PutEnvelopeTransferRule
* Folders_PutFolderById
* PowerForms_PutPowerForm
* Templates_PutTemplate
* Documents_PutTemplateDocuments
* Documents_PutTemplateDocument
* Recipients_PutTemplateRecipients
* Brands_DeleteBrands
* CaptiveRecipients_DeleteCaptiveRecipientsPart
* Contacts_DeleteContacts
* Attachments_DeleteAttachments
* CustomFields_DeleteCustomFields
* Documents_DeleteDocuments
* DocumentFields_DeleteDocumentFields
* Tabs_DeleteDocumentTabs
* Recipients_DeleteRecipients
* Recipients_DeleteRecipientTabs
* Views_DeleteEnvelopeCorrectView
* FavoriteTemplates_UnFavoriteTemplate
* Groups_DeleteGroups
* Brands_DeleteGroupBrands
* Groups_DeleteGroupUsers
* PowerForms_DeletePowerFormsList
* SigningGroups_DeleteSigningGroups
* SigningGroups_DeleteSigningGroupUsers
* Templates_DeleteTemplatePart
* CustomFields_DeleteTemplateCustomFields
* Documents_DeleteTemplateDocuments
* DocumentFields_DeleteTemplateDocumentFields
* Pages_DeleteTemplatePage
* Tabs_DeleteTemplateDocumentTabs
* Lock_DeleteTemplateLock
* Recipients_DeleteTemplateRecipients
* Recipients_DeleteTemplateRecipient
* Recipients_DeleteTemplateRecipientTabs
* Users_DeleteUsers
* CloudStorage_DeleteCloudStorageProviders
* UserCustomSettings_DeleteCustomSettings
* WorkspaceFolder_DeleteWorkspaceItems

### Issues

1. Property value is null
    + "offlineAttributes" reference not defined in specification.
    + Affected operations:
      * Envelopes_PostEnvelopes
      * Recipients_PostRecipients
      * EnvelopeTransferRules_PostEnvelopeTransferRules
      * PowerForms_PostPowerForm
      * Templates_PostTemplates
      * Recipients_PostTemplateRecipients
      * Envelopes_PutEnvelope
      * Documents_PutDocuments
      * Recipients_PutRecipients
      * EnvelopeTransferRules_PutEnvelopeTransferRules
      * EnvelopeTransferRules_PutEnvelopeTransferRule
      * Folders_PutFolderById
      * PowerForms_PutPowerForm
      * Templates_PutTemplate
      * Documents_PutTemplateDocuments
      * Documents_PutTemplateDocument
      * Recipients_PutTemplateRecipients
2. Schema can't be null
    + Response body is a binary type. Boomi only supports JSON response bodies at this time.
    + Affected operations:
      * BrandLogo_PutBrandLogo
      * BrandResources_PutBrandResources
3. DELETE requests should not return a request body.
    + According to OpenAPI specifications, DELETE requests should not include a request body.
    + Affected operations:
        * Brands_DeleteBrands
        * CaptiveRecipients_DeleteCaptiveRecipientsPart
        * Contacts_DeleteContacts
        * Attachments_DeleteAttachments
        * CustomFields_DeleteCustomFields
        * Documents_DeleteDocuments
        * DocumentFields_DeleteDocumentFields
        * Tabs_DeleteDocumentTabs
        * Recipients_DeleteRecipients
        * Recipients_DeleteRecipientTabs
        * Views_DeleteEnvelopeCorrectView
        * FavoriteTemplates_UnFavoriteTemplate
        * Groups_DeleteGroups
        * Brands_DeleteGroupBrands
        * Groups_DeleteGroupUsers
        * PowerForms_DeletePowerFormsList
        * SigningGroups_DeleteSigningGroups
        * SigningGroups_DeleteSigningGroupUsers
        * Templates_DeleteTemplatePart
        * CustomFields_DeleteTemplateCustomFields
        * Documents_DeleteTemplateDocuments
        * DocumentFields_DeleteTemplateDocumentFields
        * Pages_DeleteTemplatePage
        * Tabs_DeleteTemplateDocumentTabs
        * Lock_DeleteTemplateLock
        * Recipients_DeleteTemplateRecipients
        * Recipients_DeleteTemplateRecipient
        * Recipients_DeleteTemplateRecipientTabs
        * Users_DeleteUsers
        * CloudStorage_DeleteCloudStorageProviders
        * UserCustomSettings_DeleteCustomSettings
        * WorkspaceFolder_DeleteWorkspaceItems
---

<a name="docusign_click"></a>
## Docusign Click v2

Documentation: https://developers.docusign.com/docs/click-api/click101

### Implementation Notes
1. If using an OAuth 2.0 Authorization Code grant type, the "Preemptive authentication" option must be checked on.
2. If using an OAuth 2.0 JWT Bearer Token grant type:
   1. The public and private key needs to be imported to Boomi as an X509 certificate.
   2. The expiration field requires an "expires in" value in seconds. Usually that should be 3600.
   3. The key "scope" with value "signature impersonation" must be added as an "Extended JWT Claim".

**All 21 endpoints are passing.**

---

<a name="docusign_rooms"></a>
## Docusign Rooms v2

Documentation: https://developers.docusign.com/docs/rooms-api/rooms101

### Implementation Notes
1. If using an OAuth 2.0 Authorization Code grant type, the "Preemptive authentication" option must be checked on.
2. If using an OAuth 2.0 JWT Bearer Token grant type:
   1. The public and private key needs to be imported to Boomi as an X509 certificate.
   2. The expiration field requires an "expires in" value in seconds. Usually that should be 3600.
   3. The key "scope" with value "signature impersonation" must be added as an "Extended JWT Claim".

**4 out of 89 endpoints are failing.**

The following operations are not supported at this time:
* Fields_GetFieldSet
* Rooms_GetRoomFieldSet
* Rooms_AddDocumentToRoomViaFileUpload
* Rooms_UpdatePicture

### Issues
1. Stackoverflow error
   + Circular references are causing stack overflow errors.
   + Affected operations:
     * Fields_GetFieldSet
     * Rooms_GetRoomFieldSet
2. Schema can't be null
   + The request body has a content type of form-data. Only JSON request bodies are supported.
   + Affected operations:
     * Rooms_AddDocumentToRoomViaFileUpload
     * Rooms_UpdatePicture
     
---