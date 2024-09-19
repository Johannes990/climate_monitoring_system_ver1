package com.climate_monitoring_system.controller.climatedata;

import com.climate_monitoring_system.dto.climatedata.ControlParameterSetDTO;
import com.climate_monitoring_system.service.climatedata.ControlParameterSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ControlParameterController {
    private final ControlParameterSetService controlParameterSetService;

    @GetMapping("/controlparams/all")
    public ResponseEntity<List<ControlParameterSetDTO>> getAllControlParameters() {
        List<ControlParameterSetDTO> controlParameterSetDTOS = controlParameterSetService
                .getAllControlParameterSetDTOs();

        return ResponseEntity.ok(controlParameterSetDTOS);
    }

    @GetMapping("/controlparams/{id}")
    public ResponseEntity<ControlParameterSetDTO> getControlParameterById(@PathVariable int id) {
        ControlParameterSetDTO controlParameterSetDTO = controlParameterSetService.getControlParameterSetDTOById(id);

        return ResponseEntity.ok(controlParameterSetDTO);
    }

    @PostMapping("/controlparams/add")
    public ResponseEntity<String> addControlParameter(@RequestBody ControlParameterSetDTO controlParameterSetDTO) {
        System.out.println(controlParameterSetDTO);
        boolean controlParameterSetSaved = controlParameterSetService.addControlParameterSet(controlParameterSetDTO);
        if (controlParameterSetSaved) {
            return ResponseEntity.ok("Control parameter form submission successful");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Control parameter form submission failed");
    }
}
