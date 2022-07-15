# Docker Engine API 1.41 Connector
The Engine API is an HTTP API served by Docker Engine. It is the API the Docker client uses to communicate with the Engine, so everything the Docker client can do can be done with the API.

Documentation: https://docs.docker.com/engine/api/v1.41/

## Prerequisites

+ A TCP socket must be enabled when the engine daemon starts.

## Supported Operations
**16 out of 106 endpoints are failing.**

The following operations are **not** supported at this time:
ContainerLogs
SystemPing
ImageGet
ImageGetAll
ServiceLogs
TaskLogs
ImageBuild
ImageCreate
ImageLoad
PluginPull
PluginUpgrade
PluginCreate
PluginSet
SwarmInit
PutContainerArchive
SystemPingHead
