package com.climate_monitoring_system.service.climatedata;

import com.climate_monitoring_system.domain.climatedata.Location;
import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.dto.climatedata.SensorDTO;
import com.climate_monitoring_system.repository.climatedata.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;
    private final LocationService locationService;

    public SensorDTO getSensorDTOById(long sensorId) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);

        return checkIfSensorPresentAndGetSensorDTO(sensor);
    }

    public Sensor getSensorById(long sensorId) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorId);

        return sensor.orElseGet(Sensor::new);
    }

    public List<SensorDTO> getAllSensorDTOs() {
        List<Sensor> sensors = sensorRepository.findAll();

        return sensorsToSensorDTOs(sensors);
    }

    public SensorDTO getSensorDTOByPassKey(String passKey) {
        Optional<Sensor> sensor = sensorRepository.findByPassKey(passKey);

        return checkIfSensorPresentAndGetSensorDTO(sensor);
    }

    public List<SensorDTO> getAllSensorDTOsByLocationId(long id) {
        Location location = locationService.getLocationById(id);
        List<Sensor> sensors = sensorRepository.findAllByLocation(location);

        return sensorsToSensorDTOs(sensors);
    }

    public List<SensorDTO> getAllSensorDTOsByDeviceCode(String deviceCode) {
        List<Sensor> sensors = sensorRepository.findAllByDeviceCode(deviceCode);

        return sensorsToSensorDTOs(sensors);
    }

    public List<SensorDTO> getAllSensorDTOsByLocationIdAndDeviceCode(long locationId, String deviceCode) {
        Location location = locationService.getLocationById(locationId);
        List<Sensor> sensors = sensorRepository.findAllByLocationAndDeviceCode(location, deviceCode);

        return sensorsToSensorDTOs(sensors);
    }

    private List<SensorDTO> sensorsToSensorDTOs(List<Sensor> sensors) {
        List<SensorDTO> sensorDTOs = new ArrayList<>();

        for (Sensor sensor : sensors) {
            sensorDTOs.add(getSensorDTO(sensor));
        }

        return sensorDTOs;
    }

    private SensorDTO checkIfSensorPresentAndGetSensorDTO(Optional<Sensor> optionalSensor) {
        return optionalSensor.map(this::getSensorDTO).orElseGet(SensorDTO::new);
    }

    public SensorDTO getSensorDTO(Sensor sensor) {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setSensorId(sensor.getSensorId());
        sensorDTO.setPassKey(sensor.getPassKey());
        sensorDTO.setDeviceCode(sensor.getDeviceCode());
        sensorDTO.setTempUnit(sensor.getTempUnit());
        sensorDTO.setLocation(locationService.getLocationDTO(sensor.getLocation()));

        return sensorDTO;
    }
}
