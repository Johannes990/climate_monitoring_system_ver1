import React from "react";
import {NotificationDTO} from "@/app/dto/notification/NotificationDTO";

type NotificationTableProps = {
    notifications: NotificationDTO[];
};

const NotificationTable: React.FC<NotificationTableProps> = ({ notifications }) => {
    return (
        <div className="overflow-x-auto w-full">
            <table className="min-w-full bg-white border">
                <thead>
                <tr>
                    <th className="px-3 py-2 border">ID</th>
                    <th className="px-3 py-2 border">Notification type</th>
                    <th className="px-3 py-2 border">Generated by sensor</th>
                    <th className="px-3 py-2 border">Time generated</th>
                    <th className="px-3 py-2 border">Conditions resolved</th>
                    <th className="px-3 py-2 border">Action taken</th>
                </tr>
                </thead>
                <tbody>
                {notifications.map((notification) => (
                    <tr key={notification.notificationId}>
                        <th className="px-3 py-2 border">{notification.notificationId}</th>
                        <th className="px-3 py-2 border">{notification.notificationType.notificationTypeDescription}</th>
                        <th className="px-3 py-2 border">{notification.sensor.sensorId}</th>
                        <th className="px-3 py-2 border">
                            {notification.timestamp.toLocaleDateString('en-GB', {
                                day: '2-digit',
                                month: 'long',
                                year: 'numeric'
                            })}, {notification.timestamp.toLocaleTimeString('en-GB', {
                            hour: '2-digit',
                            minute: '2-digit',
                            second: '2-digit',
                            hour12: false
                        })}
                        </th>
                        <th className="px-3 py-2 border">{notification.conditionsSelfResolved ? "conditions self-resolved" : "conditions did not self-resolve"}</th>
                        <th className="px-3 py-2 border">{notification.userActionTaken ? "user action taken" : "user action not taken"}</th>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default NotificationTable;