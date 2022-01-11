# Boomi OpenAPI Connectors
These are branded connectors built for the Boomi platform with the Boomi OpenAPI SDK.
You can find each connector's artifacts in their subdirectory.
See below for outstanding issues.

* [PagerDuty](#pagerduty)

* [Twitter v2](#twitterv2)

* [CircleCI](#circleci)

* [Firecracker](#firecracker) **(ALL ENDPOINTS OPERATIONAL)**

* [Quickbase](#quickbase)

* [Apache Pulsar Functions](#apache_pulsar_functions)

* [Aquarius](#aquarius)

* [Provider](#provider)

* [Blackboard](#blackboard) **(ALL ENDPOINTS OPERATIONAL)**

* [Docusign Esignature](#docusign_esignature)

* [Docusign Click](#docusign_click) **(ALL ENDPOINTS OPERATIONAL)**

* [Docusign Rooms](#docusign_rooms)

* [Xero Assets](#xero_assets)

* [Xero Files](#xero_files)

* [Xero Projects](#xero_projects)

* [Xero Payroll NZ](#xero_payroll_nz) **(ALL ENDPOINTS OPERATIONAL)**

* [Xero Payroll UK](#xero_payroll_uk) **(ALL ENDPOINTS OPERATIONAL)**

* [Ably Control](#ably_control)

* [EBay Browse](#ebay_browse) **(ALL ENDPOINTS OPERATIONAL)**

* [EBay Marketing](#ebay_marketing) **(ALL ENDPOINTS OPERATIONAL)**

* [Interzoid](#interzoid) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Drive](#google_drive) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Analytics v3](#google_analytics_v3) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Healthcare](#google_healthcare) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Calendar](#google_calendar) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Compute Engine](#google_compute_engine) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Cloud Scheduler](#google_cloud_scheduler) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Cloud Asset](#google_cloud_asset) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Cloud DNS](#google_cloud_dns) **(ALL ENDPOINTS OPERATIONAL)**

* [Google People](#google_people) **(ALL ENDPOINTS OPERATIONAL)**

* [Google AdMob](#google_admob) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Android Management](#google_android_management) **(ALL ENDPOINTS OPERATIONAL)**

* [Google App Engine Admin](#google_app_engine_admin) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Artifact Registry](#google_artifact_registry) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Cloud Build](#google_cloud_build) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Classroom](#google_classroom) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Cloud Tasks](#google_cloud_tasks) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Data Fusion](#google_data_fusion) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Data Pipelines](#google_data_pipelines) **(ALL ENDPOINTS OPERATIONAL)**

* [Google Language](#google_language) **(ALL ENDPOINTS OPERATIONAL)**

* [Guru](#guru) **(ALL ENDPOINTS OPERATIONAL)**

* [Vonage Messages](#vonage_messages) **(ALL ENDPOINTS OPERATIONAL)**

* [Vonage Dispatch](#vonage_dispatch) **(ALL ENDPOINTS OPERATIONAL)**

---

## Summary of Issues

#### Attempted: 71
#### Skipped: 19
#### Partially functional: 15
#### Fully functional: 30


1. Unable to create profiles for XML
    * Priority 1
    * The OpenAPI SDK cannot parse XML type responses or requests
    * Example: AWS Elasticbeanstalk API, OperationId: GET_ApplyEnvironmentManagedAction
```
responses:
'200':
  description: Success
  content:
    text/xml:
      schema:
        $ref: '#/components/schemas/ApplyEnvironmentManagedActionResult'
```
3. OAUTH2 Authorization Header
    * Priority 1
    * Some implementations of OAUTH2 Authorization Code and Client Credentials flows require an authorization
      header in the access token request. Boomi does not support this.
    * Example: EBay Sell Account API

4. Circular Reference Error
    * Priority 1
    * Circular references are causing stack overflow errors.
    * Example API: Pagerduty, OperationId: getServiceIntegration
```
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
```
4. Path level parameters ignored
    * Priority 1
    * In the OpenAPI specification, parameters can be defined at the path level and apply to multiple HTTP methods.
    * Example API: Xero Assets, OperationId: getAssets
```
paths:
  /Assets:
    parameters:
      - $ref: '#/components/parameters/requiredHeader'
    get:
```
5. Can't find schema reference type
    * Priority 2
    * References are not followed when validating parameter schema type. Presents as "Unsupported Parameter Type: null".
    * Example API: Twitter V2, OperationId: findTweetById
```
"parameters" : [ {
 "name" : "id",
 "in" : "path",
 "description" : "A single Tweet ID.",
 "required" : true,
 "schema" : {
   "$ref" : "#/components/schemas/TweetID"
 }
```
6. Empty schemas are failing
    * Priority 2
    * The path parameter has no type. It contains an empty schema. Presents as: "Unsupported Parameter Type: null".
    * Example API: CircleCI, OperationId: getTests
```
"parameters": [
 {
   "in": "path",
   "name": "job-number",
   "description": "The number of the job.",
   "schema": {},
   "required": true,
   "example": "123"
 },
```

7. Non JSON request bodies are not supported.
    * Priority 2
    * The request body has a content type of form-data or octet-stream. Only JSON request bodies are supported. This errors as "Schema can't be null".
    * Example API: Apache Pulsar Functions, OperationId: registerFunction
```
requestBody:
  content:
    multipart/form-data:
      schema:
        $ref: '#/components/schemas/FunctionConfig'
  required: false
```

8. Unsupported response type of string.
    * Priority 3
    * In the response, the JSON schema type is "string". Errors as "Unsupported type: STRING".
    * Example API: Twitter V2, OperationId: getOpenApiSpec
```
"responses" : {
 "200" : {
   "description" : "The request was successful",
   "content" : {
     "application/json" : {
       "schema" : {
         "type" : "string"
```
9. DELETE requests with a request body are not supported.
    * Priority 3
    * According to OpenAPI specifications, GET, DELETE, and HEAD are no longer allowed to have a request body.
    * Example API: Quickbase, OperationId: deleteApp
```
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
```
10. Status code ranges are not recognized
    * Priority 3
    * 2XX is a valid range definition according to the OpenAPI specification. Boomi is not recognizing this
      status code as a successful response.
    * Example API: Ably, OperationId: getMessagesByChannel
```
responses:
     '2XX':
```

---
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
* **AWS APIs**
    * Using XML rather than JSON for request/response bodies
* **Zoom**
    * Path level parameters
* **Clever**
    * Basic auth header for auth2 issu[e
* **Firestore**
    * Circular references
* **Dynamics 365 Business Central**
    * Path level parameters
* **Vonage SMS**
    * Request body is form content.
* **Vonage Verify**]()
    * Request body is form content.

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
    + The response con`tent type is string, which is not a supported response type.
    + Affected operations:
        * listFunctions
2. Schema can't be null
    + The request body has a content type of form-data. Only JSON request bodies are supported.
    + Affected operations:
        * registerFunction
        * triggerFunction
        * updateFunction`

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

