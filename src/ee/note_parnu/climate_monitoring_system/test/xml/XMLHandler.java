package ee.note_parnu.climate_monitoring_system.test.xml;

import ee.note_parnu.climate_monitoring_system.test.xml.domain.Reading;
import ee.note_parnu.climate_monitoring_system.test.xml.domain.ReadingsData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLHandler extends DefaultHandler {
    private static final String SENSOR_SAMPLE = "InsertTx5xxSample";
    private static final String PASS_KEY = "passKey";
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
    private Reading currentReading;

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
        readingsData.setReadingList(new ArrayList<>());
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case SENSOR_SAMPLE:
                currentReading = new Reading();
                break;
            default:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case SENSOR_SAMPLE:
                readingsData.getReadingList().add(currentReading);
                break;
            case PASS_KEY:
                currentReading.setPassKey(elementValue.toString());
                break;
            case DEVICE:
                currentReading.setDevice(elementValue.toString());
                break;
            case TEMP:
                currentReading.setTemp(elementValue.toString());
                break;
            case REL_HUM:
                currentReading.setRelHum(elementValue.toString());
                break;
            case COMP_QUANT:
                currentReading.setCompQuant(elementValue.toString());
                break;
            case PRESSURE:
                currentReading.setPressure(elementValue.toString());
                break;
            case ALARMS:
                currentReading.setAlarms(elementValue.toString());
                break;
            case COMP_TYPE:
                currentReading.setCompType(elementValue.toString());
                break;
            case TEMP_U:
                currentReading.setTempU(elementValue.toString());
                break;
            case PRESSURE_U:
                currentReading.setPressureU(elementValue.toString());
                break;
            case TIMER:
                currentReading.setTimer(elementValue.toString());
                break;
            default:
                break;
        }
    }

    public ReadingsData getReadings() {
        return this.readingsData;
    }

}
