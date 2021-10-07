# Provider Connector
"Ocean Provider is the technical component executed by Data Providers allowing them to provide extended data services. When running with our Docker images, it is exposed under http://localhost:8030."

Documentation: https://docs.oceanprotocol.com/references/provider/

Specification: https://github.com/Enspire-Tech/openapi-connector-artifacts/blob/master/provider/custom-specification-provider.yaml

## Prerequisites

+ A running Provider instance

## Supported Operations

**2 out of 7 endpoints are failing.**

The following operations are **not** supported at this time:
* /api/v1/services/compute POST
* /api/v1/services/download GET
