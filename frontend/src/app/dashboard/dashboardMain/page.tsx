"use client";

import { getRequest } from "@/app/utils/api";
import React, {useCallback, useEffect, useState} from "react";
import { useRouter } from "next/navigation";
import {
    LOGIN_URL_PATH,
    PROTECTED_QUERY_PATH,
} from "@/app/utils/constants";
import {NotificationDTO} from "@/app/dto/notification/NotificationDTO";
import {fetchActiveNotifications, fetchAllNotifications} from "@/app/dashboard/notifications/notificationService";
import NotificationTable from "@/app/dashboard/components/NotificationTable";

export default function Dashboard() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const [activeNotifications, setActiveNotifications] = useState<NotificationDTO[]>([]);
    const [error, setError] = useState<string>("");
    const router = useRouter();

    const fetchData = useCallback(async () => {
        try {
            const activeNotifications = await fetchActiveNotifications(true);
            setActiveNotifications(activeNotifications);
        } catch (err) {
            setError("Failed to fetch all notifications");
            console.error(err);
        }
    }, []);

    useEffect(() => {
        window.history.replaceState(null, "", window.location.href);
        const checkAuthentication = async () => {
            try {
                const response = await getRequest(PROTECTED_QUERY_PATH);

                console.log("Protected endpoint response:", response.status);

                if (response.ok) {
                    const message = await response.text();
                    if (message === "Access to protected resources granted!") {
                        setIsAuthenticated(true);
                    } else {
                        setErrorMessage("Access to protected resources denied!");
                        console.log("Access to protected resources denied:", response.status);
                        router.push(LOGIN_URL_PATH); // Redirect to login if not authenticated
                    }
                } else {
                    setErrorMessage("An error occurred while checking authentication.");
                    console.log("An error occurred while checking authentication:", response.status);
                    router.push(LOGIN_URL_PATH); // Redirect to login if there's an error
                }
            } catch (error) {
                setErrorMessage("An error occurred during authentication check.");
                console.error("Authentication check error:", error);
                router.push(LOGIN_URL_PATH); // Redirect to login if there's an error
            }
        };

        checkAuthentication();
        fetchData();
    }, [fetchData]);

    if (errorMessage) {
        return (
            <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
                <div className="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-md">
                    <h1 className="text-3xl font-bold text-center">Error</h1>
                    <p className="text-red-500 text-center">{errorMessage}</p>
                </div>
            </main>
        );
    }

    if (!isAuthenticated) {
        return (
            <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
                <div className="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-md">
                    <h1 className="text-3xl font-bold text-center">Loading...</h1>
                </div>
            </main>
        );
    }

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Dashboard</h1>
                <p className="text-center">Welcome to your dashboard!</p>

                <h1 className="text-3xl font-bold text-center">Active Notifications</h1>
                <NotificationTable notifications={activeNotifications}/>
            </div>
        </main>
    );
}
