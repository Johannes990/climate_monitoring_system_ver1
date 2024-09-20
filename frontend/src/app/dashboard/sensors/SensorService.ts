import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {fetchData} from "@/app/utils/functions";
import {
    LOCATIONS_QUERY_PATH, SENSORS_ADD_QUERY_PATH,
    SENSORS_ALL_QUERY_PATH,
    SENSORS_BY_DEVICECODE_QUERY_PATH, SENSORS_BY_LOCATION_QUERY_PATH,
    SENSORS_BY_PASSKEY_QUERY_PATH, SENSORS_DELETE_QUERY_PATH
} from "@/app/utils/constants";
import {LocationDTO} from "@/app/dto/climatedata/LocationDTO";
import {deleteRequest, postRequest} from "@/app/utils/api";

export async function fetchAllSensors(): Promise<SensorDTO[]> {
    return fetchData(SENSORS_ALL_QUERY_PATH, "Failed to fetch sensors");
}

export async function fetchSensorsByPasskey(passkey: string): Promise<SensorDTO[]> {
    return fetchData(
        `${SENSORS_BY_PASSKEY_QUERY_PATH}${passkey}`,
        `Failed to fetch sensors by passkey: ${passkey}`
    );
}

export async function fetchSensorsByDeviceCode(deviceCode: string): Promise<SensorDTO[]> {
    return fetchData(
        `${SENSORS_BY_DEVICECODE_QUERY_PATH}${deviceCode}`,
        `Failed to fetch sensors by device code: ${deviceCode}`
    );
}

export async function fetchSensorsByLocationId(locationId: string): Promise<SensorDTO[]> {
    return fetchData(
        `${SENSORS_BY_LOCATION_QUERY_PATH}${locationId}`,
        `Failed to fetch sensord by location id: ${locationId}`
    );
}

export async function createSensor(data: SensorDTO): Promise<void> {
    await postRequest(SENSORS_ADD_QUERY_PATH, data);
}

export async function deleteSensor(sensorId: number): Promise<void> {
    await deleteRequest(`${SENSORS_DELETE_QUERY_PATH}${sensorId}`);
}

export async function fetchLocationById(locationId: string): Promise<LocationDTO> {
    return fetchData(
        `${LOCATIONS_QUERY_PATH}${locationId}`,
        `Failed to fetch Location ${locationId} for Sensor`
    );
}