import React, { useState } from "react";
import { LocationDTO } from "@/app/dto/climatedata/LocationDTO";
import { newLocation } from "@/app/utils/functions";
import {createLocation, fetchControlParameterSetById} from "@/app/dashboard/locations/LocationService";
import {useForm} from "@/app/dashboard/components/form/useForm";
import FormField from "@/app/dashboard/components/form/FormField";

interface LocationFormProps {
    onSuccess: () => void;
}

const LocationForm: React.FC<LocationFormProps> = ({onSuccess}) => {
    const { formData, handleInputChange, handleSubmit, loading, error } = useForm<LocationDTO>(newLocation());
    const [controlParameterSetId, setControlParameterSetId] = useState<string>("");

    const handleControlParameterSetIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setControlParameterSetId(e.target.value);
    };

    const onSubmit = async (parsedData: LocationDTO) => {
        const controlParameterSet = await fetchControlParameterSetById(controlParameterSetId);

        if (!controlParameterSet) throw new Error("Control Parameter Set not found");

        await createLocation({ ...parsedData, controlParameterSet });
        onSuccess();
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <h2 className="text-xl font-semibold">Create a New Location</h2>

            <FormField
                id="locationDescription"
                label="Location Description"
                value={formData.locationDescription ?? ""}
                onChange={handleInputChange}
            />
            <FormField
                id="controlParameterSetId"
                label="Control Parameter Set Id"
                value={controlParameterSetId}
                onChange={handleControlParameterSetIdChange}
            />

            {error && <p className="text-red-500">{error}</p>}

            <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-blue-500 rounded" disabled={loading}>
                {loading ? "Saving..." : "Save"}
            </button>
        </form>
    );
};

export default LocationForm;