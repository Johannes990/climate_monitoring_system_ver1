package com.climate_monitoring_system.repository.climatedata;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
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
public class ControlParameterSetRepositoryTest {
    @Autowired
    private ControlParameterSetRepository controlParameterSetRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testControlParameterSetRepositoryNotNull() {
        assertThat(controlParameterSetRepository).isNotNull();
    }

    @Test
    public void testControlParameterSetRepositoryHoldsDefaultValues() {
        final List<Float> temperatureValues = Arrays.asList(22.0f, 8.0f, 30.0f);
        final List<Float> tempToleranceValues = Arrays.asList(4.0f, 4.0f, 10.0f);
        final List<Float> relHumidityValues = Arrays.asList(45.0f, 50.0f, 50.0f);
        final List<Float> relHumidityToleranceValues = Arrays.asList(15.0f, 50.0f, 50.0f);

        List<ControlParameterSet> controlParameters = controlParameterSetRepository.findAll();

        for (int i = 0; i < controlParameters.size(); i++) {
            ControlParameterSet controlParameterSet = controlParameters.get(i);

            assertThat(controlParameterSet.getTempNorm()).isEqualTo(temperatureValues.get(i));
            assertThat(controlParameterSet.getTempTolerance()).isEqualTo(tempToleranceValues.get(i));
            assertThat(controlParameterSet.getRelHumidityNorm()).isEqualTo(relHumidityValues.get(i));
            assertThat(controlParameterSet.getRelHumidityTolerance()).isEqualTo(relHumidityToleranceValues.get(i));
        }
    }

    @Test
    public void testControlParameterSetRepositoryCrudOperations() {
        final float tempNorm = 10f;
        final float tempTolerance = 10f;
        final float relHumidityNorm = 50f;
        final float relHumidityTolerance = 10f;
        final float newTempNorm = 28.5f;
        final ControlParameterSet controlParameterSet = new ControlParameterSet();
        controlParameterSet.setTempNorm(tempNorm);
        controlParameterSet.setTempTolerance(tempTolerance);
        controlParameterSet.setRelHumidityNorm(relHumidityNorm);
        controlParameterSet.setRelHumidityTolerance(relHumidityTolerance);

        ControlParameterSet savedParameterSet = entityManager.persistAndFlush(controlParameterSet);
        assertThat(savedParameterSet.getControlParameterSetId()).isNotNull();
        assertThat(savedParameterSet.getTempNorm()).isEqualTo(tempNorm);
        assertThat(savedParameterSet.getTempTolerance()).isEqualTo(tempTolerance);
        assertThat(savedParameterSet.getRelHumidityNorm()).isEqualTo(relHumidityNorm);
        assertThat(savedParameterSet.getRelHumidityTolerance()).isEqualTo(relHumidityTolerance);

        savedParameterSet.setTempNorm(newTempNorm);
        controlParameterSetRepository.save(savedParameterSet);

        assertThat(entityManager.find(ControlParameterSet.class,
                savedParameterSet.getControlParameterSetId()).getTempNorm()).isEqualTo(newTempNorm);

        controlParameterSetRepository.delete(savedParameterSet);
        assertThat(entityManager.find(ControlParameterSet.class,
                savedParameterSet.getControlParameterSetId())).isNull();
    }
}
