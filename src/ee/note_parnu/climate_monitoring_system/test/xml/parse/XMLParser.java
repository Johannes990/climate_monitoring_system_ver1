package ee.note_parnu.climate_monitoring_system.test.xml.parse;

import ee.note_parnu.climate_monitoring_system.test.xml.XMLHandler;
import ee.note_parnu.climate_monitoring_system.test.xml.domain.ReadingsData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.File;
import java.util.Objects;

public class XMLParser {
    SAXParserFactory factory;
    SAXParser saxParser;
    XMLHandler xmlHandler;

    public XMLParser() throws ParserConfigurationException, SAXException {
        factory = SAXParserFactory.newInstance();
        saxParser = factory.newSAXParser();
        xmlHandler = new XMLHandler();
    }

    public void parseXml(String path) throws IOException, SAXException {
        saxParser.parse(path, xmlHandler);
    }

    public ReadingsData getXMLData() {
        return xmlHandler.getReadings();
    }

    public ReadingsData parseDirectory(String dirPath) {
        File directory = new File(dirPath);
        ReadingsData dirData = new ReadingsData();

        if (!directory.isDirectory()) {
            System.out.println("Provided path is not a directory");
        }

        File[] files = directory.listFiles();

        if (Objects.nonNull(files)) {
            for (File file: files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".xml")) {
                    handleXMLFile(file, dirData);
                }
            }
        }

        return dirData;
    }

    private void handleXMLFile(File file, ReadingsData directoryData) {
        System.out.println(file.getName());
        try {
            SAXParserFactory localFactory = SAXParserFactory.newInstance();
            SAXParser localSaxParser = localFactory.newSAXParser();
            localSaxParser.parse(file, xmlHandler);
            ReadingsData fileData = xmlHandler.getReadings();
            directoryData.getReadingList().addAll(fileData.getReadingList());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
