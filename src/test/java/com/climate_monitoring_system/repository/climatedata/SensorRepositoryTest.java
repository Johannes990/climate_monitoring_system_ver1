package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.Location;
import com.climate_monitoring_system.domain.climatedata.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SensorRepositoryTest {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSensorRepositoryNotNull() {
        assertThat(sensorRepository).isNotNull();
    }

    @Test
    public void testSensorRepositoryHoldsDefaultSensors() {
        final List<String> locationDescriptions = Arrays.asList("Saal 1", "Saal 2", "SMT Fridge", "EPO");
        final List<String> sensorPassKeys = Arrays.asList("15961202", "15961868", "18961817", "18941697");

        List<Sensor> sensors = sensorRepository.findAll();

        for (int i = 0; i < sensors.size(); i++) {
            Sensor sensor = sensors.get(i);

            assertThat(sensor.getPassKey()).isEqualTo(sensorPassKeys.get(i));
            assertThat(sensor.getLocation().getLocationDescription()).isEqualTo(locationDescriptions.get(i));
        }
    }

    @Test
    public void testSensorRepositoryCrudOperations() {
        final String passKey = "11112222";
        final String newPassKey = "22223333";
        final String deviceCode = "3510";
        final String newDeviceCode = "5100xx";
        final String tempUnit = "C";
        final String newTempUnit = "F";
        final Location initialLocation = locationRepository.getReferenceById(1L);
        final Location newLocation = locationRepository.getReferenceById(2L);
        Sensor sensor = new Sensor();
        sensor.setPassKey(passKey);
        sensor.setDeviceCode(deviceCode);
        sensor.setTempUnit(tempUnit);
        sensor.setLocation(initialLocation);

        Sensor savedSensor = entityManager.persistAndFlush(sensor);
        assertThat(savedSensor.getPassKey()).isNotNull();

        savedSensor.setPassKey(newPassKey);
        savedSensor.setDeviceCode(newDeviceCode);
        savedSensor.setTempUnit(newTempUnit);
        savedSensor.setLocation(newLocation);
        sensorRepository.save(savedSensor);

        assertThat(entityManager.find(Sensor.class, savedSensor.getSensorId()).getPassKey()).isEqualTo(newPassKey);
        assertThat(entityManager.find(Sensor.class, savedSensor.getSensorId()).getDeviceCode()).isEqualTo(newDeviceCode);
        assertThat(entityManager.find(Sensor.class, savedSensor.getSensorId()).getTempUnit()).isEqualTo(newTempUnit);
        assertThat(entityManager.find(Sensor.class, savedSensor.getSensorId()).getLocation()).isEqualTo(newLocation);

        sensorRepository.delete(savedSensor);
        assertThat(entityManager.find(Sensor.class, savedSensor.getSensorId())).isNull();
    }
}
