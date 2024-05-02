package ee.note_parnu.climate_monitoring_system.test;

import ee.note_parnu.climate_monitoring_system.test.xml.domain.Reading;
import ee.note_parnu.climate_monitoring_system.test.xml.domain.ReadingsData;
import ee.note_parnu.climate_monitoring_system.test.xml.parse.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        String xmlPath = "C:\\Users\\johan\\Git\\climate_monitoring_system_ver1\\src\\ee\\note_parnu\\climate_monitoring_system\\test\\test_scripts\\generated_xmls\\3510_15961202_3.xml";
        XMLParser xmlParser = new XMLParser();
        ReadingsData data = xmlParser.parseXml(xmlPath);

        assert (Objects.nonNull(data));

        List<Reading> readingsList = data.getReadingList();
        Reading reading = readingsList.get(0);
        System.out.println("readings list:");
        System.out.println("pass key: " + reading.getPassKey());
        System.out.println("temperature: " + reading.getTemp());
        System.out.println("humidity: " + reading.getRelHum());
    }
}
