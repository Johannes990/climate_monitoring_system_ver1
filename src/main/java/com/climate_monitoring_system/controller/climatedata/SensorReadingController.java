package com.climate_monitoring_system.controller.climatedata;

import com.climate_monitoring_system.dto.climatedata.SensorReadingDTO;
import com.climate_monitoring_system.service.climatedata.SensorReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SensorReadingController {
    private final SensorReadingService sensorReadingService;

    @GetMapping("/readings/{id}")
    public ResponseEntity<SensorReadingDTO> getSensorReadingById(@PathVariable int id) {
        SensorReadingDTO reading = sensorReadingService.getSensorReadingDTOById(id);
        return ResponseEntity.ok(reading);
    }

    @GetMapping("/readings/all")
    public ResponseEntity<List<SensorReadingDTO>> getAllSensorReadings() {
        List<SensorReadingDTO> readings = sensorReadingService.getAllSensorReadingDTOs();
        return ResponseEntity.ok(readings);
    }

    @GetMapping("/readings/bysensor/{sensorId}")
    public ResponseEntity<List<SensorReadingDTO>> getAllSensorReadingsBySensorId(@PathVariable int sensorId) {
        List<SensorReadingDTO> readings = sensorReadingService.getReadingDTOsBySensorId(sensorId);
        return ResponseEntity.ok(readings);
    }
}
