import React from "react";
import { ControlParameterSetDTO } from "@/app/dto/climatedata/ControlParameterSetDTO";
import { newControlParameterSet } from "@/app/utils/functions";
import { createControlParameterSet } from "@/app/dashboard/controlParameters/ControlParameterSetService";
import {useForm} from "@/app/dashboard/components/form/useForm";
import FormField from "@/app/dashboard/components/form/FormField";

interface ControlParameterSetFormProps {
    onSuccess: () => void; // this callback triggers when form submission is a success
}

const ControlParameterSetForm: React.FC<ControlParameterSetFormProps> = ({ onSuccess }) => {
    const { formData, handleInputChange, handleSubmit, loading, error } =
        useForm<ControlParameterSetDTO>(newControlParameterSet());

    const onSubmit = async (parsedData: ControlParameterSetDTO) => {
        const normalizedData = {
            ...parsedData,
            tempNorm: Number(parsedData.tempNorm),
            tempTolerance: Number(parsedData.tempTolerance),
            relHumidityNorm: Number(parsedData.relHumidityNorm),
            relHumidityTolerance: Number(parsedData.relHumidityTolerance),
        };

        await createControlParameterSet(normalizedData);
        onSuccess();
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <h2 className="text-xl funt-semibold">Create a New Control Parameter Set</h2>

            {["tempNorm", "tempTolerance", "relHumidityNorm", "relHumidityTolerance"].map((field, idx) => (
                <FormField
                    key={idx}
                    id={field}
                    label={field.replace(/([A-Z])/g, ' $1')}
                    value={formData[field as keyof ControlParameterSetDTO] ?? ""}
                    onChange={handleInputChange}
                    type="number"
                />
            ))}

            {error && <p className="text-red-500">{error}</p>}

            <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-blue-500 rounded" disabled={loading}>
                {loading ? "Saving..." : "Save"}
            </button>
        </form>
    );
};

export default ControlParameterSetForm;