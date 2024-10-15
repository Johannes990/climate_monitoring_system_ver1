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
    ArcOptions
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

import {Bar, Line, Scatter, Bubble, Doughnut} from "react-chartjs-2";
import React, {useCallback, useEffect, useState} from "react";
import {SensorReadingDTO} from "@/app/dto/climatedata/SensorReadingDTO";
import {fetchAllReadings} from "@/app/dashboard/readings/ReadingsService";

export default function Readings() {
    const [readings, setReadings] = useState<SensorReadingDTO[]>([]);
    const [error, setError] = useState<string>("");

    // testing data for ChartJs / react-chartjs-2
    const lineData = {
        labels: ['january', 'february', 'march', 'april', 'may', 'june', 'july'],
        datasets: [
            {
                data: [0.4, 0.5, 0.4, 1.0, 0.2, 0.4, 0.7],
            },
        ],
    };

    const barData = {
        labels: ["january", "february", "march", "april", "may", "may", "may", "may"],
        datasets: [
            {
                label: "brutto",
                borderRadius: 30,
                data: [0.1, 0.3, 0.4, 0.3, 0.6, 0.4, 0.9, 0.2],
                backgroundColor: "#25A844",
                barThickness: 10,
            },
            {
                label: "netto",
                borderRadius: 20,
                data: [0.05, 0.23, 0.3, 0.25, 0.49, 0.3, 0.8, 0.14],
                backgroundColor: "#9924CD",
                barThickness: 10,
            },
        ],
    };

    const doughnutData = {
        backgroundColor: [
            "#0044DD",
            "#0088AA",
            "#00BB88",
            "#00DD44",
        ],
        labels: ["data1", "data2", "data3", "data4"],
        datasets: [
            {
                label: "my doughnut chart dataset",
                data: [234, 34, 65, 136],
                backgroundColor: [
                    "#0044DD",
                    "#0088AA",
                    "#00BB88",
                    "#00DD44",
                ],
                hoverOffset: 30,
            },
        ],
    };

    // visual options for charts
    const lineOptions = {
        plugins: {
            legend: {
                display: false,
            },
        },
        elements: {
            line: {
                tension: 0.5,
                borderWidth: 5,
                borderColor: "#",
                fill: "start",
                backgroundColor: "#F0F0F0"
            },
            point: {
                radius: 0,
                hitRadius: 0,
            },
        },
        scales: {
            xAxis: {
                display: false,
            },
            yAxis: {
                display: false,
            },
        },
    };

    const barOptions = {
        plugins: {
            legend: {
                position: "top",
                align: "start",
                labels: {
                    boxWidth: 10,
                    usePointStyle: true,
                    pointStyle: "dots",
                },
                title: {
                    text: "sales report",
                    display: true,
                    color: "#000",
                    font: {
                        size: 18,
                    },
                },
            },
        },
        scales: {
            xAxis: {
                display: false,
            },
            yAxis: {
                display: false,
                max: 1,
            },
        },
        elements: {
            bar: {
                barPercentage: 1,
                categoryPercentage: 0.6,
            },
        },
    };

    const doughnutOptions = {
        elemets: {
            arc: {
                weight: 0.5,
                borderWidth: 3,
            },
        },
        cutout: 130,
    };

    const fetchData = useCallback(async () => {
        try {
            const data = await fetchAllReadings();
            setReadings(data);
        } catch (err) {
            setError("Failed to fetch all readings.");
            console.error(err);
        }
    }, []);

    useEffect(() => {
        window.history.replaceState(null, "", window.location.href);
        fetchData();
    }, [fetchData]);

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-lg p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Sensor Readings</h1>
                <Line data={lineData} options={lineOptions} />
                <Bar data={barData} options={barOptions} />
                <Doughnut data={doughnutData} options={doughnutOptions} />
            </div>
        </main>
    )
}