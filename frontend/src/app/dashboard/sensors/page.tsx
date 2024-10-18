"use client";

import React, {useCallback, useEffect, useState} from "react";
import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {deleteSensor, fetchAllSensors} from "@/app/dashboard/sensors/SensorService";
import SensorForm from "@/app/dashboard/sensors/SensorForm";

export default function Sensors() {
    const [sensors, setSensors] = useState<SensorDTO[]>([]);
    const [sensorDeleteId, setSensorDeleteId] = useState<number | undefined>(undefined);
    const [error, setError] = useState<string>("");

    const fetchData = useCallback(async () => {
        try {
            const data = await fetchAllSensors();
            setSensors(data);
        } catch (err) {
            setError("Failed to fetch all sensors.");
            console.error(err);
        }
    }, []);

    useEffect(() => {
        window.history.replaceState(null, "", window.location.href);
        fetchData();
    }, [fetchData]);

    const handleDeleteSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!sensorDeleteId) return;

        const confirmed = window.confirm(
            `Are you sure you want to delete the sensor with id: ${sensorDeleteId}?`
        );

        if (!confirmed) return;

        try {
            await deleteSensor(sensorDeleteId);
            setSensorDeleteId(undefined);
            fetchData();
        } catch (err) {
            console.error(`Error deleting sensor with id: ${sensorDeleteId}`);
            setError("Failed to delete sensor.")
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-lg p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Locations</h1>

                {error && <p className="text-red-500">{error}</p>}

                <div className="overflow-x-auto w-full">
                    <table className="min-w-full bg-white border">
                        <thead>
                        <tr>
                            <th className="px-4 py-2 border">ID</th>
                            <th className="px-4 py-2 border">Sensor Passkey</th>
                            <th className="px-4 py-2 border">Sensor Device Code</th>
                            <th className="px-4 py-2 border">Sensor Temperature Unit</th>
                            <th className="px-4 py-2 border">Location Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        {sensors.map((sensor) => (
                            <tr key={sensor.sensorId}>
                                <th className="px-4 py-2 border">{sensor.sensorId}</th>
                                <th className="px-4 py-2 border">{sensor.passKey}</th>
                                <th className="px-4 py-2 border">{sensor.deviceCode}</th>
                                <th className="px-4 py-2 border">{sensor.tempUnit}</th>
                                <th className="px-4 py-2 border">{sensor.location.locationDescription}</th>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                <SensorForm onSuccess={fetchData} />

                <form onSubmit={handleDeleteSubmit} className="space-y-4">
                    <h2 className="text-xl font-semibold">Delete An Existing Sensor</h2>

                    <div className="space-y-4">
                        <label htmlFor="sensorDeleteId" className="block text-sm font-medium">
                            Delete ID
                        </label>
                        <input
                            type="number"
                            id="sensorDeleteId"
                            name="sensorDeleteId"
                            value={sensorDeleteId}
                            onChange={(e) => setSensorDeleteId(Number(e.target.value))}
                            className="w-full px-3 py-2 border rounded"
                            required
                        />
                    </div>
                    <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-red-500 rounded">
                        Delete
                    </button>
                </form>
            </div>
        </main>
    );
}