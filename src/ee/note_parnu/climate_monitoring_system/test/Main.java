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
        final String xmlDir = "C:\\Users\\johan\\Git\\climate_monitoring_system_ver1\\src\\ee\\note_parnu\\climate_monitoring_system\\test\\test_scripts\\generated_xmls";
        final String testXmlPath = "C:\\Users\\johan\\Git\\climate_monitoring_system_ver1\\src\\ee\\note_parnu\\climate_monitoring_system\\test\\test_scripts\\generated_xmls\\3510_15961202_0.xml";

        XMLParser xmlParser = new XMLParser();
        XMLParser xmlParser1 = new XMLParser();

        ReadingsData xmlDirectoryData = xmlParser.parseDirectory(xmlDir);
        xmlParser1.parseXml(testXmlPath);
        ReadingsData singleXmlData = xmlParser1.getXMLData();
        Reading singleReading = singleXmlData.getReadingList().get(0);

        assert (Objects.nonNull(xmlDirectoryData));

        List<Reading> readingsList = xmlDirectoryData.getReadingList();

        for (int i = 0; i < readingsList.size(); i++) {
            Reading reading = readingsList.get(i);
            System.out.println("\nreadings list: " + i);
            System.out.println("pass key: " + reading.getPassKey());
            System.out.println("temperature: " + reading.getTemp());
            System.out.println("humidity: " + reading.getRelHum());
            System.out.println("compQuant: " + reading.getCompQuant());
            System.out.println("pressure: " + reading.getPressure());
            System.out.println("alarms: " + reading.getAlarms());
            System.out.println("compType: " + reading.getCompType());
            System.out.println("tempU: " + reading.getTempU());
            System.out.println("pressureU: " + reading.getPressureU());
            System.out.println("timer: " + reading.getTimer());

        }

        System.out.println("Single xml data read");
        System.out.println("pass key: " + singleReading.getPassKey());
        System.out.println("temperature: " + singleReading.getTemp());
        System.out.println("humidity: " + singleReading.getRelHum());
        System.out.println("compQuant: " + singleReading.getCompQuant());
        System.out.println("pressure: " + singleReading.getPressure());
        System.out.println("alarms: " + singleReading.getAlarms());
        System.out.println("compType: " + singleReading.getCompType());
        System.out.println("tempU: " + singleReading.getTempU());
        System.out.println("pressureU: " + singleReading.getPressureU());
        System.out.println("timer: " + singleReading.getTimer());
    }
}
