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

function parseNotificationData(data: NotificationDTO[]): NotificationDTO[] {
    return data.map(notification => ({
        ...notification,
        timestamp: new Date(notification.timestamp),
        action: notification.action || null
    }));
}

export async function fetchAllNotifications(): Promise<NotificationDTO[]> {
    const data = await fetchData(NOTIFICATIONS_ALL_QUERY_PATH, "failed to fetch notifications");
    return parseNotificationData(data);
}

export async function fetchActiveNotifications(isActive: boolean): Promise<NotificationDTO[]> {
    const data = await fetchData(NOTIFICATIONS_ACTIVE_QUERY_PATH, "failed to fetch active notifications");
    return parseNotificationData(data);
}

export async function fetchNotificationsByType(notificationTypeId: number): Promise<NotificationDTO[]> {
    const data = await fetchData(
        `${NOTIFICATIONS_BY_NOTIFICATION_TYPE_QUERY_PATH}${notificationTypeId}`,
        `failed to fetch notifications by type id: ${notificationTypeId} `
    );
    return parseNotificationData(data);
}

export async function fetchNotificationsBySensorId(sensorId: number) : Promise<NotificationDTO[]> {
    const data = await fetchData(
        `${NOTIFICATIONS_BY_SENSOR_QUERY_PATH}${sensorId}`,
        `failed to fetch notifications by sensor id: ${sensorId}`
    );
    return parseNotificationData(data);
}

export async function fetchNotificationsByConditionsResolved(conditionsResolved: boolean): Promise<NotificationDTO[]> {
    const data = await fetchData(
        `${NOTIFICATIONS_BY_CONDITIONS_RESOLVED_QUERY_PATH}${conditionsResolved}`,
        `failed to fetch notifications where condition self resolved: ${conditionsResolved}`
    );
    return parseNotificationData(data);
}

export async function fetchNotificationsByActionTaken(actionTaken: boolean): Promise<NotificationDTO[]> {
    const data = await fetchData(
        `${NOTIFICATIONS_BY_ACTION_TAKEN_QUERY_PATH}${actionTaken}`,
        `failed to fetch notifications where action taken: ${actionTaken}`
    );
    return parseNotificationData(data);
}

export async function fetchNotificationsBefore(timestamp: Date): Promise<NotificationDTO[]> {
    const data = await fetchData(
        `${NOTIFICATIONS_BEFORE_QUERY_PATH}${timestamp}`,
        `failed to fetch notifications created before: ${timestamp}`
    );
    return parseNotificationData(data);
}

export async function fetchNotificationsAfter(timestamp: Date): Promise<NotificationDTO[]> {
    const data = await fetchData(
        `${NOTIFICATIONS_AFTER_QUERY_PATH}${timestamp}`,
        `failed to fetch notifications created after: ${timestamp}`
    );
    return parseNotificationData(data);
}