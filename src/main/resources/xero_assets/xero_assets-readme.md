# Xero Assets Connector
"The Assets API exposes fixed asset related functions of the Xero Accounting application and can be used for a variety of purposes such as creating assets, retrieving asset valuations etc."

Documentation: https://developer.xero.com/documentation/api/assets/overview

Specification: https://github.com/XeroAPI/Xero-OpenAPI/blob/master/xero_assets.yaml

## Prerequisites

+ Xero account
+ OAuth2 setup with Authorization Code Grant flow

## Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.

## Supported Operations
**2 out of 6 endpoints are failing.**

The following operations are not supported at this time:
* getAssets
* getAssetTypes