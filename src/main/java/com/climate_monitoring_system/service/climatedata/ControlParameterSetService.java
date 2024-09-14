package com.climate_monitoring_system.service.climatedata;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
import com.climate_monitoring_system.dto.climatedata.ControlParameterSetDTO;
import com.climate_monitoring_system.repository.climatedata.ControlParameterSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ControlParameterSetService {
    private final ControlParameterSetRepository controlParameterSetRepository;

    public ControlParameterSetDTO getControlParameterSetById(long id) {
        Optional<ControlParameterSet> controlParameterSet = controlParameterSetRepository.findById(id);

        return checkIfControlParameterSetPresentAndGetDTO(controlParameterSet);
    }

    public List<ControlParameterSetDTO> getAllControlParameterSets() {
        List<ControlParameterSet> controlParameterSets = controlParameterSetRepository.findAll();
        List<ControlParameterSetDTO> controlParameterSetDTOs = new ArrayList<>();

        for (ControlParameterSet controlParameterSet : controlParameterSets) {
            controlParameterSetDTOs.add(getControlParameterSetDTO(controlParameterSet));
        }

        return controlParameterSetDTOs;
    }

    private ControlParameterSetDTO checkIfControlParameterSetPresentAndGetDTO(Optional<ControlParameterSet> optionalSet) {
        return optionalSet.map(this::getControlParameterSetDTO).orElseGet(ControlParameterSetDTO::new);
    }

    private ControlParameterSetDTO getControlParameterSetDTO(ControlParameterSet controlParameterSet) {
        ControlParameterSetDTO controlParameterSetDTO = new ControlParameterSetDTO();
        controlParameterSetDTO.setControlParameterSetId(controlParameterSet.getControlParameterSetId());
        controlParameterSetDTO.setTempNorm(controlParameterSet.getTempNorm());
        controlParameterSetDTO.setTempTolerance(controlParameterSet.getTempTolerance());
        controlParameterSetDTO.setRelHumidityNorm(controlParameterSet.getRelHumidityNorm());
        controlParameterSetDTO.setRelHumidityTolerance(controlParameterSet.getRelHumidityTolerance());

        return controlParameterSetDTO;
    }
}
