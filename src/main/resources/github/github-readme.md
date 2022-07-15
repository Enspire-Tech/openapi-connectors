# Github API Connector

Documentation: https://docs.github.com/en/rest

Specification: https://raw.githubusercontent.com/github/rest-api-description/main/descriptions/ghes-3.5/ghes-3.5.yaml

## Prerequisites

+ Register the Github account for OAuth2 and add your specific Boomi callback url:
  + https://platform.boomi.com/account/<YOUR-ACCOUNT-ID>/oauth2/callback

## Supported Operations
**10 out of 776 endpoints are failing.**

The following operations are **not** supported at this time:
 gitignore/get-all-templates
 meta/get-octocat
 repos/get-all-status-check-contexts
 meta/get-zen
 markdown/render
 markdown/render-raw
 repos/add-status-check-contexts
 repos/upload-release-asset
 repos/set-status-check-contexts
