package com.boomi.connector.ebay;

import com.boomi.connector.api.ConnectorException;
import com.boomi.connector.api.OAuth2Context;
import com.boomi.connector.api.OperationContext;
import com.boomi.connector.api.PropertyMap;
import com.boomi.connector.openapi.OpenAPIOperationConnection;
import com.boomi.util.LogUtil;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomOperationConnection extends OpenAPIOperationConnection {

    private static final Logger LOG = LogUtil.getLogger(com.boomi.connector.blackboard.CustomOperationConnection.class);

    public CustomOperationConnection(OperationContext context) {
        super(context);
    }

    public URL getUrl() {
        try {
            CustomContext customContext = new CustomContext(getContext());
            String url = customContext.getServer();
            return new URL(url);
        } catch (Exception e) {
            throw new ConnectorException(e);
        }
    }

    @Override
    public String getCustomAuthCredentials() {
        return "Bearer " + getBearerToken();
    }

    private String getBearerToken() {
        ConcurrentMap cache = getContext().getConnectorCache();
        if (cache.containsKey("bearer_token")) {
            long currentTime = Instant.now().getEpochSecond();
            String expireTime = String.valueOf(cache.getOrDefault("expire_time", "0"));
            if (StringUtils.isBlank(expireTime))
                expireTime = "0";
            if (currentTime < Long.parseLong(expireTime)) {
                String scope = getContext().getConnectionProperties().getProperty("scope");
                if (StringUtils.isBlank(scope))
                    scope = "";
                String savedScope = String.valueOf(cache.getOrDefault("scope", ""));
                if (scope.equals(savedScope))
                    return String.valueOf(cache.get("bearer_token"));
            }
        }

        // Use oauth context to make an api call to the token endpoint
        BufferedReader reader = null;
        HttpsURLConnection connection = null;
        String bearerToken = "";
        String expireTime = "";
        Pattern accessTokenPattern = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");
        Pattern expiresInPattern = Pattern.compile(".*\"expires_in\"\\s*:\\s*(.+),.*");
        try {
            String clientId =  getContext().getConnectionProperties().getProperty("clientId");
            String clientSecret = getContext().getConnectionProperties().getProperty("clientSecret");
            String tokenUrl = getContext().getConnectionProperties().getProperty("accessTokenUrl");
            String scope = getContext().getConnectionProperties().getProperty("scope");
            scope = percentEncode(scope);

            String content = "grant_type=client_credentials&scope=" + scope;

            URL url = new URL(tokenUrl);
            String authString = clientId + ":" + clientSecret;
            String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
            PrintStream os = new PrintStream(connection.getOutputStream());
            os.print(content);
            os.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringWriter out = new StringWriter(connection.getContentLength() > 0 ? connection.getContentLength() : 2048);
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String response = out.toString();

            // Get bearer token
            Matcher matcher = accessTokenPattern.matcher(response);
            if (matcher.matches() && matcher.groupCount() > 0) {
                bearerToken = matcher.group(1);
            }

            // Get expire time
            matcher = expiresInPattern.matcher(response);
            if (matcher.matches() && matcher.groupCount() > 0) {
                int expires_in = Integer.parseInt(matcher.group(1));
                Instant expireInstant = Instant.now().plusSeconds(expires_in);
                expireTime = String.valueOf(expireInstant.getEpochSecond());
            }

            cache.remove("bearer_token");
            cache.remove("expire_time");
            cache.put("bearer_token", bearerToken);
            cache.put("expire_time", expireTime);
            cache.put("scope", scope);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to get OAuth2 access token", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.log(Level.WARNING, "Unable to close buffered reader for token api call", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return bearerToken;
    }

    /**
     * Percent-encodes a string so it's suitable for use in a URL Path (not a query string / form encode, which uses + for spaces, etc)
     */
    public static String percentEncode(String encodeMe) {
        if (encodeMe == null) {
            return "";
        }
        String encoded = encodeMe.replace("%", "%25");
        encoded = encoded.replace(" ", "%20");
        encoded = encoded.replace("!", "%21");
        encoded = encoded.replace("#", "%23");
        encoded = encoded.replace("$", "%24");
        encoded = encoded.replace("&", "%26");
        encoded = encoded.replace("'", "%27");
        encoded = encoded.replace("(", "%28");
        encoded = encoded.replace(")", "%29");
        encoded = encoded.replace("*", "%2A");
        encoded = encoded.replace("+", "%2B");
        encoded = encoded.replace(",", "%2C");
        encoded = encoded.replace("/", "%2F");
        encoded = encoded.replace(":", "%3A");
        encoded = encoded.replace(";", "%3B");
        encoded = encoded.replace("=", "%3D");
        encoded = encoded.replace("?", "%3F");
        encoded = encoded.replace("@", "%40");
        encoded = encoded.replace("[", "%5B");
        encoded = encoded.replace("]", "%5D");
        return encoded;
    }

}
