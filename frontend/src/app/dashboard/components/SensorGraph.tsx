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
import {HUMIDITY_READING_COLOR, TEMPERATURE_READING_COLOR} from "@/app/utils/constants";
import {TempGraphOptions} from "@/app/dashboard/readings/graphOptions/TempGraph";
import {HumidityGraphOptions} from "@/app/dashboard/readings/graphOptions/HumidityGraph";

interface SensorGraphProps {
    sensorId: number;
    locationDescription: string;
    times: Date[];
    temps: number[];
    humidities: number[];
    tempLimits: { mintemp: number, maxTemp: number };
    humidityLimits: { minHumidity: number, maxHumidity: number };
}

const SensorGraph: React.FC<SensorGraphProps> = ({
    sensorId,
    locationDescription,
    times,
    temps,
    humidities,
    tempLimits,
    humidityLimits
}) => {
    const graphHeight = "270px";
    const graphWidth = "500px";

    return (
        <div className="mb-8">
            <h2 className="text-2xl font-semibold text-center">{locationDescription}</h2>
            <div className="flex flex-col md:flex-row md:space-x-4 items-center justify-center">
                <div className="w-full md:w-1/2" style={{ height: graphHeight, width: graphWidth }}>
                    <Line
                        data={{
                            labels: times,
                            datasets: [{
                                label: `${locationDescription} Temperature`,
                                data: temps,
                                backgroundColor: TEMPERATURE_READING_COLOR,
                            }]
                    }}
                        options={TempGraphOptions({
                            minTemp: tempLimits.mintemp,
                            maxTemp: tempLimits.maxTemp,
                        })}
                    />
                </div>
                <div className="w-full md:w-1/2" style={{ height: graphHeight, width: graphWidth }}>
                    <Line
                        data={{
                            labels:times,
                            datasets: [{
                                label: `${locationDescription} Humidity`,
                                data: humidities,
                                backgroundColor: HUMIDITY_READING_COLOR,
                            }]
                        }}
                        options={HumidityGraphOptions({
                            minHumidity: humidityLimits.minHumidity,
                            maxHumidity: humidityLimits.maxHumidity,
                        })}
                    />
                </div>
            </div>
        </div>
    );
};

export default SensorGraph;