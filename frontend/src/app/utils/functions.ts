import {ControlParameterSetDTO} from "@/app/dto/climatedata/ControlParameterSetDTO";
import {LocationDTO} from "@/app/dto/climatedata/LocationDTO";
import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {SensorReadingDTO} from "@/app/dto/climatedata/SensorReadingDTO";
import {getRequest} from "@/app/utils/api";


export function newControlParameterSet(): ControlParameterSetDTO {
    return {
        controlParameterSetId: undefined,
        tempNorm: 0.0,
        tempTolerance: 0.0,
        relHumidityNorm: 0.0,
        relHumidityTolerance: 0.0,
    };
}

export function newLocation(): LocationDTO {
    return {
        locationId: undefined,
        locationDescription: "",
        controlParameterSet: newControlParameterSet(),
    };
}

export function newSensor(): SensorDTO {
    return {
        sensorId: undefined,
        passKey: "",
        deviceCode: "",
        tempUnit: "",
        location: newLocation(),
    };
}

export function newSensorReading(): SensorReadingDTO {
    return {
        sensorReadingId: undefined,
        temperature: 0.0,
        relHumidity: 0.0,
        readingTime: undefined,
        sensor: newSensor(),
    };
}

export async function fetchData(queryPath: string, errorMsg: string) {
    const response = await getRequest(queryPath);

    if (response.ok) {
        return response.json()
    }

    throw new Error(errorMsg);
}