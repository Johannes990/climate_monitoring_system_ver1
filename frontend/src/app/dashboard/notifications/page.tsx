"use client";

import React, {useCallback, useEffect, useState} from "react";
import NotificationTable from "@/app/dashboard/components/NotificationTable";
import {NotificationDTO} from "@/app/dto/notification/NotificationDTO";
import {
    fetchActiveNotifications,
    fetchAllNotifications, fetchNotificationsAfter, fetchNotificationsBefore,
    fetchNotificationsByActionTaken,
    fetchNotificationsByConditionsResolved,
    fetchNotificationsBySensorId,
    fetchNotificationsByType
} from "@/app/dashboard/notifications/notificationService";
import * as sea from "node:sea";

export default function Notifications() {
    const [notifications, setNotifications] = useState<NotificationDTO[]>([]);
    const [activeNotifications, setActiveNotifications] = useState<NotificationDTO[]>([]);
    const [searchInput, setSearchInput] = useState<string>("");
    const [error, setError] = useState<string>("");

    const fetchData = useCallback(async () => {
        try {
            const allNotifications = await fetchAllNotifications();
            const activeNotifications = await fetchActiveNotifications(true);
            setNotifications(allNotifications);
            setActiveNotifications(activeNotifications);
        } catch (err) {
            setError("Failed to fetch all notifications");
            console.error(err);
        }
    }, []);

    useEffect(() => {
        window.history.replaceState(null, "", window.location.href);
        fetchData();
    }, [fetchData]);

    const handleSearch = async () => {
        if (!searchInput.trim()) {
            const allNotifications = await fetchAllNotifications();
            setNotifications(allNotifications);
            return;
        }

        try {
            let filteredNotifications: NotificationDTO[] = [];
            setError("");

            if (searchInput.startsWith("type:")) {
                const typeId = parseInt(searchInput.split(":")[1], 10);
                if (!isNaN(typeId)) {
                    filteredNotifications = await fetchNotificationsByType(typeId);
                }
            } else if (searchInput.startsWith("sensor:")) {
                const sensorId = parseInt(searchInput.split(":")[1], 10);
                if (!isNaN(sensorId)) {
                    filteredNotifications = await fetchNotificationsBySensorId(sensorId);
                }
            } else if (searchInput.startsWith("resolved:")) {
                const resolved = searchInput.split(":")[1] === "true";
                filteredNotifications = await fetchNotificationsByConditionsResolved(resolved);
            } else if (searchInput.startsWith("action:")) {
                const actionTaken = searchInput.split(":")[1] === "true";
                filteredNotifications = await fetchNotificationsByActionTaken(actionTaken);
            } else if (searchInput.startsWith("before:")) {
                const timestamp = new Date(searchInput.split(":")[1]);
                if (!isNaN(timestamp.getTime())) {
                    filteredNotifications = await fetchNotificationsBefore(timestamp);
                }
            } else if (searchInput.startsWith("after:")) {
                const timestamp = new Date(searchInput.split(":")[1]);
                if (!isNaN(timestamp.getTime())) {
                    filteredNotifications = await fetchNotificationsAfter(timestamp);
                }
            } else {
                setError("Invalid search format: use type:typeId, sensor:sensorId, resolved:true/false, action:true/false, before:date or after:date");
            }

            setNotifications(filteredNotifications);
        } catch(err) {
            setError("Failed to fetch notifications based on seacrh criteria");
            console.error(err);
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl p-8 space-y-6 bg-white rounded shadow-md">

                {error && <p className="text-red-500">{error}</p>}


                <h1 className="text-3xl font-bold text-center">Active Notifications</h1>
                <NotificationTable notifications={activeNotifications}/>

                {error && <p className="text-red-500">{error}</p>}

                <h1 className="text-3xl font-bold text-center">All Notifications</h1>
                <div className="flex items-center sapce-x-4">
                    <input
                        type="text"
                        value={searchInput}
                        onChange={(e) => setSearchInput(e.target.value)}
                        placeholder="Search (type:typeId, sensor:sensorId, action:boolean, resolved:boolean, before:date, after:date)"
                        className="flex-grow p-2 border-gray-300 center-justified rounded"
                    />
                    <button
                        onClick={handleSearch}
                        className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                    >
                        Filter
                    </button>
                </div>
                <NotificationTable notifications={notifications}/>

            </div>
        </main>
    );
}
