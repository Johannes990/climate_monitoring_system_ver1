package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
import com.climate_monitoring_system.domain.climatedata.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@FunctionalInterface
interface LocationAssertion {
    void apply(Location location, int index);
}

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LocationRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ControlParameterSetRepository controlParameterSetRepository;
    @Autowired
    private TestEntityManager entityManager;

    List<Location> locations;
    final List<String> locationDescriptions = Arrays.asList("Saal 1", "SMT Fridge", "EPO", "Saal 2");
    final List<Float> temperatureValues = Arrays.asList(22.0f, 8.0f, 30.0f, 22.0f);
    final List<Float> tempToleranceValues = Arrays.asList(4.0f, 4.0f, 10.0f, 4.0f);
    final List<Float> relHumidityValues = Arrays.asList(45.0f, 50.0f, 50.0f, 45.0f);
    final List<Float> relHumidityToleranceValues = Arrays.asList(15.0f, 50.0f, 50.0f, 15.0f);
    final String locationDescription = "Kelder";
    final String newLocationDescription = "Teine Kelder";

    @BeforeEach
    public void setup() {
        locations = locationRepository.findAll();
    }

    @Test
    public void testLocationRepositoryNotNull() {
        assertThat(locationRepository).isNotNull();
    }

    @Test
    public void testLocationRepositoryHoldsDefaultLocations() {
        iterateAndAssertLocations(locations, assertLocationDescription());
    }

    @Test
    public void testLocationRepositoryHoldsCorrectControlParameters() {
        iterateAndAssertLocations(locations, assertControlParameterSet());
    }

    @Test
    public void testLocationRepositoryCrudOperations() {
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

    private void iterateAndAssertLocations(List<Location> locations, LocationAssertion assertion) {
        for (int i = 0; i < locations.size(); i++) {
            assertion.apply(locations.get(i), i);
        }
    }

    private LocationAssertion assertLocationDescription() {
        return (location, index) -> {
            assertThat(location.getLocationDescription()).isEqualTo(locationDescriptions.get(index));
        };
    }

    private LocationAssertion assertControlParameterSet() {
        return (location, index) -> {
            ControlParameterSet controlParameterSet = location.getControlParameterSet();
            assertThat(controlParameterSet.getTempNorm()).isEqualTo(temperatureValues.get(index));
            assertThat(controlParameterSet.getTempTolerance()).isEqualTo(tempToleranceValues.get(index));
            assertThat(controlParameterSet.getRelHumidityNorm()).isEqualTo(relHumidityValues.get(index));
            assertThat(controlParameterSet.getRelHumidityTolerance()).isEqualTo(relHumidityToleranceValues.get(index));
        };
    }
}
