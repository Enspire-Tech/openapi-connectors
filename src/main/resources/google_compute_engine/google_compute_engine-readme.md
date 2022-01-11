# Google Cloud Compute Engine API v1 Connector
Creates and runs virtual machines on Google Cloud Platform.

Specification: https://github.com/APIs-guru/openapi-directory/blob/main/APIs/googleapis.com/compute/v1/openapi.yaml

## Prerequisites
+ Google Cloud Platform Account with the Google Compute Engine API enabled
+ Add Boomi callback URL to your GCP application settings: https://platform.boomi.com/account/<your_Boomi Integration_account_ID>/oauth2/callback
+ OAuth2 Authorization Code Grant and JWT Grant types are supported
  + If you are using Authorization Code Grant, you must provide:
      + Client ID
      + Client Secret
=  + If you are using JWT Grant, you must provide:
    + A .p12 key file created for a GCP service account

## Implementation Notes
+ For Authorization Code Grant type:
  + The scope should be https://www.googleapis.com/auth/cloud-platform or https://www.googleapis.com/auth/compute
+ For JWT Bearer Token Grant type
  + The .p12 key file needs to be uploaded to Boomi as an X.509 Certificate
  + The issuer and subject should be the email address of the service account.
    + \############-compute@developer.gserviceaccount.com
  + The scope needs to be added in the "Extended JWT Claim" section

## Supported Operations
**All 597 endpoints are passing.**

