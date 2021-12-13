#!/usr/bin/env bash

connectors=(
  "pagerduty"
  "twitter_v2"
  "circleci"
  "firecracker"
  "quickbase"
  "apache_pulsar_functions"
  "aquarius"
  "provider"
  "blackboard"
  "docusign_esignature"
  "docusign_click"
  "docusign_rooms"
  "xero_assets"
  "xero_files"
  "xero_projects"
  "xero_payroll_nz"
  "xero_payroll_uk"
  "ably_control"
  "ebay_browse"
  "ebay_marketing"
  "interzoid"
  "google_drive"
  "google_analytics_v3"
  "google_healthcare"
  "google_calendar"
  "google_compute_engine"
  "google_cloud_scheduler"
  "google_cloud_asset"
  "google_cloud_dns"
  "google_people"
  "guru"
  "vonage_messages"
  "vonage_dispatch"
)

for c in ${connectors[@]}; do
  /Users/jacobmortelliti/IdeaProjects/openapi-connectors/src/main/resources/separate-connector.sh \
  -o /Users/jacobmortelliti/boomi/openapi_connector_repos \
  -t $c
done