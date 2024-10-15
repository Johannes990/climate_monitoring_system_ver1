import {SensorReadingDTO} from "@/app/dto/climatedata/SensorReadingDTO";
import {fetchData} from "@/app/utils/functions";
import {
    READINGS_ALL_QUERY_PATH,
    READINGS_BY_SENSOR_QUERY_PATH
} from "@/app/utils/constants";

export async function fetchAllReadings(): Promise<SensorReadingDTO[]> {
    return fetchData(
        READINGS_ALL_QUERY_PATH,
        "failed to fetch sensor readings"
    );
}

export async function fetchReadingsBySensorId(sensorId: number): Promise<SensorReadingDTO[]> {
    return fetchData(
        `${READINGS_BY_SENSOR_QUERY_PATH}${sensorId}`,
        `Failed to fetch readings from sensor: ${sensorId}`
    );
}