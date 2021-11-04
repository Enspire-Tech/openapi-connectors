# Xero Payroll New Zealand Connector
"The Payroll API exposes payroll related functions of the Xero payroll application and can be used for a variety of purposes such as syncing employee details, importing timesheets etc."

Documentation: https://developer.xero.com/documentation/api/payrollnz/overview

Specification: https://github.com/XeroAPI/Xero-OpenAPI/blob/master/xero_assets.yaml

## Prerequisites

+ Xero account
+ OAuth2 setup with Authorization Code Grant flow

## Implementation Notes
1. Xero-Tenant-Id should be automatically populated when importing the operation. For this to work, generate the OAuth2 token in the connection before importing the operation.

## Supported Operations
**All 68 endpoints are working.**
