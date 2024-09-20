import {LocationDTO} from "@/app/dto/climatedata/LocationDTO";

export interface SensorDTO {
    sensorId?: number;
    passKey: string;
    deviceCode: string;
    tempUnit: string;
    location: LocationDTO
}