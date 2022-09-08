package com.boomi.connector.zoom_video_sdk;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ValidateXMLTest {

    @Test
    public void validateConnectorConfig() {
        String xml = "/connector-config-zoom_video_sdk.xml";
        String xsd = "/genericconnector.xsd";
        try {
            validateAgainstXSD(xml, xsd);
        } catch (Exception e) {
            Assert.fail("Exception: " + e);
        }
    }

    @Test
    public void validateConnectorDescriptor() {
        String xml = "/connector-descriptor-zoom_video_sdk.xml";
        String xsd = "/genericconnectordesc.xsd";
        try {
            validateAgainstXSD(xml, xsd);
        } catch (Exception e) {
            Assert.fail("Exception: " + e);
        }
    }

    void validateAgainstXSD(String xmlPath, String xsdPath) throws IOException, SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        InputStream xmlStream = this.getClass().getResourceAsStream(xmlPath);

        URL xsdUrl = this.getClass().getResource(xsdPath);

        Schema schema = factory.newSchema(xsdUrl);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xmlStream));

    }
}
