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
  "google_datastore"
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
  "amazon_chime"
  "amazon_cloudwatch"
  "amazon_dynamodb"
  "aws_amplify"
  "aws_amplify_backend"
  "amazon_rds"
  "amazon_glacier"
  "amazon_lightsail"
  "amazon_lookout_metrics"
  "aws_lambda"
  "aws_rds_data_service"
  "amazon_lookout_vision"
  "amazon_machine_learning"
  "amazon_mechanical_turk"
  "amazon_memorydb"
  "amazon_polly"
  "amazon_rekognition"
  "amazon_route53_recovery_cluster"
  "amazon_route53_recovery_control_configuration"
  "amazon_sagemaker_edge_manager"
  "amazon_sagemaker_feature_store_runtime"
  "amazon_transcribe"
  "amazon_translate"
  "amazon_clouddirectory"
  "amazon_cognito_identity"
  "amazon_cognito_identity_provider"
  "amazon_elastic_block_store"
  "amazon_elastic_file_system"
  "amazon_inspector"
  "amazon_redshift_data"
  "aws_auto_scaling_plans"
  "abuseipdb"
  "opsgenie"
  "parsehub"
  "pandadoc"
  "pipedrive"
  "pingdom"
  "uservoice"
  "splunk_on_call"
  "docker_engine"
  "digitalocean"
  "github"
  "postmark_server"
  "postmark_account"
  "hubspot_crm"
  "hubspot_cms"
  "hubspot_marketing"
  "hubspot_events"
  "gitlab"
  "square_connect"
  "mailchimp_transactional"
  "healthcaredotgov"
  "zoom_meeting"
  "zoom_marketplace"
  "zoom_phone"
  "zoom_contact_center"
  "zoom_video_sdk"
  "zoom_chat"
  "zoom_scim2"
  "zoom_chatbot"
)

for c in ${connectors[@]}; do
  "$SCRIPT_PATH" \
  -o "$REPO_DIR" \
  -t $c
done