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

* [Xero Assets](#xero_assets)

* [Xero Files](#xero_files)

* [Xero Projects](#xero_projects)

* [Xero Payroll NZ](#xero_payroll_nz)

* [Xero Payroll UK](#xero_payroll_uk)

* [Ably Control](#ably_control)

### Skipped APIs
* **Bitbucket** 
  * https://api.bitbucket.org/swagger.json
  * 160 out of 304 endpoints failing (as a 2.0 spec)
  * Issue converting to OAS3
* **Github** 
  * https://raw.githubusercontent.com/github/rest-api-description/main/descriptions/ghes-3.1/ghes-3.1.json
  * 171 out of 682 endpoints failing
  * Mostly "Unsupported type: ARRAY" errors
* **Insightly**
  * https://api.na1.insightly.com/v3.1/swagger/docs/v3.1
  * 184 out of 417 endpoints failing
  * Mostly "Unsupported type: ARRAY" errors
* **Amdocs MarketOne**
  * https://api.swaggerhub.com/apis/Amdocs_Media/MarketONE/2021.02.Released
  * 19 out of 39 endpoints failing
  * Mostly stackoverflow errors due to circular references
* **Apache Pulsar Admin**
  * https://pulsar.apache.org/swagger/master//swagger.json
  * 131 out of 413 endpoints failing
  * Various errors
* **Guru**
  * https://api.getguru.com/api/v1/swagger.json
  * 22 out of 54 endpoints failing
  * Mostly "Unsupported type: ARRAY" errors
* **Xero Accounting**
  * https://github.com/XeroAPI/Xero-OpenAPI/blob/master/xero_accounting.yaml
  * 83 out of 226 endpoints failing
  * Mostly stackoverflow errors due to circular references
* **Stripe**
  * https://github.com/stripe/openapi/blob/master/openapi/spec3.json
  * 302 out of 384 endpoints failing
  * Mostly stackoverflow errors due to circular references
* **Ably**
  * https://github.com/ably/open-specs/blob/main/definitions/platform-v1.yaml
  * 21 out of 22 endpoints failing
  * 20 endpoints failing due to the HTTP status code range issue
  

## Summary of Issues

<table>
   <tr><th>Priority</th></td><th>Error</th><th>Explanation</th><th>Endpoints Affected</th><th>Example</th></tr>
   <tr>
      <td>1</td>
      <td>Stackoverflow error</td>
      <td>Circular references are causing stack overflow errors.</td>
      <td>5</td>
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
      <td>1</td>
      <td>Unsupported type: ARRAY</td>
      <td>Responses with a schema type of array are not supported.</td>
      <td>16</td>
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
      <td>2</td>
      <td>Unsupported Parameter Type: null</td>
      <td>References are not followed when validating parameter schema type.</td>
      <td>5</td>
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
      <td>2</td>
      <td>Unsupported Parameter Type: null</td>
      <td>The path parameter has no type. It contains an empty schema.</td>
      <td>7</td>
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
      <td>2</td>
      <td>Schema can't be null</td>
      <td>The request body has a content type of form-data or octet-stream. Only JSON request bodies are supported.</td>
      <td>11</td>
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
   <tr>
      <td>2</td>
      <td>Property value is null</td>
      <td>Schema references to parameter components are not resolved.</td>
      <td>6</td>
      <td>
         API: Pagerduty, OperationId: listAuditRecords<br>
         <pre>"$ref": "#/components/parameters/audit_method_type/schema"</pre>
      </td>
   </tr>
   <tr>
      <td>3</td>
      <td>Unsupported type: STRING</td>
      <td>In the response, the JSON schema type is "string".</td>
      <td>2</td>
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
      <td>3</td>
      <td>DELETE requests should not return a request body</td>
      <td>According to OpenAPI specifications, GET, DELETE, and HEAD are no longer allowed to have a request body.</td>
      <td>35</td>
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
      <td>3</td>
      <td>Parameters at path level are ignored</td>
      <td>
         In the OpenAPI specification, parameters can be defined at the path level and apply to multiple HTTP methods.
      </td>
      <td>0</td>
      <td>
         API: Xero Assets, OperationId: getAssets
         <pre>
paths:
  /Assets:
    parameters:
      - $ref: '#/components/parameters/requiredHeader'
    get:
         </pre>
      </td>
   </tr>
    <tr>
        <td>3</td>
        <td>Status code ranges are not recognized</td>
        <td>'2XX' is a valid range definition according to the OpenAPI specification. Boomi is not recognizing this 
            status code as a successful response.</td>
        <td>20</td>
        <td>
            API: Ably, OperationId: getMessagesByChannel<br>
            <pre>
responses:
    '2XX':
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
   3. The key "scope" and its value must be added as an "Extended JWT Claim".

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
   3. The key "scope" and its value must be added as an "Extended JWT Claim".

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

<a name="xero_assets"></a>
## Xero Assets

Documentation: https://developer.xero.com/documentation/api/assets/overview

### Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.


**2 out of 6 endpoints are failing.**

The following operations are not supported at this time:
* getAssets
* getAssetTypes

### Issues
1. Unsupported Parameter Type: null
   + References are not followed when validating parameter schema type.
   + Affected operations:
     + getAssets
2. Unsupported type:ARRAY
   + Responses with a schema type of array are not supported.
   + Affected operations:
     + getAssetTypes

---

<a name="xero_files"></a>
## Xero Files

Documentation: https://developer.xero.com/documentation/api/files/files

### Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.

**5 out of 17 endpoints are failing.**

The following operations are not supported at this time:
* getFileAssociations
* getAssociationsByObject
* getFolders
* uploadFile
* uploadFileToFolder

### Issues
1. Unsupported type:ARRAY
   + Responses with a schema type of array are not supported.
   + Affected operations:
      + getFileAssociations
      + getAssociationsByObject
      + getFolders
2. Schema can't be null
   + The request body has a content type of form-data. Only JSON request bodies are supported.
   + Affected operations:
      + uploadFile
      + uploadFileToFolder

---

<a name="xero_projects"></a>
## Xero Projects

Documentation: https://developer.xero.com/documentation/api/projects/overview

### Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.


**1 out of 13 endpoints are failing.**

The following operations are not supported at this time:
* getTasks

### Issues
1. Unsupported Parameter Type: null
   + References are not followed when validating parameter schema type.
   + Affected operations:
      + getTasks

---

<a name="xero_payroll_nz"></a>
## Xero Payroll NZ

Documentation: https://developer.xero.com/documentation/api/payrollnz/overview

### Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.

**2 out of 68 endpoints are failing.**

The following operations are not supported at this time:
* createEmployeeOpeningBalances
* createMultipleEmployeeEarningsTemplate

### Issues
1. Unsupported type:ARRAY
   + Responses with a schema type of array are not supported.
   + Affected operations:
      + createEmployeeOpeningBalances
      + createMultipleEmployeeEarningsTemplate
---

<a name="xero_payroll_uk"></a>
## Xero Payroll UK

Documentation: https://developer.xero.com/documentation/api/payrolluk/overview

### Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.

**1 out of 70 endpoints are failing.**

The following operations are not supported at this time:
* createMultipleEmployeeEarningsTemplate

### Issues
1. Unsupported type:ARRAY
   + Responses with a schema type of array are not supported.
   + Affected operations:
      + createMultipleEmployeeEarningsTemplate
---

<a name="ably_control"></a>
## Ably Control

https://developer.xero.com/documentation/api/payrollnz/overview

Documentation: https://ably.com/documentation/control-api

**6 out of 22 endpoints are failing.**

The following endpoints are not supported at this time:
* /accounts/{account_id}/apps 
* /apps/{app_id}/keys
* /apps/{app_id}/namespaces
* /apps/{app_id}/queues
* /apps/{app_id}/rules
* /apps/{id}/pkcs12

### Issues
1. Unsupported type:ARRAY
    + Responses with a schema type of array are not supported.
    + Affected operations:
      * /accounts/{account_id}/apps
      * /apps/{app_id}/keys
      * /apps/{app_id}/namespaces
      * /apps/{app_id}/queues
      * /apps/{app_id}/rules
2. Schema can't be null
    + The request body has a content type of "multipart/form-data"
    + Affected operations:
      * /apps/{id}/pkcs12

