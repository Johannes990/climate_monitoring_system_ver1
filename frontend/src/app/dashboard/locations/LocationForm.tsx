import React, { useState } from "react";
import { LocationDTO } from "@/app/dto/climatedata/LocationDTO";
import { newLocation } from "@/app/utils/functions";
import {createLocation, fetchControlParameterSetById} from "@/app/dashboard/locations/LocationService";
import { ControlParameterSetDTO } from "@/app/dto/climatedata/ControlParameterSetDTO";

interface LocationFormProps {
    onSuccess: () => void;
}

const LocationForm: React.FC<LocationFormProps> = ({onSuccess}) => {
    const [formData, setFormData] = useState<LocationDTO>(newLocation());
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string>("");
    const [controlParameterSetId, setControlParameterSetId] = useState<string>("");

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleControlParameterSetIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setControlParameterSetId(e.target.value);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            const controlParameterSet: ControlParameterSetDTO | null =
                await fetchControlParameterSetById(controlParameterSetId);

            if (!controlParameterSet) {
                throw new Error("Control Parameter Set not found during Location addition");
            }

            const parsedData: LocationDTO = {
                ...formData,
                controlParameterSet
            };

            await createLocation(parsedData);
            setFormData(newLocation());
            setControlParameterSetId("");
            onSuccess();
        } catch (err) {
            setError("Failed to save location");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
            <h2 className="text-xl font-semibold">Create a New Location</h2>

            <div className="space-y-2">
                <label htmlFor="locationDescription" className="block text-sm font-medium capitalize">
                    location description
                </label>
                <input
                    type="text"
                    id="locationDescription"
                    name="locationDescription"
                    value={formData.locationDescription}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded"
                    required
                />
            </div>
            <div className="space-y-2">
                <label htmlFor="controlParameterSetId" className="block text-sm font-medium capitalize">
                    Control Parameter Set ID
                </label>
                <input
                    type="text"
                    id="controlParameterSetId"
                    name="controlParameterSetId"
                    value={controlParameterSetId}
                    onChange={handleControlParameterSetIdChange}
                    className="w-full px-3 py-2 border rounded"
                    required
                />
            </div>

            {error && <p className="text-red-500">{error}</p>}

            <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-blue-500 rounded" disabled={loading}>
                {loading ? "Saving..." : "Save"}
            </button>
        </form>
    );
};

export default LocationForm;