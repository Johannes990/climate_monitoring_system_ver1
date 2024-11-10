package com.climate_monitoring_system.service.notification;

import com.climate_monitoring_system.domain.climatedata.ControlParameterSet;
import com.climate_monitoring_system.domain.climatedata.Sensor;
import com.climate_monitoring_system.domain.climatedata.SensorReading;
import com.climate_monitoring_system.domain.notification.Notification;
import com.climate_monitoring_system.domain.notification.NotificationType;
import com.climate_monitoring_system.dto.notification.NotificationDTO;
import com.climate_monitoring_system.repository.climatedata.SensorReadingRepository;
import com.climate_monitoring_system.repository.climatedata.SensorRepository;
import com.climate_monitoring_system.repository.notification.NotificationRepository;
import com.climate_monitoring_system.repository.notification.NotificationTypeRepository;
import com.climate_monitoring_system.service.climatedata.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SensorReadingRepository sensorReadingRepository;
    private final SensorService sensorService;
    private final SensorRepository sensorRepository;
    private final NotificationTypeService notificationTypeService;
    private final NotificationTypeRepository notificationTypeRepository;
    private final ActionService actionService;
    private LocalDateTime lastCheckTime = LocalDateTime.now();
    private HashMap<Integer, FixedDeque> sensorQueues = new HashMap<>();
    private HashMap<Integer, Integer> sensorNotifications = new HashMap<>();
    private final int READING_WINDOW = 10;
    private final int NO_ERROR = 0;
    private final int OVER_TEMP = 1;
    private final int UNDER_TEMP = 2;
    private final int OVER_RH = 3;
    private final int UNDER_RH = 4;


    public List<NotificationDTO> getAllNotificationDTOs() {
        List<Notification> allNotifications = notificationRepository.findAll();
        return notificationsToNotificationDTOs(allNotifications);
    }

    public List<NotificationDTO> getAllNotificationDTOsByNotificationType(
            NotificationType notificationType
    ) {
        List<Notification> allNotificationsByType = notificationRepository
                .findAllByNotificationType(notificationType);
        return notificationsToNotificationDTOs(allNotificationsByType);
    }

    public List<NotificationDTO> getAllNotificationDTOsBySensor(Sensor sensor) {
        List<Notification> allNotificationsBySensor = notificationRepository
                .findAllBySensor(sensor);
        return notificationsToNotificationDTOs(allNotificationsBySensor);
    }

    public List<NotificationDTO> getAllNotificationDTOsWithActionTaken(boolean actionTaken) {
        List<Notification> allNotificationsWithActionTaken = notificationRepository
                .findAllByUserActionTaken(actionTaken);
        return notificationsToNotificationDTOs(allNotificationsWithActionTaken);
    }

    public List<NotificationDTO> getAllNotificationDTOsWithConditionsSelfResolved(boolean selfResolved) {
        List<Notification> allNotificationsWithConditionsSelfResolved = notificationRepository
                .findAllByConditionsSelfResolved(selfResolved);
        return notificationsToNotificationDTOs(allNotificationsWithConditionsSelfResolved);
    }

    public List<NotificationDTO> getAllNotificationsDTOsBefore(Timestamp timestamp) {
        List<Notification> allNotificationsBefore = notificationRepository
                .findAllByTimeStampBefore(timestamp);
        return notificationsToNotificationDTOs(allNotificationsBefore);
    }

    public List<NotificationDTO> getAllNotificationsDTOsAfter(Timestamp timestamp) {
        List<Notification> allNotificationsAfter = notificationRepository
                .findAllByTimeStampAfter(timestamp);
        return notificationsToNotificationDTOs(allNotificationsAfter);
    }

    @Scheduled(fixedRate = 15000)
    public void checkForNewReadings() {
        System.out.println("Checking for new readings...");
        ZonedDateTime lastCheckTimeInUTC = lastCheckTime.atZone(ZoneId.of("UTC")).minusHours(2);
        System.out.println("New time check: " + lastCheckTimeInUTC);
        List<Sensor> sensors = sensorRepository.findAll();

        for (Sensor sensor: sensors) {
            System.out.println("Sensor id: " + sensor.getSensorId());
            ControlParameterSet controlParams = sensor.getLocation().getControlParameterSet();
            float maxTemp = controlParams.getTempNorm() + controlParams.getTempTolerance();
            float minTemp = controlParams.getTempNorm() - controlParams.getTempTolerance();
            float maxRH = controlParams.getRelHumidityNorm() + controlParams.getRelHumidityTolerance();
            float minRH = controlParams.getRelHumidityNorm() - controlParams.getRelHumidityTolerance();
            List<SensorReading> newReadings = sensorReadingRepository
                    .findAllBySensor(sensor).stream().filter(
                            reading -> reading.getReadingTime().toLocalDateTime().isAfter(ChronoLocalDateTime.from(lastCheckTimeInUTC))
                    ).toList();
            System.out.println("found new readings: " + newReadings);

            for (SensorReading sensorReading: newReadings) {
                System.out.println("New reading picked up from sensor: " + sensorReading.getSensor());
                System.out.println("Reading added to deque: " + sensorReading);
                processNewReading(sensor, sensorReading, maxTemp, minTemp, maxRH, minRH);
            }
        }

        lastCheckTime = LocalDateTime.now();
    }

    private void processNewReading(Sensor sensor, SensorReading newReading,
                                   float tempMax, float tempMin,
                                   float relHumidityMax, float relHumidityMin) {
        int sensorId = (int)sensor.getSensorId();
        if (sensorId == newReading.getSensor().getSensorId()) {
            FixedDeque sensorDeque = sensorQueues.computeIfAbsent(sensorId, _ -> new FixedDeque(READING_WINDOW));
            sensorDeque.addValue(newReading);
            sensorQueues.put(sensorId, sensorDeque);

            if (sensorDeque.isFull()) {
                boolean overTempMax = sensorDeque.readingsOverTempMax(tempMax);
                boolean underTempMin = sensorDeque.readingsUnderTempMin(tempMin);
                boolean overHumidityMax = sensorDeque.readingsOverHumidityMax(relHumidityMax);
                boolean underHumidityMin = sensorDeque.readingsUnderHumidityMin(relHumidityMin);
                sensorNotifications.putIfAbsent(sensorId, NO_ERROR);
                int prevNotificationCode = sensorNotifications.get(sensorId);

                if (prevNotificationCode == OVER_TEMP && !overTempMax) {
                    System.out.println("Resolving OVER_TEMP");
                    resolveNotificationConditions(sensor, 1, 3L);
                }
                if (prevNotificationCode == UNDER_TEMP && !underTempMin) {
                    System.out.println("Resolving UNDER_TEMP");
                    resolveNotificationConditions(sensor, 2, 3L);
                }
                if (prevNotificationCode == OVER_RH && !overHumidityMax) {
                    System.out.println("Resolving OVER_RH");
                    resolveNotificationConditions(sensor, 4, 6L);
                }
                if (prevNotificationCode == UNDER_RH && !underHumidityMin) {
                    System.out.println("Resolving UNDER_RH");
                    resolveNotificationConditions(sensor, 5, 6L);
                }
                if (overTempMax) {
                    System.out.println("notification OVER_TEMP, sensorID: " + sensorId);
                    sensorNotifications.put(sensorId, OVER_TEMP);
                    if (prevNotificationCode != OVER_TEMP) {
                        notificationRepository
                                .save(getNewNotification(sensor, 1, false));
                    }
                }
                if (underTempMin) {
                    System.out.println("notification UNDER_TEMP, sensorID: " + sensorId);
                    sensorNotifications.put(sensorId, UNDER_TEMP);
                    if (prevNotificationCode != UNDER_TEMP) {
                        notificationRepository
                                .save(getNewNotification(sensor, 2, false));
                    }
                }
                if (overHumidityMax) {
                    System.out.println("notification OVER_RH, sensorID: " + sensorId);
                    sensorNotifications.put(sensorId, OVER_RH);
                    if (prevNotificationCode != OVER_RH) {
                        notificationRepository
                                .save(getNewNotification(sensor, 4, false));
                    }
                }
                if (underHumidityMin) {
                    System.out.println("notification UNDER_RH, sensorID: " + sensorId);
                    sensorNotifications.put(sensorId, UNDER_RH);
                    if (prevNotificationCode != UNDER_RH) {
                        notificationRepository
                                .save(getNewNotification(sensor, 5, false));
                    }
                }
            }
        }
    }

    private void resolveNotificationConditions(
            Sensor sensor,
            int initialNotificationTypeId,
            long finalNotificationTypeId) {
        int sensorId = (int) sensor.getSensorId();
        sensorNotifications.put(sensorId, NO_ERROR);
        List<Notification> initialNotifications = notificationRepository
                .findAllBySensorAndIsActive(sensor, true)
                .stream()
                .filter(
                        notification -> notification.getNotificationType()
                                .getNotificationTypeId() == initialNotificationTypeId
                ).toList();

        if (!initialNotifications.isEmpty()) {
            Notification initialNotification = initialNotifications.getFirst();
            initialNotification.setConditionsSelfResolved(true);
            notificationRepository.save(initialNotification);
            notificationRepository.save(getNewNotification(sensor, finalNotificationTypeId, true));
        }
    }

    private Notification getNewNotification(Sensor sensor, long notificationTypeId, boolean selfResolved) {
        Notification newNotification = new Notification();
        newNotification.setNotificationType(notificationTypeRepository.getReferenceById(notificationTypeId));
        newNotification.setSensor(sensor);
        newNotification.setConditionsSelfResolved(selfResolved);
        newNotification.setUserActionTaken(false);
        return newNotification;
    }

    private List<NotificationDTO> notificationsToNotificationDTOs(
            List<Notification> notifications
    ) {
        List<NotificationDTO> notificationDTOs = new ArrayList<>();

        for (Notification notification : notifications) {
            notificationDTOs.add(notificationToNotificationDTO(notification));
        }

        return notificationDTOs;
    }

    private NotificationDTO notificationToNotificationDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(notification.getNotificationId());
        notificationDTO.setNotificationType(notificationTypeService
                .getNotificationTypeDTO(notification.getNotificationType()));
        notificationDTO.setSensor(sensorService.getSensorDTO(notification.getSensor()));
        notificationDTO.setTimestamp(notification.getTimeStamp());
        notificationDTO.setConditionsSelfResolved(notification.isConditionsSelfResolved());
        notificationDTO.setUserActionTaken(notification.isUserActionTaken());
        notificationDTO.setAction(actionService.actionToActionDTO(notification.getAction()));
        notificationDTO.setActive(notification.isActive());
        return notificationDTO;
    }
}

class FixedDeque {
    private final Deque<SensorReading> readingDeque;
    private final int size;

    public FixedDeque(int size) {
        this.readingDeque = new ArrayDeque<>();
        this.size = size;
    }

    public void addValue(SensorReading reading) {
        if (readingDeque.size() >= 10) {
            readingDeque.removeFirst();
        }
        readingDeque.addLast(reading);
    }

    public boolean isFull() {
        return readingDeque.size() == size;
    }

    public boolean readingsOverTempMax(float limit) {
        return readingDeque.stream().allMatch(reading -> reading.getTemperature() > limit);
    }

    public boolean readingsUnderTempMin(float limit) {
        return readingDeque.stream().allMatch(reading -> reading.getTemperature() < limit);
    }

    public boolean readingsOverHumidityMax(float limit) {
        return readingDeque.stream().allMatch(reading -> reading.getRelHumidity() > limit);
    }

    public boolean readingsUnderHumidityMin(float limit) {
        return readingDeque.stream().allMatch(reading -> reading.getRelHumidity() < limit);
    }
}
