# Google Drive API v3 Connector
"The Google Drive API allows you to create apps that leverage Google Drive cloud storage. You can develop applications that integrate with Google Drive, and create robust functionality in your application using Google Drive API."

Documentation: https://developers.google.com/drive/api

Specification: https://github.com/APIs-guru/openapi-directory/blob/main/APIs/googleapis.com/drive/v3/openapi.yaml

## Prerequisites
+ Google Cloud Platform Account with the Google Drive API enabled
+ Add Boomi callback URL to your GCP application settings: https://platform.boomi.com/account/<your_Boomi Integration_account_ID>/oauth2/callback
+ You need to enter the following for authentication through Boomi:
  + Client ID
  + Client Secret
  + Scope (For example, the most permissive is: https://www.googleapis.com/auth/drive)

## Supported Operations
**2 out of 46 endpoints are failing.**

The following operations are **not** supported at this time:
* drive.files.create
* drive.files.update
