package ee.note_parnu.climate_monitoring_system.test;

import ee.note_parnu.climate_monitoring_system.test.xml.domain.Reading;
import ee.note_parnu.climate_monitoring_system.test.xml.domain.ReadingsData;
import ee.note_parnu.climate_monitoring_system.test.xml.parse.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        final String xmlDir = "C:\\Users\\johan\\Git\\climate_monitoring_system_ver1\\src\\ee\\note_parnu\\climate_monitoring_system\\test\\test_scripts\\generated_xmls";
        XMLParser xmlParser = new XMLParser();

        ReadingsData xmlDirectoryData = xmlParser.parseDirectory(xmlDir);

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

    }
}
