import {TEMPERATURE_LIMIT_COLOR, TEMPERATURE_READING_COLOR} from "@/app/utils/constants";

export const TempGraphOptions = (limitValues: { minTemp: number, maxTemp: number }) => {
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
                    highTempLime: {
                        type: 'line',
                        yMin: limitValues.maxTemp,
                        yMax: limitValues.maxTemp,
                        borderColor: TEMPERATURE_LIMIT_COLOR,
                        borderWidth: 2,
                        label: {
                            content: "Max Temp",
                            enabled: true,
                            position: 'end',
                        },
                    },
                    lowTempLine: {
                        type: 'line',
                        yMin: limitValues.minTemp,
                        yMax: limitValues.minTemp,
                        borderColor: TEMPERATURE_LIMIT_COLOR,
                        borderWidth: 2,
                        label: {
                            content: "Min Temp",
                            enabled: true,
                            position: 'end',
                        },
                    },
                },
            },
        },
        elements: {
            line: {
                tension: 0.5,
                borderWidth: 2,
                borderColor: TEMPERATURE_READING_COLOR,
            },
            point: {
                radius: 5,
                hitRadius: 5,
            },
        },
        scales: {
            x: {
                    type: "date",
                    display: true,
                    ticks: {
                        suggestedMin: 0,
                        beginAtZero: true,
                    }
            },
            y: {
                    unit: "c",
                    display: true,
            },
        },
        layout: {
            padding: {
                top: verticalPadding,
                bottom: verticalPadding,
            },
        },
    }
};
