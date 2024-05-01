package ee.note_parnu.climate_monitoring_system.xml.domain;

import java.util.ArrayList;
import java.util.List;

public class ReadingsData {
    private List<Reading> readingList;

    public List<Reading> getReadingList() {
        return this.readingList;
    }

    public void addReading(Reading reading) {
        this.readingList.add(reading);
    }
}
