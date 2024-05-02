package ee.note_parnu.climate_monitoring_system.xml;

import ee.note_parnu.climate_monitoring_system.xml.domain.Reading;
import ee.note_parnu.climate_monitoring_system.xml.domain.ReadingsData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

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
            case SENSOR_SAMPLE:
                readingsData.setReadingList(new ArrayList<>());
                break;
            case PASS_KEY, DEVICE, TEMP, REL_HUM, COMP_QUANT, PRESSURE, ALARMS, COMP_TYPE, TEMP_U, PRESSURE_U, TIMER:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case PASS_KEY:
                getNthReading(0).setPassKey(elementValue.toString());
                break;
            case DEVICE:
                getNthReading(0).setDevice(elementValue.toString());
                break;
            case TEMP:
                getNthReading(0).setTemp(elementValue.toString());
                break;
            case REL_HUM:
                getNthReading(0).setRelHum(elementValue.toString());
                break;
            case COMP_QUANT:
                getNthReading(0).setCompQuant(elementValue.toString());
                break;
            case PRESSURE:
                getNthReading(0).setPressure(elementValue.toString());
                break;
            case ALARMS:
                getNthReading(0).setAlarms(elementValue.toString());
                break;
            case COMP_TYPE:
                getNthReading(0).setCompType(elementValue.toString());
                break;
            case TEMP_U:
                getNthReading(0).setTempU(elementValue.toString());
                break;
            case PRESSURE_U:
                getNthReading(0).setPressureU(elementValue.toString());
                break;
            case TIMER:
                getNthReading(0).setTimer(elementValue.toString());
                break;
        }
    }

    private Reading getNthReading(int n) {
        return readingsData.getReadingList().get(n);
    }

    public ReadingsData getReadings() {
        return this.readingsData;
    }

}
