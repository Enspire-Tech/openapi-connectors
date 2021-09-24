package com.boomi.connector.ebay;

import com.boomi.connector.api.ConnectorContext;
import com.boomi.connector.api.PropertyMap;
import org.apache.commons.lang3.StringUtils;

public class CustomContext {

    private String server;
    private String spec;

    public CustomContext(ConnectorContext context) {
        PropertyMap connectionProperties = context.getConnectionProperties();
        String apiCategory = connectionProperties.getProperty("apiCategory");
        String apiType = connectionProperties.getProperty(apiCategory);
        switch (apiType) {
            case "sell_account":
                spec = "sell_account_v1_oas3-1.json";
                server = "https://api.ebay.com/sell/account/v1";
                break;
            case "sell_inventory":
                spec = "sell_inventory_v1_oas3.json";
                server = "https://api.ebay.com/sell/inventory/v1";
                break;
            case "sell_fulfillment":
                spec = "sell_fulfillment_v1_oas3.json";
                server = "https://api.ebay.com/sell/fulfillment/v1";
                break;
            case "sell_finances":
                spec = "sell_finances_v1_oas3.json";
                server = "https://api.ebay.com/sell/finances/v1";
                break;
            case "sell_marketing":
                spec = "sell_marketing_v1_oas3.json";
                server = "https://api.ebay.com/sell/marketing/v1";
                break;
            case "sell_negotiation":
                spec = "sell_negotiation_v1_oas3.json";
                server = "https://api.ebay.com/sell/negotiation/v1";
                break;
            case "sell_recommendation":
                spec = "sell_recommendation_v1_oas3.json";
                server = "https://api.ebay.com/sell/recommendation/v1";
                break;
            case "sell_analytics":
                spec = "sell_analytics_v1_oas3.json";
                server = "https://api.ebay.com/sell/analytics/v1";
                break;
            case "sell_metadata":
                spec = "sell_metadata_v1_oas3.json";
                server = "https://api.ebay.com/sell/metadata/v1";
                break;
            case "sell_compliance":
                spec = "sell_compliance_v1_oas3.json";
                server = "https://api.ebay.com/sell/compliance/v1";
                break;
            case "sell_feed":
                spec = "sell_feed_v1_oas3.json";
                server = "https://api.ebay.com/sell/feed/v1";
                break;
            case "buy_browse":
                spec = "buy_browse_v1_oas3.json";
                server = "https://api.ebay.com/buy/browse/v1";
                break;
            case "buy_marketing":
                spec = "buy_marketing_v1_beta_oas3.json";
                server = "https://api.ebay.com/buy/marketing/v1_beta";
                break;
            case "commerce_catalog":
                spec = "commerce_catalog_v1_beta_oas3.json";
                server = "https://api.ebay.com/commerce/catalog/v1_beta";
                break;
            case "commerce_charity":
                spec = "commerce_charity_v1_oas3.json";
                server = "https://api.ebay.com/commerce/charity/v1";
                break;
            case "commerce_media":
                spec = "commerce_media_v1_beta_oas3_conversion.yaml";
                server = "https://apim.ebay.com/commerce/media/v1_beta";
                break;
            case "commerce_notification":
                spec = "commerce_notification_v1_oas3.json";
                server = "https://api.ebay.com/commerce/notification/v1";
                break;
            case "commerce_taxonomy":
                spec = "commerce_taxonomy_v1_oas3.json";
                server = "https://api.ebay.com/commerce/taxonomy/v1";
                break;
            case "commerce_translation":
                spec = "commerce_translation_v1_beta_oas3.json";
                server = "https://api.ebay.com/commerce/translation/v1_beta";
                break;
            case "developer_analytics":
                spec = "developer_analytics_v1_beta_oas3.json";
                server = "https://api.ebay.com/developer/analytics/v1_beta";
                break;
        }

        // If sandbox environment, change url
        String serverType = connectionProperties.getProperty("serverType");
        if (!StringUtils.isBlank(serverType) && serverType.equals("sandbox")) {
            server = server.substring(0, 11) + ".sandbox" + server.substring(11);
        }

        String overloadedSpec = context.getConnectionProperties().getProperty("spec");
        if (!StringUtils.isBlank(overloadedSpec))
            spec = overloadedSpec;

        String overloadedServer = context.getConnectionProperties().getProperty("url");
        if (!StringUtils.isBlank(overloadedServer))
            server = overloadedServer;
    }

    public String getServer() {
        return server;
    }

    public String getSpec() {
        return spec;
    }
}
