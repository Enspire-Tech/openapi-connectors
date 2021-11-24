# Google Cloud Scheduler API v1 Connector
Fully managed cron job service.

Documentation: https://cloud.google.com/scheduler/docs/quickstart
Specification: https://github.com/APIs-guru/openapi-directory/blob/main/APIs/googleapis.com/cloudscheduler/v1/openapi.yaml

## Prerequisites
+ Google Cloud Platform Account with the Google Cloud Scheduler API enabled
+ Add Boomi callback URL to your GCP application settings: https://platform.boomi.com/account/<your_Boomi Integration_account_ID>/oauth2/callback
+ OAuth2 Authorization Code Grant and JWT Grant types are supported
    + If you are using Authorization Code Grant, you must provide:
        + Client ID
        + Client Secret
          =  + If you are using JWT Grant, you must provide:
        + A .p12 key file created for a GCP service account

## Implementation Notes
+ For Authorization Code Grant type:
    + The scope should be https://www.googleapis.com/auth/cloud-platform
+ For JWT Bearer Token Grant type
    + The .p12 key file needs to be uploaded to Boomi as an X.509 Certificate
    + The issuer and subject should be the email address of the service account.
        + \############-compute@developer.gserviceaccount.com
    + The scope needs to be added in the "Extended JWT Claim" section

## Supported Operations
**All 9 endpoints are passing.**

