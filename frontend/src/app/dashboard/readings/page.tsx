"use client";

import {
    Chart as ChartJS,
    CategoryScale,
    LineElement,
    LinearScale,
    PointElement,
    Title,
    Tooltip,
    Legend,
    Filler,
    BarElement,
    ArcElement,
} from "chart.js";
import annotationPlugin from 'chartjs-plugin-annotation';

ChartJS.register(
    CategoryScale,
    LineElement,
    LinearScale,
    PointElement,
    Title,
    Tooltip,
    Legend,
    Filler,
    BarElement,
    ArcElement,
    annotationPlugin,
);

import {Line} from "react-chartjs-2";
import React, {useCallback, useEffect, useState} from "react";
import {fetchReadingsBySensorId} from "@/app/dashboard/readings/ReadingService";
import {TempGraphOptions} from "@/app/dashboard/readings/graphOptions/TempGraph";
import {HumidityGraphOptions} from "@/app/dashboard/readings/graphOptions/HumidityGraph";
import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {fetchAllSensors} from "@/app/dashboard/sensors/SensorService";
import {HUMIDITY_READING_COLOR, TEMPERATURE_READING_COLOR} from "@/app/utils/constants";

interface SensorData {
    times: Date[],
    temps: number[],
    humidities: number[],
}

export default function Readings() {
    const [sensors, setSensors] = useState<SensorDTO[]>([]);
    const [sensorData, setSensorData] = useState<{ [sensorId: number]: SensorData }>({});
    const [error, setError] = useState<string>("");
    const graphHeight = "270px";
    const graphWidth = "500px";

    const fetchReadingsForAllSensors = useCallback(async () => {
        try {
            const sensors = await fetchAllSensors();
            setSensors(sensors);

            const dataPromises = sensors.map(async (sensor) => {
                const readings = await fetchReadingsBySensorId(sensor.sensorId);
                const sensorData: SensorData = {
                    times: readings.map(reading => reading.readingTime),
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
                        <h2 className="text-2xl font-semibold text-center">{sensor.location.locationDescription}</h2>

                        {sensorData[sensor.sensorId] ? (
                            <div className="flex flex-col md:flex-row md:space-x-4 items-center justify-center">
                                <div className="w-full md:w-1/2" style={{ height: graphHeight, width: graphWidth }}>
                                    <Line
                                        data={{
                                            labels: sensorData[sensor.sensorId].times,
                                            datasets: [{
                                                label: `${sensor.location.locationDescription} Temperature`,
                                                data: sensorData[sensor.sensorId].temps,
                                                backgroundColor: TEMPERATURE_READING_COLOR,
                                            }]
                                        }}
                                        options={TempGraphOptions({
                                            minTemp: limitValues.minTemp,
                                            maxTemp: limitValues.maxTemp,
                                        })}
                                    />
                                </div>
                                <div className="w-full md:w-1/2" style={{ height: graphHeight, width: graphWidth }}>
                                    <Line
                                        data={{
                                            labels: sensorData[sensor.sensorId].times,
                                            datasets: [{
                                                label: `${sensor.location.locationDescription} Humidity`,
                                                data: sensorData[sensor.sensorId].humidities,
                                                backgroundColor: HUMIDITY_READING_COLOR,
                                            }]
                                        }}
                                        options={HumidityGraphOptions({
                                            minHumidity: limitValues.minHumidity,
                                            maxHumidity: limitValues.maxHumidity,
                                        })}
                                    />
                                </div>

                            </div>
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