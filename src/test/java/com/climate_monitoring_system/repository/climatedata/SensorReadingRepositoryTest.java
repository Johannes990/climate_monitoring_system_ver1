package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.climatedata.SensorReading;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SensorReadingRepositoryTest {
    @Autowired
    private SensorReadingRepository sensorReadingRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private TestEntityManager entityManager;
    final float tempReading = 24.2f;
    final float newTempReading = 23.9f;
    final float humidityReading = 57.4f;
    final float newHumidityReading = 58.0f;

    @Test
    public void testSensorReadingRepositoryNotNull() {
        assertThat(sensorReadingRepository).isNotNull();
    }

    @Test
    public void testSensorReadingRepositoryCrudOperations() {
        final Sensor sensor = sensorRepository.getReferenceById(1L);
        final SensorReading sensorReading = new SensorReading();
        sensorReading.setTemperature(tempReading);
        sensorReading.setRelHumidity(humidityReading);
        sensorReading.setSensor(sensor);

        SensorReading savedReading = entityManager.persistAndFlush(sensorReading);
        entityManager.refresh(savedReading); // refresh gets the database applied timestamp into memory
        assertThat(savedReading.getSensorReadingId()).isNotNull();
        assertThat(savedReading.getReadingTime()).isNotNull();

        savedReading.setTemperature(newTempReading);
        savedReading.setRelHumidity(newHumidityReading);
        sensorReadingRepository.save(savedReading);

        assertThat(entityManager.find(SensorReading.class,
                savedReading.getSensorReadingId()).getTemperature()).isEqualTo(newTempReading);
        assertThat(entityManager.find(SensorReading.class,
                savedReading.getSensorReadingId()).getRelHumidity()).isEqualTo(newHumidityReading);

        sensorReadingRepository.delete(savedReading);
        assertThat(entityManager.find(SensorReading.class, savedReading.getSensorReadingId())).isNull();
    }

    @Test
    public void testSensorReadingRepositorySensorReadingNotNullAfterSensorDelete() {
        final long sensorId = 2L;
        final Sensor sensor = sensorRepository.getReferenceById(sensorId);
        final SensorReading sensorReading = new SensorReading();
        sensorReading.setTemperature(tempReading);
        sensorReading.setRelHumidity(humidityReading);
        sensorReading.setSensor(sensor);

        SensorReading savedReading = entityManager.persistAndFlush(sensorReading);
        assertThat(savedReading.getSensor().getPassKey()).isEqualTo("15961868");
        assertThat(savedReading.getSensor()).isNotNull();
        assertThat(entityManager.find(Sensor.class,
                sensorRepository.getReferenceById(sensorId).getSensorId())).isNotNull();

        sensorRepository.delete(sensor);
        entityManager.refresh(savedReading);

        assertThat(entityManager.find(Sensor.class,
                sensorRepository.getReferenceById(sensorId).getSensorId())).isNull();
        assertThat(savedReading.getSensorReadingId()).isNotNull();
    }
}
