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

    public ControlParameterSetDTO getControlParameterSetDTOById(long id) {
        Optional<ControlParameterSet> controlParameterSet = controlParameterSetRepository.findById(id);

        return checkIfControlParameterSetPresentAndGetDTO(controlParameterSet);
    }

    public ControlParameterSet getControlParameterSetById(long id) {
        Optional<ControlParameterSet> controlParameterSet = controlParameterSetRepository.findById(id);

        return controlParameterSet.orElseGet(ControlParameterSet::new);

    }

    public List<ControlParameterSetDTO> getAllControlParameterSetDTOs() {
        List<ControlParameterSet> controlParameterSets = controlParameterSetRepository.findAll();

        return controlParametersToControlParameterDTOs(controlParameterSets);
    }

    public boolean addControlParameterSet(ControlParameterSetDTO controlParameterSetDTO) {
        Optional<ControlParameterSet> possibleParameterSet = controlParameterSetRepository
                .findById(controlParameterSetDTO.getControlParameterSetId());

        if (possibleParameterSet.isEmpty()) {
            ControlParameterSet controlParameterSet = new ControlParameterSet();
            controlParameterSet.setTempNorm(controlParameterSetDTO.getTempNorm());
            controlParameterSet.setTempTolerance(controlParameterSetDTO.getTempTolerance());
            controlParameterSet.setRelHumidityNorm(controlParameterSetDTO.getRelHumidityNorm());
            controlParameterSet.setRelHumidityTolerance(controlParameterSetDTO.getRelHumidityTolerance());
            controlParameterSetRepository.save(controlParameterSet);
            return true;
        }

        return false;
    }

    private List<ControlParameterSetDTO> controlParametersToControlParameterDTOs(List<ControlParameterSet> controlParameterSets) {
        List<ControlParameterSetDTO> controlParameterSetDTOs = new ArrayList<>();

        for (ControlParameterSet controlParameterSet : controlParameterSets) {
            controlParameterSetDTOs.add(getControlParameterSetDTO(controlParameterSet));
        }

        return controlParameterSetDTOs;
    }

    private ControlParameterSetDTO checkIfControlParameterSetPresentAndGetDTO(Optional<ControlParameterSet> optionalSet) {
        return optionalSet.map(this::getControlParameterSetDTO).orElseGet(ControlParameterSetDTO::new);
    }

    public ControlParameterSetDTO getControlParameterSetDTO(ControlParameterSet controlParameterSet) {
        ControlParameterSetDTO controlParameterSetDTO = new ControlParameterSetDTO();
        controlParameterSetDTO.setControlParameterSetId(controlParameterSet.getControlParameterSetId());
        controlParameterSetDTO.setTempNorm(controlParameterSet.getTempNorm());
        controlParameterSetDTO.setTempTolerance(controlParameterSet.getTempTolerance());
        controlParameterSetDTO.setRelHumidityNorm(controlParameterSet.getRelHumidityNorm());
        controlParameterSetDTO.setRelHumidityTolerance(controlParameterSet.getRelHumidityTolerance());

        return controlParameterSetDTO;
    }
}
