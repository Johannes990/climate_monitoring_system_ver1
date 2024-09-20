import { LocationDTO } from "@/app/dto/climatedata/LocationDTO";
import { postRequest, deleteRequest } from "@/app/utils/api";
import {
    LOCATIONS_ALL_QUERY_PATH,
    LOCATIONS_ADD_QUERY_PATH,
    LOCATIONS_DELETE_QUERY_PATH,
    LOCATIONS_BY_DESCRIPTION_CONTAINING_QUERY_PATH, CONTROL_PARAMS_QUERY_PATH
} from "@/app/utils/constants";
import {fetchData} from "@/app/utils/functions";
import {ControlParameterSetDTO} from "@/app/dto/climatedata/ControlParameterSetDTO";

export async function fetchAllLocations(): Promise<LocationDTO[]> {
    return fetchData(LOCATIONS_ALL_QUERY_PATH, "Failed to fetch locations");
}

export async function fetchLocationsByDescription(description: string): Promise<LocationDTO[]> {
    return fetchData(
        `${LOCATIONS_BY_DESCRIPTION_CONTAINING_QUERY_PATH}${description}`,
        `Failed to fetch locations containing description: ${description}`
    );
}

export async function createLocation(data: LocationDTO): Promise<void> {
    await postRequest(LOCATIONS_ADD_QUERY_PATH, data);
}

export async function deleteLocation(id: number): Promise<void> {
    await deleteRequest(`${LOCATIONS_DELETE_QUERY_PATH}${id}`);
}

export async function fetchControlParameterSetById(id: string): Promise<ControlParameterSetDTO | null> {
    return fetchData(
        `${CONTROL_PARAMS_QUERY_PATH}${id}`,
        `Failed to fetch Control Parameter Set ${id} for Location`
    );
}
