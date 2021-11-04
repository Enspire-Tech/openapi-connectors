# Xero Files Connector
"The Files API provides access to the files, folders, and the association of files within a Xero organisation. It can be used to upload/download files, manage folders and associate files to invoices, contacts, payments etc."

Documentation: https://developer.xero.com/documentation/api/files/overview

Specification: https://github.com/XeroAPI/Xero-OpenAPI/blob/master/xero_files.yaml

## Prerequisites

+ Xero account
+ OAuth2 setup with Authorization Code Grant flow

## Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.

## Supported Operations
**2 out of 17 endpoints are failing.**

The following operations are not supported at this time:
+ uploadFile
+ uploadFileToFolder
