# Zoom Phone Connector

Documentation: https://marketplace.zoom.us/docs/api-reference/phone/methods/#overview

## Prerequisites

+ Setup OAUTH2
    + Create an app to obtain a client id and client secret.
    + Enter your boomi callback url into the Zoom App: https://platform.boomi.com/account/[YOUR-TENANT-ID]/oauth2/callback

## Supported Operations
**24 out of 247 endpoints are failing.**

The following operations are **not** supported at this time:
* getCallQueueRecordings
* listPhoneDevices
* getADevice
* listCallLogsMetrics
* getPhoneRecordings
* getPSOperationLogs
* getSiteSettingForType
* accountSmsSession
* smsSessionDetails
* phoneUser
* phoneUserCallLogs
* phoneUserRecordings
* phoneUserSettings
* userSmsSession
* phoneUserVoiceMails
* accountVoiceMails
* createCRPhoneNumbers
* addExtensionsToADevice
* addMembers
* addUserSetting
* activeCRPhoneNumbers
* updateADevice
* updateSiteSetting
* updateUserSetting