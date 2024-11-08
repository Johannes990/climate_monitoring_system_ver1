package com.climate_monitoring_system.controller.climatedata;

import com.climate_monitoring_system.dto.climatedata.ControlParameterSetDTO;
import com.climate_monitoring_system.service.climatedata.ControlParameterSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.climate_monitoring_system.controller.Paths.*;

@RestController
@RequiredArgsConstructor
public class ControlParameterSetController {
    private final ControlParameterSetService controlParameterSetService;

    @GetMapping(CONTROL_PARAMS_ALL_QUERY_PATH)
    public ResponseEntity<List<ControlParameterSetDTO>> getAllControlParameters() {
        List<ControlParameterSetDTO> controlParameterSetDTOS = controlParameterSetService
                .getAllControlParameterSetDTOs();
        return ResponseEntity.ok(controlParameterSetDTOS);
    }

    @GetMapping(CONTROL_PARAMS_QUERY_PATH + "{id}")
    public ResponseEntity<ControlParameterSetDTO> getControlParameterById(@PathVariable int id) {
        ControlParameterSetDTO controlParameterSetDTO = controlParameterSetService.getControlParameterSetDTOById(id);
        return ResponseEntity.ok(controlParameterSetDTO);
    }

    @DeleteMapping(CONTROL_PARAMS_DELETE_PATH + "{id}")
    public ResponseEntity<String> deleteControlParameterById(@PathVariable int id) {
        boolean deleteSuccessful = controlParameterSetService.deleteControlParameterSetById(id);

        if (deleteSuccessful) {
            return ResponseEntity.status(HttpStatus.OK).body("Control parameter deleted Successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Control parameter not deleted successfully");
    }

    @PostMapping(CONTROL_PARAMS_ADD_QUERY_PATH)
    public ResponseEntity<String> addControlParameter(@RequestBody ControlParameterSetDTO controlParameterSetDTO) {
        boolean controlParameterSetSaved = controlParameterSetService.addControlParameterSet(controlParameterSetDTO);

        if (controlParameterSetSaved) {
            return ResponseEntity.ok("Control parameter set posted successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Control parameter set not posted successfully");
    }
}
