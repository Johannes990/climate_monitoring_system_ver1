"use client";

import React, {useCallback, useEffect, useState} from "react";
import {fetchReadingsBySensorId} from "@/app/dashboard/readings/ReadingService";
import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {fetchAllSensors} from "@/app/dashboard/sensors/SensorService";
import SensorGraph from "@/app/dashboard/components/SensorGraph";

interface SensorData {
    times: Date[],
    temps: number[],
    humidities: number[],
}

export default function Readings() {
    const [sensors, setSensors] = useState<SensorDTO[]>([]);
    const [sensorData, setSensorData] = useState<{ [sensorId: number]: SensorData }>({});
    const [error, setError] = useState<string>("");

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
            setError("Failed to fetch readings for all sensors. " + err);
            console.error(err);
        }
    }, []);

    useEffect(() => {
        window.history.replaceState(null, "", window.location.href);
        fetchReadingsForAllSensors();
    }, [fetchReadingsForAllSensors]);

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            {error && <p className="text-red-500">{error}</p>}

            <div className="w-full max-w-4xl p-8 space-y-6 bg-white rounded shadow-md">
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