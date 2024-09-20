import {ControlParameterSetDTO} from "@/app/dto/climatedata/ControlParameterSetDTO";

export interface LocationDTO {
    locationId?: number;
    locationDescription?: string;
    controlParameterSet: ControlParameterSetDTO;
}