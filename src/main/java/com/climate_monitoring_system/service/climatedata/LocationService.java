package com.climate_monitoring_system.service.climatedata;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
import com.climate_monitoring_system.domain.climatedata.Location;
import com.climate_monitoring_system.dto.climatedata.ControlParameterSetDTO;
import com.climate_monitoring_system.dto.climatedata.LocationDTO;
import com.climate_monitoring_system.repository.climatedata.ControlParameterSetRepository;
import com.climate_monitoring_system.repository.climatedata.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final ControlParameterSetService controlParameterSetService;
    private final ControlParameterSetRepository controlParameterSetRepository;

    public LocationDTO getLocationDTOById(long id) {
        Optional<Location> location = locationRepository.findById(id);

        return checkIfLocationPresentAndGetLocationDTO(location);
    }

    public Location getLocationById(long id) {
        Optional<Location> location = locationRepository.findById(id);

        return location.orElseGet(Location::new);

    }

    public List<LocationDTO> getAllLocationDTOs() {
        List<Location> locations = locationRepository.findAll();

        return locationsToLocationDTOs(locations);
    }

    public LocationDTO getLocationDTOByDescription(String description) {
        Optional<Location> optionalLocation = locationRepository.findByLocationDescription(description);

        return checkIfLocationPresentAndGetLocationDTO(optionalLocation);
    }

    public List<LocationDTO> getAllLocationDTOsByDescriptionContaining(String description) {
        List<Location> locationsByDescription = locationRepository.findAllByLocationDescriptionContaining(description);

        return locationsToLocationDTOs(locationsByDescription);
    }

    public List<LocationDTO> getAllLocationDTOsByControlParameterSetId(long id) {
        ControlParameterSet controlParameterSet = controlParameterSetService.getControlParameterSetById(id);
        List<Location> locations = locationRepository.findAllByControlParameterSet(controlParameterSet);

        return locationsToLocationDTOs(locations);
    }

    public boolean addLocation(LocationDTO locationDTO) {
        Optional<Location> possibleLocation = locationRepository.findById(locationDTO.getLocationId());
        long parameterSetId = locationDTO.getControlParameterSet().getControlParameterSetId();
        Optional<ControlParameterSet> possibleParameterSet = controlParameterSetRepository.findById(parameterSetId);

        if (possibleLocation.isEmpty() && possibleParameterSet.isPresent()) {
            Location location = new Location();
            ControlParameterSet parameterSet = possibleParameterSet.get();
            location.setLocationDescription(locationDTO.getLocationDescription());
            location.setControlParameterSet(parameterSet);
            locationRepository.save(location);
            return true;
        }

        return false;
    }

    public boolean deleteLocationById(long id) {
        Optional<Location> possibleLocation = locationRepository.findById(id);

        if (possibleLocation.isPresent()) {
            Location location = possibleLocation.get();
            locationRepository.delete(location);
            return true;
        }

        return false;
    }

    private List<LocationDTO> locationsToLocationDTOs(List<Location> location) {
        List<LocationDTO> locationDTOs = new ArrayList<>();

        for (Location locationDTO : location) {
            locationDTOs.add(getLocationDTO(locationDTO));
        }

        return locationDTOs;
    }

    private LocationDTO checkIfLocationPresentAndGetLocationDTO(Optional<Location> optionalLocation) {
        return optionalLocation.map(this::getLocationDTO).orElseGet(LocationDTO::new);
    }

    public LocationDTO getLocationDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLocationId(location.getLocationId());
        locationDTO.setLocationDescription(location.getLocationDescription());
        locationDTO.setControlParameterSet(controlParameterSetService
                .getControlParameterSetDTO(location.getControlParameterSet()));

        return locationDTO;
    }
}
