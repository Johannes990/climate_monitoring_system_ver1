"use client";

import  "../globals.css";
import React, { useState } from "react";
import {
    DASHBOARD_CONTROLPARAM_URL_PATH,
    DASHBOARD_LOCATIONS_URL_PATH, DASHBOARD_NOTIFICATIONS_PATH,
    DASHBOARD_SENSOR_READINGS_URL_PATH,
    DASHBOARD_SENSORS_URL_PATH,
    DASHBOARD_URL_PATH,
    LOGIN_URL_PATH,
    LOGOUT_QUERY_PATH
} from "../utils/constants"
import { getRequest } from "@/app/utils/api";
import { useRouter } from "next/navigation";

export default function LoginLayout({ children }: { children: React.ReactNode}) {
    const [errorMessage, setErrorMessage] = useState("");
    const router = useRouter();

    async function logout() {
        try {
            const response = await getRequest(LOGOUT_QUERY_PATH);

            if (response.ok) {
                sessionStorage.removeItem("userSession");
                console.log("user session removed from session storage");
                localStorage.removeItem("userToken");
                console.log("user token removed from local storage");
                router.push(LOGIN_URL_PATH);
            } else {
                setErrorMessage("Logout failed!");
            }
        } catch (error) {
            setErrorMessage("An error occurred during logout.");
            console.error("Logout error:", error);
        }
    }

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

    return (
        <div>
            <nav>
                <div className="navbar">
                    <div className="navbar-container flex items-center justify-between">
                        <>
                            <div className="logo">
                                <a href={DASHBOARD_URL_PATH}>
                                    <img
                                        src="/assets/NOTE-AB.png"
                                        alt="Company logo"
                                        className="h-16 w-auto"
                                    />
                                </a>
                            </div>
                            <div className="nav-links flex space-x-4">
                                <a href={DASHBOARD_URL_PATH} className="nav-link">Dashboard</a>
                                <a href={DASHBOARD_CONTROLPARAM_URL_PATH} className="nav-link">Control Parameters</a>
                                <a href={DASHBOARD_LOCATIONS_URL_PATH} className="nav-link">Locations</a>
                                <a href={DASHBOARD_SENSORS_URL_PATH} className="nav-link">Sensors</a>
                                <a href={DASHBOARD_SENSOR_READINGS_URL_PATH} className="nav-link">Sensor Readings</a>
                                <a href={DASHBOARD_NOTIFICATIONS_PATH} className="nav-link">Notifications</a>
                            </div>
                        </>
                        <>
                        <div className="nav-button" onClick={logout}>
                                <button>Logout</button>
                            </div>
                        </>
                    </div>
                </div>
            </nav>
            <main>{children}</main>
        </div>
    );
}