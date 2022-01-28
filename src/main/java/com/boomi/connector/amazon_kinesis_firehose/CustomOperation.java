package com.boomi.connector.amazon_kinesis_firehose;

import com.boomi.connector.amazon_kinesis_firehose.CustomOperationConnection;
import com.boomi.connector.api.ObjectData;
import com.boomi.connector.openapi.OpenAPIOperation;
import com.boomi.util.LogUtil;
import org.apache.commons.io.IOUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomOperation extends OpenAPIOperation {

    private final String CONTENT_TYPE = "application/x-amz-json-1.1";
    private final String ALGORITHM = "AWS4-HMAC-SHA256";
    private final String SERVICE = "firehose";
    private String amzDate = "";

    private static final Logger LOG = LogUtil.getLogger(com.boomi.connector.amazon_kinesis.CustomOperation.class);


    protected CustomOperation(CustomOperationConnection connection) {
        super(connection);
    }

    /**
     * Adds custom headers from Connection page.
     *
     * @return Key value pairs of all headers in the request
     */

    @Override
    protected Iterable<Map.Entry<String, String>> getHeaders(ObjectData data) {
        Iterable<Map.Entry<String, String>> originalHeaders = super.getHeaders(data);

        Iterator<Map.Entry<String ,String>> originalHeaderIterator = originalHeaders.iterator();
        ArrayList<Map.Entry<String, String>> headerList = new ArrayList<>();

        //add other custom headers
        while (originalHeaderIterator.hasNext()) {
            headerList.add(originalHeaderIterator.next());
        }

        headerList.add(new AbstractMap.SimpleEntry<>("Content-Type", CONTENT_TYPE));

        amzDate = getAmzDate();
        headerList.add(new AbstractMap.SimpleEntry<>("X-Amz-Date", amzDate));

        try {
            headerList.add(new AbstractMap.SimpleEntry<>("Authorization", getAuthorizationHeader(data)));
        } catch (IOException | NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            LOG.log(Level.WARNING, e.toString());
        }


        return headerList;
    }

    private String getAuthorizationHeader(ObjectData data) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String method = this.getContext().getCustomOperationType();
        String host = this.getConnection().getUrl().getHost();
        String accessKey = this.getContext().getConnectionProperties().getProperty("awsAccessKey");
        String secretKey = this.getContext().getConnectionProperties().getProperty("awsSecretKey");
        String dateStamp = getDateStamp();
        String region = getRegion();

        String canonicalUri = this.getConnection().getUrl().getPath();
        if (Objects.equals(canonicalUri, "")) {
            canonicalUri = "/";
        }

        String canonicalHeaders = "content-type:" + CONTENT_TYPE + "\n"
                + "host:" + host + "\n"
                + "x-amz-date:" + amzDate + "\n"
                + "x-amz-target:" + getAmzTarget(data) + "\n";

        String signedHeaders = "content-type;host;x-amz-date;x-amz-target";

        String canonicalRequest = method + "\n"
                + canonicalUri + "\n"
                + getCanonicalQueryString(data) + "\n"
                + canonicalHeaders + "\n"
                + signedHeaders + "\n"
                + getPayloadHash(data);

        String credentialScope = dateStamp + "/"
                + region + "/"
                + SERVICE + "/"
                + "aws4_request";

        String hashedCanonicalRequest = createHash(canonicalRequest);

        String stringToSign = ALGORITHM + "\n"
                + amzDate + "\n"
                + credentialScope + "\n"
                + hashedCanonicalRequest;

        byte[] signatureKey = getSignatureKey(secretKey, dateStamp, region, SERVICE);

        String signature = toHexString(calculateHMAC(signatureKey, stringToSign));

        String authorizationHeader = ALGORITHM + " Credential=" + accessKey + "/"
                + credentialScope + ", "
                + "SignedHeaders=" + signedHeaders + ", "
                + "Signature=" + signature;
        return authorizationHeader;
    }

    private String getRegion() {
        String url = this.getContext().getConnectionProperties().getProperty("url");
        String region = url.substring(url.indexOf(".") + 1);
        region = region.substring(0, region.indexOf("."));
        return region;
    }

    private String getAmzTarget(ObjectData data) {
        String amzTarget = "";
        Iterator <Map.Entry<String, String>> headerIterator = super.getHeaders(data).iterator();
        while (headerIterator.hasNext()) {
            Map.Entry<String, String> header = headerIterator.next();
            if (header.getKey().equals("X-Amz-Target")) {
                amzTarget = header.getValue();
            }
        }
        return amzTarget;
    }

    private String getCanonicalQueryString(ObjectData data) {
        String canonicalQueryString = "";
        Iterator<Map.Entry<String, String>> parametersIterator = this.getParameters(data).iterator();
        while (parametersIterator.hasNext()) {
            Map.Entry<String, String> parameter = parametersIterator.next();
            canonicalQueryString += parameter.getKey() + "=" + parameter.getValue();
            if (parametersIterator.hasNext())
                canonicalQueryString += "&";
        }
        return canonicalQueryString;
    }

    private byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] kDate = calculateHMAC(("AWS4" + key).getBytes(StandardCharsets.UTF_8), dateStamp);
        byte[] kRegion = calculateHMAC(kDate, regionName);
        byte[] kService = calculateHMAC(kRegion, serviceName);
        byte[] kSigning = calculateHMAC(kService, "aws4_request");
        return kSigning;
    }

    private String getAmzDate() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyyMMdd'T'HHmmss'Z'")
                .withZone(ZoneId.from(ZoneOffset.UTC));
        return formatter.format(Instant.now());
    }

    private String getDateStamp() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyyMMdd")
                .withZone(ZoneId.from(ZoneOffset.UTC));
        return formatter.format(Instant.now());
    }

    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    private byte[] calculateHMAC(byte[] key, String data)
            throws NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        return mac.doFinal(data.getBytes());
    }

    private String getPayloadHash(ObjectData data) throws NoSuchAlgorithmException, IOException {
        String payload = IOUtils.toString(super.getEntity(data).getContent(), StandardCharsets.UTF_8);
        return createHash(payload);
    }

    private String createHash(String stringToHash) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));
        String hexString = toHexString(encodedHash);
        return hexString;
    }
}
