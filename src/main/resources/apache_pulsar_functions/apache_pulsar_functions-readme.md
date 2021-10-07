# Apache Pulsar Functions Connector
"Pulsar Functions provides an easy-to-use API that developers can use to create and manage processing logic for the Apache Pulsar messaging system. With Pulsar Functions, you can write functions of any level of complexity in Java or Python and run them in conjunction with a Pulsar cluster without needing to run a separate stream processing engine."

Documentation: https://pulsar.apache.org/functions-rest-api/

Specification: https://github.com/Enspire-Tech/openapi-connector-artifacts/blob/master/apache_pulsar_functions/custom-specification-apache_pulsar_functions.yaml

## Prerequisites

+ A running Apache Pulsar process

## Supported Operations

**5 out of 19 endpoints are failing.**

The following operations are **not** supported at this time:
* getConnectorsList
* listFunctions
* registerFunction
* triggerFunction
* updateFunction

