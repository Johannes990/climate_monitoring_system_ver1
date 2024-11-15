"use client";

import { getRequest } from "@/app/utils/api";
import React, {useCallback, useEffect, useState} from "react";
import { useRouter } from "next/navigation";
import {
    LOGIN_URL_PATH,
    PROTECTED_QUERY_PATH,
} from "@/app/utils/constants";
import {NotificationDTO} from "@/app/dto/notification/NotificationDTO";
import {fetchActiveNotifications} from "@/app/dashboard/notifications/notificationService";
import NotificationTable from "@/app/dashboard/components/NotificationTable";
import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {SensorData} from "@/app/dashboard/readings/SensorData";
import {fetchAllSensors} from "@/app/dashboard/sensors/SensorService";
import {fetchReadingsBySensorId} from "@/app/dashboard/readings/ReadingService";
import SensorGraph from "@/app/dashboard/components/SensorGraph";

export default function Dashboard() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const [activeNotifications, setActiveNotifications] = useState<NotificationDTO[]>([]);
    const [sensors, setSensors] = useState<SensorDTO[]>([]);
    const [sensorData, setSensorData] = useState<{ [sensorId: number]: SensorData }>({});
    const router = useRouter();

    const fetchData = useCallback(async () => {
        try {
            const activeNotifications = await fetchActiveNotifications(true);
            setActiveNotifications(activeNotifications);
        } catch (err) {
            setErrorMessage("Failed to fetch all notifications");
            console.error(err);
        }
    }, []);

    const fetchReadingsForAllSensors = useCallback(async () => {
        try {
            const sensors = await fetchAllSensors();
            setSensors(sensors);

            const dataPromises = sensors.map(async (sensor) => {
                const readings = await fetchReadingsBySensorId(sensor.sensorId);
                const sensorData: SensorData = {
                    times: readings.map(reading => new Date(reading.readingTime).toDateString()),
                    temps: readings.map(reading => reading.temperature),
                    humidities: readings.map(reading => reading.relHumidity),
                };

                return { sensorId: sensor.sensorId, data: sensorData }
            });

            const sensorReadings = await Promise.all(dataPromises);
            const sensorDataMap: { [sensorId: number]: SensorData } = sensorReadings.reduce((acc, { sensorId, data }) => {
                acc[sensorId] = data;
                return acc;
            }, {});

            setSensorData(sensorDataMap);
        } catch (err) {
            setErrorMessage("Failed to fetch readings for all sensors. " + err);
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
        fetchReadingsForAllSensors();
    }, [fetchData, fetchReadingsForAllSensors]);

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

                <h1 className="text-3xl font-bold text-center">Sensor Readings</h1>
                {sensors.map(sensor => {
                    const limitValues = {
                        minTemp: sensor.location.controlParameterSet.tempNorm - sensor.location.controlParameterSet.tempTolerance,
                        maxTemp: sensor.location.controlParameterSet.tempNorm + sensor.location.controlParameterSet.tempTolerance,
                        minHumidity: sensor.location.controlParameterSet.relHumidityNorm - sensor.location.controlParameterSet.relHumidityTolerance,
                        maxHumidity: sensor.location.controlParameterSet.relHumidityNorm + sensor.location.controlParameterSet.relHumidityTolerance
                    };

                    return (
                        <div key={sensor.sensorId} className="mb-8">


                            {sensorData[sensor.sensorId] ? (
                                <SensorGraph
                                    key={sensor.sensorId}
                                    sensorId={sensor.sensorId}
                                    locationDescription={sensor.location.locationDescription}
                                    times={sensorData[sensor.sensorId].times}
                                    temps={sensorData[sensor.sensorId].temps}
                                    humidities={sensorData[sensor.sensorId].humidities}
                                    tempLimits={{
                                        mintemp: limitValues.minTemp,
                                        maxTemp: limitValues.maxTemp,
                                    }}
                                    humidityLimits={{
                                        minHumidity: limitValues.minHumidity,
                                        maxHumidity: limitValues.maxHumidity,
                                    }}
                                />
                            ) : (
                                <p className="text-center">No data available for this sensor.</p>
                            )}
                        </div>
                    );
                })}
            </div>
        </main>
    );
}
