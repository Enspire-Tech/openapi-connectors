# Google Cloud Logging API v2 Connector
"Writes log entries and manages your Cloud Logging configuration."

Documentation: https://cloud.google.com/logging/docs/
Specification: https://github.com/APIs-guru/openapi-directory/blob/main/APIs/googleapis.com/logging/v2/openapi.yaml

## Prerequisites
+ Google Cloud Platform Account with this API enabled
+ Add Boomi callback URL to your GCP application settings: https://platform.boomi.com/account/<your_Boomi Integration_account_ID>/oauth2/callback
+ OAuth2 Authorization Code Grant and JWT Grant types are supported
    + If you are using Authorization Code Grant, you must provide:
        + Client ID
        + Client Secret
        + If you are using JWT Grant, you must provide:
        + A .p12 key file created for a GCP service account

## Implementation Notes
+ For Authorization Code Grant type:
    + Find applicable scope at: https://developers.google.com/identity/protocols/oauth2/scopes
+ For JWT Bearer Token Grant type
    + The .p12 key file needs to be uploaded to Boomi as an X.509 Certificate
    + The issuer and subject should be the email address of the service account.
        + Example: \############-xxxxxxxx@developer.gserviceaccount.com
    + The scope needs to be added in the "Extended JWT Claim" section

## Supported Operations
**All endpoints are passing.**

