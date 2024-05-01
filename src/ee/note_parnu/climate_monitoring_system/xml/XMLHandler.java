package ee.note_parnu.climate_monitoring_system.xml;

import ee.note_parnu.climate_monitoring_system.xml.domain.ReadingsData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
    private static final String SENSOR_SAMPLE = "InsertTx5xxSample";
    private static final String PASS_KEY = "PassKey";
    private static final String DEVICE = "device";
    private static final String TEMP = "temp";
    private static final String REL_HUM = "relHum";
    private static final String COMP_QUANT = "compQuant";
    private static final String PRESSURE = "pressure";
    private static final String ALARMS = "alarms";
    private static final String COMP_TYPE = "compType";
    private static final String TEMP_U = "tempU";
    private static final String PRESSURE_U = "pressureU";
    private static final String TIMER = "timer";

    private ReadingsData readingsData;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        readingsData = new ReadingsData();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {

        }
    }

}
