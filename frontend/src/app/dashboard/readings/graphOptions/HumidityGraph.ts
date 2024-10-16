import {HUMIDITY_LIMIT_COLOR, HUMIDITY_READING_COLOR} from "@/app/utils/constants";

export const HumidityGraphOptions = (limitValues: { minHumidity: number, maxHumidity: number }) => {
    const verticalPadding = 0;

    return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: true,
            },
            annotation: {
                annotations: {
                    highHumidityLine: {
                        type: 'line',
                        yMin: limitValues.maxHumidity,
                        yMax: limitValues.maxHumidity,
                        borderColor: HUMIDITY_LIMIT_COLOR,
                        borderWidth: 2,
                        label: {
                            content: "Max Humidity",
                            enabled: true,
                            position: 'end',
                        },
                    },
                    lowHumidityLine: {
                        type: 'line',
                        yMin: limitValues.minHumidity,
                        yMax: limitValues.minHumidity,
                        borderColor: HUMIDITY_LIMIT_COLOR,
                        borderWidth: 2,
                        label: {
                            content: "Min Humidity",
                            enabled: true,
                            position: 'end'
                        },
                    },
                },
            },
        },
        elements: {
            line: {
                tension: 0.5,
                borderWidth: 2,
                borderColor: HUMIDITY_READING_COLOR,
            },
            point: {
                radius: 5,
                hitRadius: 5,
            },
        },
        scales: {
            x: [
                {
                    type: "date",
                    display: false,
                    ticks: {
                        suggestedMin: 0,
                        beginAtZero: true,
                    }
                },
            ],
            y: [
                {
                    unit: "percent",
                    display: false,
                },
            ],
        },
        layout: {
            padding: {
                top: verticalPadding,
                bottom: verticalPadding,
            },
        },
    }
};