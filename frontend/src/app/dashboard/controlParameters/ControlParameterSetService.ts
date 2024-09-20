import { ControlParameterSetDTO } from "@/app/dto/climatedata/ControlParameterSetDTO";
import { deleteRequest, getRequest, postRequest } from "@/app/utils/api";
import {
    CONTROL_PARAMS_ADD_QUERY_PATH,
    CONTROL_PARAMS_ALL_QUERY_PATH,
    CONTROL_PARAMS_DELETE_PATH
} from "@/app/utils/constants";
import { fetchData } from "@/app/utils/functions";

export async function fetchAllControlParameters(): Promise<ControlParameterSetDTO[]> {
    return fetchData(CONTROL_PARAMS_ALL_QUERY_PATH, "Failed to fetch control parameters");
}

export async function createControlParameterSet(data: ControlParameterSetDTO): Promise<void> {
    await postRequest(CONTROL_PARAMS_ADD_QUERY_PATH, data);
}

export async function deleteControlParameterSet(id: number): Promise<void> {
    await deleteRequest(`${CONTROL_PARAMS_DELETE_PATH}${id}`);
}