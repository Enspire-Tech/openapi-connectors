# Hubspot CRM Connector

Documentation: https://developers.hubspot.com/docs/api/crm/understanding-the-crm

## Prerequisites

+ Authentication can be done with OAUTH2 or a Private App Access Token
+ For OAUTH2:
  + Find your Client ID and Client secret when you create a new app
  + Provide your required scope
+ For Private App Access Token:
  + Create a token in Settings > Integrations

## Supported Operations
**1 out of 205 endpoints are failing.**

The following operations are **not** supported at this time:
* get-/integrators/timeline/v3/events/{eventTemplateId}/{eventId}/render_getRenderById
