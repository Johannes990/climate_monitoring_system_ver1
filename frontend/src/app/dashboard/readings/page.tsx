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
);

import {Bar, Line, Doughnut} from "react-chartjs-2";
import React, {useCallback, useEffect, useState} from "react";
import {SensorReadingDTO} from "@/app/dto/climatedata/SensorReadingDTO";
import {fetchAllReadings} from "@/app/dashboard/readings/ReadingsService";

export default function Readings() {
    const [readings, setReadings] = useState<SensorReadingDTO[]>([]);
    const [error, setError] = useState<string>("");
    const [times, setTimes] = useState<Date[]>([]);
    const [readingVals, setReadingVals] = useState<number[]>([]);

    const fetchData = useCallback(async () => {
        try {
            const data = await fetchAllReadings();
            setReadings(data);
            var timeStamps = [];
            var readingData = [];
            for (var i = 0; i < data.length; i++) {
                readingData.push(data[i].temperature);
                timeStamps.push(data[i].readingTime);
            }
            setTimes(timeStamps);
            setReadingVals(readingData);
        } catch (err) {
            setError("Failed to fetch all readings." + err);
            console.error(err);
        }
    }, []);

    // visual options for charts
    const lineOptions = {
        plugins: {
            legend: {
                display: true,
            },
        },
        elements: {
            line: {
                tension: 0.5,
                borderWidth: 2,
                borderColor: "#FF0000",
            },
            point: {
                radius: 5,
                hitRadius: 5,
            },
        },
        scales: {
            xAxis: [
                {
                    type: "date",
                    display: false,
                    ticks: {
                        suggestedMin: 0,
                        beginAtZero: true,
                    }
                },
            ],
            yAxis: [
                {
                    unit: "c",
                    display: false,
                },
            ],
        },
    };

    const sensorsData = {
        labels: times,
        datasets: [
            {
                label: "sensor data",
                data: readingVals,
            },
        ],
    };

    useEffect(() => {
        window.history.replaceState(null, "", window.location.href);
        fetchData();
    }, [fetchData]);

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Sensor Readings</h1>

                {error && <p className="text-red-500">{error}</p>}

                <Line data={sensorsData} options={lineOptions} />
            </div>
        </main>
    )
}