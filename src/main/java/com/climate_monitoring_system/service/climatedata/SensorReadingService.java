package com.climate_monitoring_system.service.climatedata;

import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.climatedata.SensorReading;
import com.climate_monitoring_system.dto.climatedata.SensorReadingDTO;
import com.climate_monitoring_system.repository.climatedata.SensorReadingRepository;
import com.climate_monitoring_system.repository.climatedata.SensorReadingSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorReadingService {
    private final SensorReadingRepository sensorReadingRepository;
    private final SensorService sensorService;

    public SensorReadingDTO getSensorReadingDTOById(long id) {
        Optional<SensorReading> sensorReading = sensorReadingRepository.findById(id);

        return checkIfSensorReadingPresentGetDTO(sensorReading);
    }

    public List<SensorReadingDTO> getAllSensorReadingDTOs() {
        List<SensorReading> sensorReadings = sensorReadingRepository.findAll();

        return sensorReadingsToSensorReadingDTOs(sensorReadings);
    }

    public List<SensorReadingDTO> getReadingDTOsBySensorId(long sensorId) {
        Sensor sensor = sensorService.getSensorById(sensorId);
        Specification<SensorReading> spec = SensorReadingSpecification.hasSensor(sensor);
        return getSensorDTOsBySpec(spec);
    }

    private SensorReadingDTO checkIfSensorReadingPresentGetDTO(Optional<SensorReading> optionalReading) {
        return optionalReading.map(this::getSensorReadingDTO).orElseGet(SensorReadingDTO::new);
    }

    private SensorReadingDTO getSensorReadingDTO(SensorReading sensorReading) {
        SensorReadingDTO sensorReadingDTO = new SensorReadingDTO();
        sensorReadingDTO.setSensorReadingId(sensorReading.getSensorReadingId());
        sensorReadingDTO.setTemperature(sensorReading.getTemperature());
        sensorReadingDTO.setRelHumidity(sensorReading.getRelHumidity());
        sensorReadingDTO.setReadingTime(sensorReading.getReadingTime());
        sensorReadingDTO.setSensor(sensorService.getSensorDTO(sensorReading.getSensor()));

        return sensorReadingDTO;
    }

    private List<SensorReadingDTO> getSensorDTOsBySpec(Specification<SensorReading> spec) {
        List<SensorReading> sensorReadings = sensorReadingRepository.findAll(spec);

        return sensorReadingsToSensorReadingDTOs(sensorReadings);
    }

    private List<SensorReadingDTO> sensorReadingsToSensorReadingDTOs(List<SensorReading> sensorReadings) {
        List<SensorReadingDTO> sensorReadingDTOs = new ArrayList<>();

        for (SensorReading sensorReading : sensorReadings) {
            sensorReadingDTOs.add(getSensorReadingDTO(sensorReading));
        }

        return sensorReadingDTOs;
    }
}
