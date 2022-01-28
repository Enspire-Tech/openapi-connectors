#!/usr/bin/env bash

SCRIPT_PATH="/Users/jacobmortelliti/IdeaProjects/openapi-connectors/src/main/resources/separate-connector.sh"
REPO_DIR="/Users/jacobmortelliti/boomi/openapi_connector_repos/"

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
  'google_admob'
  'google_android_management'
  'google_app_engine_admin'
  'google_artifact_registry'
  'google_cloud_build'
  "google_admob"
  "google_app_engine_admin"
  "google_android_management"
  "google_artifact_registry"
  "google_cloud_build"
  "google_classroom"
  "google_cloud_tasks"
  "google_data_fusion"
  "google_data_pipelines"
  "google_language"
  "google_apps_script"
  "google_cloud_logging"
  "google_cloud_monitoring"
  "google_translate"
  "google_cloud_shell"
  "google_sheets"
  "guru"
  "vonage_messages"
  "vonage_dispatch"
  "amazon_kinesis"
  "amazon_kinesis_analytics"
  "amazon_kinesis_firehose"
)

for c in ${connectors[@]}; do
  "$SCRIPT_PATH" \
  -o "$REPO_DIR" \
  -t $c
done