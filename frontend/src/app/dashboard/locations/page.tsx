"use client";

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { getRequest, postRequest, deleteRequest } from "@/app/utils/api";
import { LocationDTO } from "@/app/dto/climatedata/LocationDTO";
import {
    LOCATIONS_ALL_QUERY_PATH,
    LOCATIONS_BY_DESCRIPTION_CONTAINING_QUERY_PATH,
    LOCATIONS_ADD_QUERY_PATH,
    LOCATIONS_DELETE_QUERY_PATH,
    CONTROL_PARAMS_QUERY_PATH,
    initialLocationData
} from "@/app/utils/constants";
import { ControlParameterSetDTO } from "@/app/dto/climatedata/ControlParameterSetDTO";

export default function Locations() {
    const [controlParameterSetId, setControlParameterSetId] = useState("");
    const [controlParameterSetData, setControlParameterSetData] = useState<ControlParameterSetDTO>();
    const [newLocationData, setNewLocationData] = useState<LocationDTO>(initialLocationData);
    const router = useRouter();

    const fetchControlParameterData = async () => {
        const response = await getRequest(CONTROL_PARAMS_QUERY_PATH + controlParameterSetId);
        try {
            if (response.ok) {
                const data = await response.json();
                setControlParameterSetData(data);
            }
        } catch (error) {
            console.error(
                "Error fetching control parameter set id:"
                + controlParameterSetId + "for location:",
                error);
        }
    }
}