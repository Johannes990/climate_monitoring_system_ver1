package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
import com.climate_monitoring_system.domain.climatedata.Location;
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
public class LocationRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ControlParameterSetRepository controlParameterSetRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testLocationRepositoryNotNull() {
        assertThat(locationRepository).isNotNull();
    }

    @Test
    public void testLocationRepositoryHoldsDefaultLocations() {
        final List<String> locationDescriptions = Arrays.asList("Saal 1", "SMT Fridge", "EPO", "Saal 2");

        List<Location> locations = locationRepository.findAll();

        for (int i = 0; i < locationDescriptions.size(); i++) {
            Location location = locations.get(i);

            assertThat(location.getLocationDescription()).isEqualTo(locationDescriptions.get(i));
        }
    }

    @Test
    public void testLocationRepositoryHoldsCorrectControlParameters() {
        final List<Float> temperatureValues = Arrays.asList(22.0f, 8.0f, 30.0f, 22.0f);
        final List<Float> tempToleranceValues = Arrays.asList(4.0f, 4.0f, 10.0f, 4.0f);
        final List<Float> relHumidityValues = Arrays.asList(45.0f, 50.0f, 50.0f, 45.0f);
        final List<Float> relHumidityToleranceValues = Arrays.asList(15.0f, 50.0f, 50.0f, 15.0f);

        List<Location> locations = locationRepository.findAll();

        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            ControlParameterSet controlParameterSet = location.getControlParameterSet();

            assertThat(controlParameterSet.getTempNorm()).isEqualTo(temperatureValues.get(i));
            assertThat(controlParameterSet.getTempTolerance()).isEqualTo(tempToleranceValues.get(i));
            assertThat(controlParameterSet.getRelHumidityNorm()).isEqualTo(relHumidityValues.get(i));
            assertThat(controlParameterSet.getRelHumidityTolerance()).isEqualTo(relHumidityToleranceValues.get(i));
        }
    }

    @Test
    public void testLocationRepositoryCrudOperations() {
        final String locationDescription = "Kelder";
        final String newLocationDescription = "Teine Kelder";
        final ControlParameterSet parameterSet = controlParameterSetRepository.getReferenceById(1L);
        Location location = new Location();
        location.setLocationDescription(locationDescription);
        location.setControlParameterSet(parameterSet);

        Location savedLocation = entityManager.persistAndFlush(location);
        assertThat(savedLocation.getLocationId()).isNotNull();

        savedLocation.setLocationDescription(newLocationDescription);
        locationRepository.save(savedLocation);

        assertThat(entityManager.find(Location.class, savedLocation.getLocationId())
                .getLocationDescription()).isEqualTo(newLocationDescription);

        locationRepository.delete(savedLocation);
        assertThat(entityManager.find(Location.class, savedLocation.getLocationId())).isNull();
    }
}
