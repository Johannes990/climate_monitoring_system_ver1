package com.climate_monitoring_system.controller.climatedata;

import com.climate_monitoring_system.dto.climatedata.SensorDTO;
import com.climate_monitoring_system.service.climatedata.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/sensors/{id}")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable long id) {
        SensorDTO sensor = sensorService.getSensorDTOById(id);

        return ResponseEntity.ok(sensor);
    }

    @GetMapping("/sensors/all")
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        List<SensorDTO> sensors = sensorService.getAllSensorDTOs();

        return ResponseEntity.ok(sensors);
    }

    @GetMapping("/sensors/bypasskey/{passKey}")
    public ResponseEntity<SensorDTO> getSensorByPassKey(@PathVariable String passKey) {
        SensorDTO sensor = sensorService.getSensorDTOByPassKey(passKey);

        return ResponseEntity.ok(sensor);
    }

    @GetMapping("/sensors/bylocation/{id}")
    public ResponseEntity<List<SensorDTO>> getAllSensorsByLocationId(@PathVariable long id) {
        List<SensorDTO> sensors = sensorService.getAllSensorDTOsByLocationId(id);

        return ResponseEntity.ok(sensors);
    }

    @GetMapping("/sensors/bydevicecode/{deviceCode}")
    public ResponseEntity<List<SensorDTO>> getAllSensorsByDeviceCode(@PathVariable String deviceCode) {
        List<SensorDTO> sensors = sensorService.getAllSensorDTOsByDeviceCode(deviceCode);

        return ResponseEntity.ok(sensors);
    }

    @GetMapping("/sensors/bylocation/{id}/bydevicecode/{deviceCode}")
    public ResponseEntity<List<SensorDTO>> getSensorsByLocationIdAndDeviceCode(@PathVariable long id,
                                                                               @PathVariable String deviceCode) {
        List<SensorDTO> sensors = sensorService.getAllSensorDTOsByLocationIdAndDeviceCode(id, deviceCode);

        return ResponseEntity.ok(sensors);
    }
}
