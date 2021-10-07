# Docusign eSignature v2.1 Connector
"Integrate eSignatures into your application in minutes. DocuSign's secure and award-winning eSignature API makes requesting signatures, automating forms, and tracking documents directly from your app easy."

Documentation: https://developers.docusign.com/docs/esign-rest-api/

Swagger Specification: https://github.com/docusign/OpenAPI-Specifications/blob/master/esignature.rest.swagger-v2.1.json

OAS3 Converted Specification: https://github.com/Enspire-Tech/openapi-connector-artifacts/blob/master/docusign_esignature/custom-specification-docusign_esignature.yaml

## Prerequisites

+ Docusign account
+ OAuth2 setup using either the Authorization Code Grant or JWT Grant flows.

## Implementation Notes
1. If using an OAuth 2.0 Authorization Code grant type, the "Preemptive authentication" option must be checked on.
2. If using an OAuth 2.0 JWT Bearer Token grant type:
   1. The public and private key needs to be imported to Boomi as an X509 certificate.
   2. The expiration field requires an "expires in" value in seconds. Usually that should be 3600.
   3. The key "scope" with desired value (for example, "signature impersonation") must be added as an "Extended JWT Claim".
   
## Supported Operations

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
