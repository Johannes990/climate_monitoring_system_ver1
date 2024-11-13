import {NotificationDTO} from "@/app/dto/notification/NotificationDTO";
import {fetchData} from "@/app/utils/functions";
import {
    NOTIFICATIONS_ACTIVE_QUERY_PATH, NOTIFICATIONS_AFTER_QUERY_PATH,
    NOTIFICATIONS_ALL_QUERY_PATH, NOTIFICATIONS_BEFORE_QUERY_PATH,
    NOTIFICATIONS_BY_ACTION_TAKEN_QUERY_PATH,
    NOTIFICATIONS_BY_CONDITIONS_RESOLVED_QUERY_PATH,
    NOTIFICATIONS_BY_NOTIFICATION_TYPE_QUERY_PATH,
    NOTIFICATIONS_BY_SENSOR_QUERY_PATH
} from "@/app/utils/constants";

export async function fetchAllNotifications(): Promise<NotificationDTO[]> {
 return fetchData(NOTIFICATIONS_ALL_QUERY_PATH, "failed to fetch notifications");
}

export async function fetchActiveNotifications(isActive: boolean): Promise<NotificationDTO[]> {
    return fetchData(NOTIFICATIONS_ACTIVE_QUERY_PATH, "failed to fetch active notifications");
}

export async function fetchNotificationsByType(notificationTypeId: number): Promise<NotificationDTO[]> {
    return fetchData(
        `${NOTIFICATIONS_BY_NOTIFICATION_TYPE_QUERY_PATH}${notificationTypeId}`,
        `failed to fetch notifications by type id: ${notificationTypeId} `
    );
}

export async function fetchNotificationsBySensorId(sensorId: number) : Promise<NotificationDTO[]> {
    return fetchData(
        `${NOTIFICATIONS_BY_SENSOR_QUERY_PATH}${sensorId}`,
        `failed to fetch notifications by sensor id: ${sensorId}`
    );
}

export async function fetchNotificationsByConditionsResolved(conditionsResolved: boolean): Promise<NotificationDTO[]> {
    return fetchData(
        `${NOTIFICATIONS_BY_CONDITIONS_RESOLVED_QUERY_PATH}${conditionsResolved}`,
        `failed to fetch notifications where condition self resolved: ${conditionsResolved}`
    );
}

export async function fetchNotificationsByActionTaken(actionTaken: boolean): Promise<NotificationDTO[]> {
    return fetchData(
        `${NOTIFICATIONS_BY_ACTION_TAKEN_QUERY_PATH}${actionTaken}`,
        `failed to fetch notifications where action taken: ${actionTaken}`
    );
}

export async function fetchNotificationsBefore(timestamp: Date): Promise<NotificationDTO[]> {
    return fetchData(
        `${NOTIFICATIONS_BEFORE_QUERY_PATH}${timestamp}`,
        `failed to fetch notifications created before: ${timestamp}`
    );
}

export async function fetchNotificationsAfter(timestamp: Date): Promise<NotificationDTO[]> {
    return fetchData(
        `${NOTIFICATIONS_AFTER_QUERY_PATH}${timestamp}`,
        `failed to fetch notifications created after: ${timestamp}`
    );
}