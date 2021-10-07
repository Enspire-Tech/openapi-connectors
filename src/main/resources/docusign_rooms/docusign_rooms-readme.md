# Docusign Rooms v2 Connector
"DocuSign Rooms, a technology available through the Rooms for Real Estate and Rooms for Mortgage products, simplifies complex agreements by bringing together each person involved with a transaction in a digital space called a room where every detail, task, and transaction document is centrally located and managed."

Documentation: https://developers.docusign.com/docs/rooms-api/rooms101

Swagger Specification: https://github.com/docusign/OpenAPI-Specifications/blob/master/rooms.rest.swagger-v2.json

OAS3 Converted Specification: https://github.com/Enspire-Tech/openapi-connector-artifacts/blob/master/docusign_rooms/custom-specification-docusign_rooms.yaml

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

**4 out of 89 endpoints are failing.**

The following operations are not supported at this time:
* Fields_GetFieldSet
* Rooms_GetRoomFieldSet
* Rooms_AddDocumentToRoomViaFileUpload
* Rooms_UpdatePicture
