# Ably Control Connector
"Ably Control API is a REST API that enables you to manage your Ably account programmatically. The Control API also enables you to build web apps and command-line tools, to create and manage your Ably realtime infrastructure."

Documentation: https://ably.com/documentation/control-api

Specification: https://github.com/ably/open-specs/blob/main/definitions/control-v1.yaml

## Prerequisites

+ Ably account
+ API Key

## Supported Operations

**6 out of 22 endpoints are failing.**

The following endpoints are not supported at this time:
* /accounts/{account_id}/apps
* /apps/{app_id}/keys
* /apps/{app_id}/namespaces
* /apps/{app_id}/queues
* /apps/{app_id}/rules
* /apps/{id}/pkcs12
