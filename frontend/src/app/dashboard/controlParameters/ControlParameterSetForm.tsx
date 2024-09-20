import React, {useState} from "react";
import {ControlParameterSetDTO} from "@/app/dto/climatedata/ControlParameterSetDTO";
import {newControlParameterSet} from "@/app/utils/functions";
import {createControlParameterSet} from "@/app/dashboard/controlParameters/ControlParameterSetService";

interface ControlParameterSetFormProps {
    onSuccess: () => void; // this callback triggers when form submission is a success
}

const ControlParameterSetForm: React.FC<ControlParameterSetFormProps> = ({ onSuccess }) => {
    const [formData, setFormData] = useState<ControlParameterSetDTO>(newControlParameterSet());
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const parsedData = {
                ...formData,
                tempNorm: Number(formData.tempNorm),
                tempTolerance: Number(formData.tempTolerance),
                relHumidityNorm: Number(formData.relHumidityNorm),
                relHumidityTolerance: Number(formData.relHumidityTolerance),
            };
            await createControlParameterSet(parsedData);
            setFormData(newControlParameterSet());
            onSuccess();
        } catch (err) {
            setError("Failed to save control parameter set.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
            <h2 className="text-xl funt-semibold">Create a New Control Parameter Set</h2>

            {["tempNorm", "tempTolerance", "relHumidityNorm", "relHumidityTolerance"].map((field, idx) => (
                <div key={idx} className="space-y-2">
                    <label htmlFor={field} className="block text-sm font-medium capitalize">
                        {field.replace(/([A-Z])/g, ' $1')}
                    </label>
                    <input
                        type="number"
                        id={field}
                        name={field}
                        value={formData[field as keyof ControlParameterSetDTO]}
                        onChange={handleInputChange}
                        className="w-full px-3 py-2 border rounded"
                        required
                    />
                </div>
            ))}

            {error && <p className="text-red-500">{error}</p>}

            <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-blue-500 rounded" disabled={loading}>
                {loading ? "Saving..." : "Save"}
            </button>
        </form>
    );
};

export default ControlParameterSetForm;