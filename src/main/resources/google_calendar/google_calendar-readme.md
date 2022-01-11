# Google Calendar API v3 Connector
Manipulates events and other calendar data.

Specification: https://github.com/APIs-guru/openapi-directory/blob/main/APIs/googleapis.com/calendar/v3/openapi.yaml

## Prerequisites
+  JWT Grant type is supported
    + You must provide a.p12 key file created for a GCP service account

## Implementation Notes
+ For JWT Bearer Token Grant type
    + The .p12 key file needs to be uploaded to Boomi as an X.509 Certificate
    + The issuer and subject should be the email address of the service account.
        + \############-compute@developer.gserviceaccount.com
    + The scope needs to be added in the "Extended JWT Claim" section
      + Provide a one or a combination of the following scopes:
        + https://www.googleapis.com/auth/calendar
        + https://www.googleapis.com/auth/calendar.events
        + https://www.googleapis.com/auth/calendar.events.readonly
        + https://www.googleapis.com/auth/calendar.readonly
        + https://www.googleapis.com/auth/calendar.settings.readonly

## Supported Operations
**All 37 endpoints are passing.**

