import React, {useState} from "react";
import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {newSensor} from "@/app/utils/functions";
import {createSensor, fetchLocationById} from "@/app/dashboard/sensors/SensorService";
import {useForm} from "@/app/dashboard/components/form/useForm";
import FormField from "@/app/dashboard/components/form/FormField";

interface SensorFormProps {
    onSuccess: () => void;
}

const SensorForm: React.FC<SensorFormProps> = ({onSuccess}) => {
    const { formData, handleInputChange, handleSubmit, loading, error } = useForm<SensorDTO>(newSensor());
    const [locationId, setLocationId] = useState<string>("");

    const handleLocationIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLocationId(e.target.value);
    };

    const onSubmit = async (parsedData: SensorDTO) => {
        const location = await fetchLocationById(locationId);

        if (!location) throw new Error("Location not found");

        await createSensor({ ...parsedData, location });
        onSuccess();
    };


    return (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <h2 className="text-xl font-semibold">Create a New Sensor</h2>

            <FormField
                id="passKey"
                label="Sensor PassKey"
                value={formData.passKey}
                onChange={handleInputChange}
            />
            <FormField
                id="deviceCode"
                label="Sensor Device Code"
                value={formData.deviceCode}
                onChange={handleInputChange}
            />
            <FormField
                id="tempUnit"
                label="Sensor Temp Unit"
                value={formData.tempUnit}
                onChange={handleInputChange}
            />
            <FormField
                id="locationId"
                label="Location ID"
                value={locationId}
                onChange={handleLocationIdChange}
            />

            {error && <p className="text-red-500">{error}</p>}

            <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-blue-500 rounded" disabled={loading}>
                {loading ? "Saving..." : "Save"}
            </button>
        </form>
    );
};

export default SensorForm;