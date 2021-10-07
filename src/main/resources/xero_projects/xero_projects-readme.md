# Xero Projects Connector
"Xero Projects allows businesses to track time and costs on projects/jobs and report on profitability."

Documentation: https://developer.xero.com/documentation/api/projects/overview

Specification: https://github.com/XeroAPI/Xero-OpenAPI/blob/master/xero-projects.yaml

## Prerequisites

+ Xero account
+ OAuth2 setup with Authorization Code Grant flow

## Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.

## Supported Operations
**1 out of 13 endpoints are failing.**

The following operations are not supported at this time:
* getTasks
