# Boomi OpenAPI Connectors
These are branded connectors built for the Boomi platform with the Boomi OpenAPI SDK. 
You can find each connector's artifacts in their subdirectory.
See below for outstanding issues.
* [PagerDuty](#pagerduty)
  
* [Twitter v2](#twitterv2)

* [CircleCI](#circleci)

* [Firecracker](#firecracker) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Quickbase](#quickbase)

* [Apache Pulsar Functions](#apache_pulsar_functions)

* [Aquarius](#aquarius)

* [Provider](#provider)

* [Blackboard](#blackboard) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Docusign Esignature](#docusign_esignature)

* [Docusign Click](#docusign_click) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Docusign Rooms](#docusign_rooms)

* [Xero Assets](#xero_assets)

* [Xero Files](#xero_files)

* [Xero Projects](#xero_projects)

* [Xero Payroll NZ](#xero_payroll_nz)

* [Xero Payroll UK](#xero_payroll_uk)

* [Ably Control](#ably_control)

* [EBay Browse](#ebay_browse) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [EBay Marketing](#ebay_marketing) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Interzoid](#interzoid) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Google Drive](#google_drive) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Google Analytics v3](#google_analytics_v3) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Google Healthcare](#google_healthcare) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Google Calendar](#google_calendar) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Google Compute Engine](#google_compute_engine) <span style="color:green">**(ALL ENDPOINTS OPERATIONAL)**</span>

* [Guru](#guru)

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
* **EBay**
  * The majority of EBay APIs were skipped
  * This is due to the OAUTH2 Authorization Header Issue
* **Google Analytics Data**
  * https://github.com/APIs-guru/openapi-directory/blob/main/APIs/googleapis.com/analyticsdata/v1beta/openapi.yaml
  * 6 out of 7 endpoints failing
  * Stack overflow errors due to circular references
* **Postman**
  * Endpoints failing due to issue: Parameters at path level are ignored.


## Summary of Issues

1. OAUTH2 Authorization Header 
   1. Priority 1 
   2. Some implementations of OAUTH2 Authorization Code and Client Credentials flows require an authorization
  header in the access token request. Boomi does not support this. 
   3. Example: EBay Sell Account API

2. Circular Reference Error
   1. Priority 1
   2. Circular references are causing stack overflow errors.
   3. Example API: Pagerduty, OperationId: getServiceIntegration
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
3. Path level parameters ignored
   1. Priority 1
   2. In the OpenAPI specification, parameters can be defined at the path level and apply to multiple HTTP methods.
   3. Example API: Xero Assets, OperationId: getAssets
           <pre>
paths:
  /Assets:
    parameters:
      - $ref: '#/components/parameters/requiredHeader'
    get:
         </pre>
4. Can't find schema reference type
   1. Priority 2
   2. References are not followed when validating parameter schema type. Presents as "Unsupported Parameter Type: null".
   3. Example API: Twitter V2, OperationId: findTweetById<br>
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
5. Empty schemas are failing 
   1. Priority 2
   2. The path parameter has no type. It contains an empty schema. Presents as: "Unsupported Parameter Type: null".    
   3. Example API: CircleCI, OperationId: getTests<br>
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
    
6. Non JSON request bodies are not supported.
   1. Priority 2
   2. The request body has a content type of form-data or octet-stream. Only JSON request bodies are supported. This errors as "Schema can't be null".
   3. Example API: Apache Pulsar Functions, OperationId: registerFunction
            <pre>
requestBody:
  content:
    multipart/form-data:
      schema:
        $ref: '#/components/schemas/FunctionConfig'
  required: false
         </pre>
    
7. Unsupported response type of string.
   1. Priority 3
   2. In the response, the JSON schema type is "string". Errors as "Unsupported type: STRING".
   3. Example API: Twitter V2, OperationId: getOpenApiSpec<br>
            <pre>
"responses" : {
 "200" : {
   "description" : "The request was successful",
   "content" : {
     "application/json" : {
       "schema" : {
         "type" : "string"
         </pre>
8. DELETE requests with a request body are not supported. 
   1. Priority 3
   2. According to OpenAPI specifications, GET, DELETE, and HEAD are no longer allowed to have a request body.
   3. Example API: Quickbase, OperationId: deleteApp<br>
            <pre>
 delete:
   tags:
     \- Apps
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
9. Status code ranges are not recognized
   1. Priority 3 
   2. 2XX is a valid range definition according to the OpenAPI specification. Boomi is not recognizing this 
            status code as a successful response.</td>
        <td>20</td>
        <td>
            API: Ably, OperationId: getMessagesByChannel<br>
            <pre>
responses:
    '2XX':
            </pre>

---
<a name="pagerduty"></a>
## PagerDuty

**8 out of 193 endpoints are failing.**

### Issues
1. Property value is null. 
    + Schema references to parameter components are not being processed.
    + Affected operations: 
        * listEscalationPolicyAuditRecords
        * listSchedulesAuditRecords
        * listServiceAuditRecords
        * listTeamsAuditRecords
        * listUsersAuditRecords
        
2. Stackoverflow error
    + Circular references are causing stack overflow errors.
    + Affected operations:
        * getServiceIntegration
        * createServiceIntegration
        * updateServiceIntegration
    

---   

<a name="twitterv2"></a>
## Twitter API v2

**4 out of 7 endpoints are failing.**

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

**6 out of 41 endpoints are failing.**

### Issues
1. Unsupported Parameter Type: null
   + The path parameter has no type. It contains an empty schema. ***"schema": {}***
   + Affected operations:
      * getJobArtifacts
      * getTests
      * getJobDetails
      * getPipelineByNumber
      * getJobDetails

---   

<a name="firecracker"></a>
## Firecracker

**All 26 endpoints are passing.**

---   

<a name="quickbase"></a>
## Quickbase

**3 out of 35 endpoints failing.**

### Issues
1. DELETE requests should not return a request body.
    + According to OpenAPI specifications, DELETE requests should not include a request body.
    + Affected operations:
      * deleteApp
      * deleteFields
      * deleteRecords

---   

<a name="apache_pulsar_functions"></a>
## Apache Pulsar Functions

**4 out of 19 endpoints are failing.**

### Issues
1. Unsupported type: STRING
   + The response content type is string, which is not a supported response type.
   + Affected operations:
      * listFunctions
2. Schema can't be null
   + The request body has a content type of form-data. Only JSON request bodies are supported.
   + Affected operations:
     * registerFunction
     * triggerFunction
     * updateFunction

---   

<a name="aquarius"></a>
## Aquarius

**2 out of 11 endpoints are failing.**

### Issues
1. Schema can't be null
    + The request body has a content type of octet-stream. Only JSON request bodies are supported.
    + Affected operations:
      * /api/v1/aquarius/assets/ddo/encrypt
      * /api/v1/aquarius/assets/ddo/encryptashex

---

<a name="provider"></a>
## Provider

**2 out of 7 endpoints are failing.**

### Issues
1. Unsupported Parameter Type: null
    + The query parameter has no type. It contains an empty schema. ***"schema": {}***
    + Affected operations:
      * /api/v1/services/compute POST
      * /api/v1/services/download GET

--- 

<a name="blackboard"></a>
## Blackboard

**All 248 endpoints are passing.**

---

<a name="docusign_esignature"></a>
## Docusign Esignature v2.1

**51 out of 402 endpoints are failing.**

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

**All 21 endpoints are passing.**

---

<a name="docusign_rooms"></a>
## Docusign Rooms v2

**4 out of 89 endpoints are failing.**

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

**1 out of 6 endpoints are failing.**

### Issues
1. Unsupported Parameter Type: null
   + References are not followed when validating parameter schema type.
   + Affected operations:
     + getAssets

---

<a name="xero_files"></a>
## Xero Files

**2 out of 17 endpoints are failing.**

### Issues
1. Schema can't be null
   + The request body has a content type of form-data. Only JSON request bodies are supported.
   + Affected operations:
      + uploadFile
      + uploadFileToFolder

---

<a name="xero_projects"></a>
## Xero Projects

**1 out of 13 endpoints are failing.**

### Issues
1. Unsupported Parameter Type: null
   + References are not followed when validating parameter schema type.
   + Affected operations:
      + getTasks

---

<a name="xero_payroll_nz"></a>
## Xero Payroll NZ

**All endpoints are working.**

### Issues
1. Unsupported type:ARRAY
   + Responses with a schema type of array are not supported.
   + Affected operations:
      + createEmployeeOpeningBalances
      + createMultipleEmployeeEarningsTemplate
---

<a name="xero_payroll_uk"></a>
## Xero Payroll UK

**All endpoints are working.**

---

<a name="ably_control"></a>
## Ably Control

**1 out of 22 endpoints are failing.**

### Issues
1. Schema can't be null
    + The request body has a content type of "multipart/form-data"
    + Affected operations:
      * /apps/{id}/pkcs12

---

<a name="ebay_browse"></a>
## EBay Buy Browse 

**All 11 endpoints are passing.**

---

<a name="ebay_marketing"></a>
## EBay Buy Marketing

**All 3 endpoints are passing.**

---

<a name="interzoid"></a>
## Interzoid API Collection

**All 23 endpoints are passing.**

The following Interzoid APIs are supported:
<pre>Get Company Match Similarity Key
Get Full Name Match Similarity Key
Get Full Name Parsed Match Similarity Key
Get Address Match Similarity Key
Get City Match Similarity Key
Get Country Match Similarity Key
Get State Two-Letter Abbreviation
Get City Standard
Get Country Standard
Retrieve Detailed Information for an Email Address
Global Page Load Performance
Get Currency Rate
Convert Currency Rate
Get Current Crypto Price
Get Precious Metal Price
Get Current Time for a Global Location
Get Global Number Information
Get North American Area Code
Get North American Area Code From Number
Get Current Weather for a US City
Get Current Weather by US Zip Code
Get Current US Weather by Lat and Long
Get Zip Code Detailed Info
</pre>[

---

<a name="google_drive"></a>
## Google Drive
**All 46 endpoints are passing.**
---

<a name="google_analytics_v3"></a>
## Google Analytics v3
**All 88 endpoints are passing**

---

<a name="google_healthcare"></a>
## Google Healthcare API v1
**All 56 endpoints are passing**

---

<a name="google_calendar"></a>
## Google Calendar API v3
**All 37 endpoints are passing**
---

<a name="google_compute_engine"></a>
## Google Compute Engine API v1
**All 597 endpoints are passing**

---
<a name="guru"></a>
## Guru API v1.0
**All documented endpoints are passing**

