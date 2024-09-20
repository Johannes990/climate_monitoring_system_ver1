import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";


export interface SensorReadingDTO {
    sensorReadingId?: number;
    temperature: number;
    relHumidity: number;
    readingTime?: Date;
    sensor: SensorDTO;
}