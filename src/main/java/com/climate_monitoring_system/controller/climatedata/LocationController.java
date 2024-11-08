package com.climate_monitoring_system.controller.climatedata;

import com.climate_monitoring_system.dto.climatedata.LocationDTO;
import com.climate_monitoring_system.service.climatedata.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.climate_monitoring_system.controller.Paths.*;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping(LOCATIONS_QUERY_PATH + "{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable int id) {
        LocationDTO location = locationService.getLocationDTOById(id);
        return ResponseEntity.ok(location);
    }

    @GetMapping(LOCATIONS_ALL_QUERY_PATH)
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        List<LocationDTO> locations = locationService.getAllLocationDTOs();
        return ResponseEntity.ok(locations);
    }

    @GetMapping(LOCATIONS_BY_DESCRIPTION_PRECISE_QUERY_PATH + "{description}")
    public ResponseEntity<LocationDTO> getLocationByDescriptionPrecise(@PathVariable String description) {
        LocationDTO location = locationService.getLocationDTOByDescription(description);
        return ResponseEntity.ok(location);
    }

    @GetMapping(LOCATIONS_BY_DESCRIPTION_CONTAINING_QUERY_PATH + "{description}")
    public ResponseEntity<List<LocationDTO>> getLocationByDescriptionContaining(@PathVariable String description) {
        List<LocationDTO> locations = locationService.getAllLocationDTOsByDescriptionContaining(description);
        return ResponseEntity.ok(locations);
    }

    @DeleteMapping(LOCATIONS_DELETE_QUERY_PATH + "{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable int id) {
        boolean deleteSuccessful = locationService.deleteLocationById(id);

        if (deleteSuccessful) {
            return ResponseEntity.status(HttpStatus.OK).body("Location deleted successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Location not deleted successfully");
    }

    @PostMapping(LOCATIONS_ADD_QUERY_PATH)
    public ResponseEntity<String> addLocation(@RequestBody LocationDTO location) {
        boolean addSuccessful = locationService.addLocation(location);

        if (addSuccessful) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Location posted successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Location not posted successfully");
    }
}
