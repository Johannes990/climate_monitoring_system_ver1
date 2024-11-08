package com.climate_monitoring_system.service.notification;

import com.climate_monitoring_system.domain.notification.Action;
import com.climate_monitoring_system.domain.userauth.AppUser;
import com.climate_monitoring_system.dto.notification.ActionDTO;
import com.climate_monitoring_system.repository.notification.ActionRepository;
import com.climate_monitoring_system.repository.userauth.UserRepository;
import com.climate_monitoring_system.service.userauth.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository actionRepository;
    private final AppUserService appUserService;
    private final UserRepository userRepository;

    public ActionDTO getActionDTOById(long id) {
        Optional<Action> optionalAction = actionRepository.findById(id);
        return optionalAction.map(this::actionToActionDTO).orElseGet(ActionDTO::new);
    }

    public List<ActionDTO> getAllActions() {
        List<Action> actions = actionRepository.findAll();
        return actionsToActionDTOs(actions);
    }

    public List<ActionDTO> getAllActionDTOsByUserId(long userId) {
        Optional<AppUser> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            List<Action> userActions = actionRepository.findAllByUser(user);
            return actionsToActionDTOs(userActions);
        }

        return new ArrayList<>();
    }

    public List<ActionDTO> getAllActionsByUserName(String userName) {
        Optional<AppUser> optionalUser = userRepository.findByUserName(userName);

        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            List<Action> userActions = actionRepository.findAllByUser(user);
            return actionsToActionDTOs(userActions);
        }

        return new ArrayList<>();
    }

    public List<ActionDTO> getAllActionDTOsBefore(Timestamp timestamp) {
        List<Action> actionsBeforeTimestamp = actionRepository.findAllByTimestampBefore(timestamp);
        return actionsToActionDTOs(actionsBeforeTimestamp);
    }

    public List<ActionDTO> getAllActionDTOsAfter(Timestamp timestamp) {
        List<Action> actionsAfterTimestamp = actionRepository.findAllByTimestampAfter(timestamp);
        return actionsToActionDTOs(actionsAfterTimestamp);
    }

    private List<ActionDTO> actionsToActionDTOs(List<Action> actions) {
        List<ActionDTO> actionDTOs = new ArrayList<>();

        for (Action action : actions) {
            actionDTOs.add(actionToActionDTO(action));
        }

        return actionDTOs;
    }

    public ActionDTO actionToActionDTO(Action action) {
        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setActionId(action.getActionId());
        actionDTO.setTimestamp(action.getTimestamp());
        actionDTO.setMessage(action.getMessage());
        actionDTO.setUser(appUserService.findByUserId(action.getUser().getUserId()));
        return actionDTO;
    }
}
