package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
import com.climate_monitoring_system.domain.climatedata.Location;
import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.climatedata.SensorReading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClimateDataTest {
    @Autowired
    ControlParameterSetRepository controlParameterSetRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    SensorRepository sensorRepository;
    @Autowired
    TestEntityManager entityManager;

    ControlParameterSet controlParameterSet;
    Location location;
    Sensor sensor;
    SensorReading sensorReading;
    SensorReading savedReading;
    final long parameterSetId = 1L;
    final float tempNorm = 22.0f;
    final float newTempNorm = 25.0f;
    final float tempTolerance = 4.0f;
    final float relHumidityNorm = 45.0f;
    final float newRelHumidityNorm = 50.0f;
    final float relHumidityTolerance = 15.0f;
    final long locationId = 4L;
    final String locationDescription = "Saal 2";
    final long sensorId = 2L;
    final String sensorPassKey = "15961868";
    final float tempReading = 26.0f;
    final float humidityReading = 33.9f;

    @BeforeEach
    public void setup() {
        controlParameterSet = controlParameterSetRepository.getReferenceById(parameterSetId);
        location = locationRepository.getReferenceById(locationId);
        sensor = sensorRepository.getReferenceById(sensorId);
        sensorReading = new SensorReading();

        sensorReading.setTemperature(tempReading);
        sensorReading.setRelHumidity(humidityReading);
        sensorReading.setSensor(sensor);
        savedReading = entityManager.persistAndFlush(sensorReading);
    }

    @Test
    public void testClimateDataStackRetrievesCorrectEntityData() {
        assertThat(savedReading.getSensorReadingId()).isNotNull();
        assertThat(savedReading.getTemperature()).isEqualTo(tempReading);
        assertThat(savedReading.getRelHumidity()).isEqualTo(humidityReading);

        Sensor savedSensor = savedReading.getSensor();
        assertThat(savedSensor.getPassKey()).isEqualTo(sensorPassKey);

        Location savedLocation = savedSensor.getLocation();
        assertThat(savedLocation).isEqualTo(location);
        assertThat(savedLocation.getLocationDescription()).isEqualTo(locationDescription);

        ControlParameterSet savedParameterSet = savedLocation.getControlParameterSet();
        assertThat(savedParameterSet).isEqualTo(controlParameterSet);
        assertThat(savedParameterSet.getTempNorm()).isEqualTo(tempNorm);
        assertThat(savedParameterSet.getTempTolerance()).isEqualTo(tempTolerance);
        assertThat(savedParameterSet.getRelHumidityNorm()).isEqualTo(relHumidityNorm);
        assertThat(savedParameterSet.getRelHumidityTolerance()).isEqualTo(relHumidityTolerance);
    }

    @Test
    public void testClimateDataStackChangesReflectInRetrievedData() {
        Sensor savedSensor = savedReading.getSensor();
        Location savedLocation = savedSensor.getLocation();
        ControlParameterSet savedParameterSet = savedLocation.getControlParameterSet();
        assertThat(savedParameterSet.getTempNorm()).isEqualTo(tempNorm);
        assertThat(savedParameterSet.getRelHumidityNorm()).isEqualTo(relHumidityNorm);

        savedParameterSet.setTempNorm(newTempNorm);
        savedParameterSet.setRelHumidityNorm(newRelHumidityNorm);
        controlParameterSetRepository.save(savedParameterSet);

        assertThat(entityManager.find(ControlParameterSet.class,
                savedReading.getSensor().getLocation().getControlParameterSet().getControlParameterSetId())
                .getTempNorm()).isEqualTo(newTempNorm);
        assertThat(entityManager.find(ControlParameterSet.class,
                savedReading.getSensor().getLocation().getControlParameterSet().getControlParameterSetId())
                .getRelHumidityNorm()).isEqualTo(newRelHumidityNorm);
    }
}
