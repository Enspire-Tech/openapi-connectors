# Docusign Click v2 Connector
"DocuSign Click lets you capture consent to standard agreement terms with a single click: terms and conditions, terms of service, terms of use, privacy policies, and more. The Click API lets you include this customizable clickwrap solution in your DocuSign integrations."

Documentation: https://developers.docusign.com/docs/click-api/

Swagger Specification: https://github.com/docusign/OpenAPI-Specifications/blob/master/click.rest.swagger-v2.json

OAS3 Converted Specification: https://github.com/Enspire-Tech/openapi-connector-artifacts/blob/master/docusign_click/custom-specification-docusign_click.yaml

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

**All 21 endpoints are passing.**
