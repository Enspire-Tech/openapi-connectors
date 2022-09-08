# Zoom Contact Center Connector

Documentation: https://marketplace.zoom.us/docs/api-reference/contact-center/methods/#overview

## Prerequisites

+ Setup OAUTH2
    + Create an app to obtain a client id and client secret.
    + Enter your boomi callback url into the Zoom App: https://platform.boomi.com/account/[YOUR-TENANT-ID]/oauth2/callback

## Supported Operations
**25 out of 93 endpoints are failing.**

The following operations are **not** supported at this time:
* getQueueDispositionSets
* users
* userGet
* getDisposition
* getSet
* /contact_center/users/{userId}/skills
* listUserRecordings
* listEngagementRecordings
* listQueueRecordings
* notes
* getNote
* engagementNotes
* AnalyticsHistoricalVoiceQueuesMetrics
* listHistoricalDetailMetric
* listQueueAgentMetric
* assignQueueDispositions
* assignQueueDispositionSets
* createUser
* deleteQueueDisposition
* deleteQueueDispositionSet
* deleteDisposition
* deleteSet
* updateDisposition
* updateSet
* noteUpdate