package com.climate_monitoring_system.controller.climatedata;

import com.climate_monitoring_system.dto.climatedata.SensorDTO;
import com.climate_monitoring_system.service.climatedata.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.climate_monitoring_system.controller.Paths.*;

@RestController
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @GetMapping(SENSORS_QUERY_PATH + "{id}")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable long id) {
        SensorDTO sensor = sensorService.getSensorDTOById(id);
        return ResponseEntity.ok(sensor);
    }

    @GetMapping(SENSORS_ALL_QUERY_PATH)
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        List<SensorDTO> sensors = sensorService.getAllSensorDTOs();
        return ResponseEntity.ok(sensors);
    }

    @GetMapping(SENSORS_BY_PASSKEY_QUERY_PATH + "{passKey}")
    public ResponseEntity<SensorDTO> getSensorByPassKey(@PathVariable String passKey) {
        SensorDTO sensor = sensorService.getSensorDTOByPassKey(passKey);
        return ResponseEntity.ok(sensor);
    }

    @GetMapping(SENSORS_BY_LOCATION_QUERY_PATH + "{id}")
    public ResponseEntity<List<SensorDTO>> getAllSensorsByLocationId(@PathVariable long id) {
        List<SensorDTO> sensors = sensorService.getAllSensorDTOsByLocationId(id);
        return ResponseEntity.ok(sensors);
    }

    @GetMapping(SENSORS_BY_DEVICECODE_QUERY_PATH + "{deviceCode}")
    public ResponseEntity<List<SensorDTO>> getAllSensorsByDeviceCode(@PathVariable String deviceCode) {
        List<SensorDTO> sensors = sensorService.getAllSensorDTOsByDeviceCode(deviceCode);
        return ResponseEntity.ok(sensors);
    }

    @GetMapping(SENSORS_BY_LOCATION_QUERY_PATH + "{id}/bydevicecode/{deviceCode}")
    public ResponseEntity<List<SensorDTO>> getSensorsByLocationIdAndDeviceCode(@PathVariable long id,
                                                                               @PathVariable String deviceCode) {
        List<SensorDTO> sensors = sensorService.getAllSensorDTOsByLocationIdAndDeviceCode(id, deviceCode);
        return ResponseEntity.ok(sensors);
    }

    @DeleteMapping(SENSORS_DELETE_QUERY_PATH + "{id}")
    public ResponseEntity<String> deleteSensorById(@PathVariable long id) {
        boolean deleteSuccessful = sensorService.deleteSensorById(id);

        if (deleteSuccessful) {
            return ResponseEntity.status(HttpStatus.OK).body("Sensor deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sensor not deleted successfully");
    }

    @PostMapping(SENSORS_ADD_QUERY_PATH)
    public ResponseEntity<String> addSensor(@RequestBody SensorDTO sensorDTO) {
        boolean addSuccessful = sensorService.addSensor(sensorDTO);

        if (addSuccessful) {
            return ResponseEntity.status(HttpStatus.OK).body("Sensor posted successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sensor not posted successfully");
    }
}
