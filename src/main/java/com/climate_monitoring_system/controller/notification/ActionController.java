package com.climate_monitoring_system.controller.notification;

import com.climate_monitoring_system.dto.notification.ActionDTO;
import com.climate_monitoring_system.service.notification.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

import static com.climate_monitoring_system.controller.Paths.*;

@RestController
@RequiredArgsConstructor
public class ActionController {
    private final ActionService actionService;

    @GetMapping(ACTIONS_QUERY_PATH + "{id}")
    public ResponseEntity<ActionDTO> getAction(@PathVariable int id) {
        ActionDTO action = actionService.getActionDTOById(id);
        return ResponseEntity.ok(action);
    }

    @GetMapping(ACTIONS_ALL_QUERY_PATH)
    public ResponseEntity<List<ActionDTO>> getAllActions() {
        List<ActionDTO> allActions = actionService.getAllActions();
        return ResponseEntity.ok(allActions);
    }

    @GetMapping(ACTIONS_BY_USER_QUERY_PATH + "{id}")
    public ResponseEntity<List<ActionDTO>> getActionsByUserId(@PathVariable int id) {
        List<ActionDTO> actionsByUser = actionService.getAllActionDTOsByUserId(id);
        return ResponseEntity.ok(actionsByUser);
    }

    @GetMapping(ACTIONS_BY_USER_QUERY_PATH + "{name}")
    public ResponseEntity<List<ActionDTO>> getActionsByUserName(@PathVariable String name) {
        List<ActionDTO> actionsByUser = actionService.getAllActionsByUserName(name);
        return ResponseEntity.ok(actionsByUser);
    }

    @GetMapping(ACTIONS_BEFORE_QUERY_PATH + "{timestamp}")
    public ResponseEntity<List<ActionDTO>> getActionsBefore(@PathVariable Timestamp timestamp) {
        List<ActionDTO> actionsBeforeTimestamp = actionService
                .getAllActionDTOsBefore(timestamp);
        return ResponseEntity.ok(actionsBeforeTimestamp);
    }

    @GetMapping(ACTIONS_AFTER_QUERY_PATH + "{timestamp}")
    public ResponseEntity<List<ActionDTO>> getActionsAfter(@PathVariable Timestamp timestamp) {
        List<ActionDTO> actionsAfterTimestamp = actionService
                .getAllActionDTOsAfter(timestamp);
        return ResponseEntity.ok(actionsAfterTimestamp);
    }
}
