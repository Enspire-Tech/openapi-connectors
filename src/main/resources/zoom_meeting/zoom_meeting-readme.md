# Zoom Meeting Connector

Documentation: https://marketplace.zoom.us/docs/api-reference/zoom-api/methods/#overview

## Prerequisites

+ Setup OAUTH2
  + Create an app to obtain a client id and client secret.
  + Enter your boomi callback url into the Zoom App: https://platform.boomi.com/account/[YOUR-TENANT-ID]/oauth2/callback

## Supported Operations
**15 out of 296 endpoints are failing.**

The following operations are **not** supported at this time:
* accountSettingsRegistration
* groupSettingsRegistration
* dashboardWebinarParticipantsQOSSummary
* getRoomProfiles
* getRoomDevices
* getRoomProfile
* listCollaborationDevices
* getHotDeskUsage
* createRoomDeviceProfile
* createReservation
* deleteRoomProfile
* deleteReservation
* accountSettingsRegistrationUpdate
* updateDeviceProfile
* updateReservation
