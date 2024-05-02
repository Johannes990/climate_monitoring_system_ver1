package ee.note_parnu.climate_monitoring_system.test.xml.parse;

import ee.note_parnu.climate_monitoring_system.test.xml.XMLHandler;
import ee.note_parnu.climate_monitoring_system.test.xml.domain.ReadingsData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class XMLParser {
    SAXParserFactory factory;
    SAXParser saxParser;
    XMLHandler xmlHandler;

    public XMLParser() throws ParserConfigurationException, SAXException {
        factory = SAXParserFactory.newInstance();
        saxParser = factory.newSAXParser();
        xmlHandler = new XMLHandler();
    }

    public ReadingsData parseXml(String path) throws IOException, SAXException {
        saxParser.parse(path, xmlHandler);
        return xmlHandler.getReadings();
    }
}
