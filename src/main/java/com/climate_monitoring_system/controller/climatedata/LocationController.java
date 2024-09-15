package com.climate_monitoring_system.controller.climatedata;

import com.climate_monitoring_system.dto.climatedata.LocationDTO;
import com.climate_monitoring_system.service.climatedata.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/locations/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable int id) {
        LocationDTO location = locationService.getLocationDTOById(id);

        return ResponseEntity.ok(location);
    }

    @GetMapping("locations/all")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        List<LocationDTO> locations = locationService.getAllLocationDTOs();

        return ResponseEntity.ok(locations);
    }

    @GetMapping("/locations/bydescriptionprecise/{description}")
    public ResponseEntity<LocationDTO> getLocationByDescriptionPrecise(@PathVariable String description) {
        LocationDTO location = locationService.getLocationDTOByDescription(description);

        return ResponseEntity.ok(location);
    }

    @GetMapping("/locations/bydescriptioncontaining/{description}")
    public ResponseEntity<List<LocationDTO>> getLocationByDescriptionContaining(@PathVariable String description) {
        List<LocationDTO> locations = locationService.getAllLocationDTOsByDescriptionContaining(description);

        return ResponseEntity.ok(locations);
    }
}
