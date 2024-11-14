"use client";

import React, {useCallback, useEffect, useState} from "react";
import NotificationTable from "@/app/dashboard/components/NotificationTable";
import {NotificationDTO} from "@/app/dto/notification/NotificationDTO";
import {fetchActiveNotifications, fetchAllNotifications} from "@/app/dashboard/notifications/notificationService";

export default function Notifications() {
    const [notifications, setNotifications] = useState<NotificationDTO[]>([]);
    const [activeNotifications, setActiveNotifications] = useState<NotificationDTO[]>([]);
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

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl p-8 space-y-6 bg-white rounded shadow-md">

                {error && <p className="text-red-500">{error}</p>}

                <h1 className="text-3xl font-bold text-center">Active Notifications</h1>
                <NotificationTable notifications={activeNotifications} />

                {error && <p className="text-red-500">{error}</p>}

                <h1 className="text-3xl font-bold text-center">All Notifications</h1>
                <NotificationTable notifications={notifications} />

            </div>
        </main>
    );
}
