import React, {useState} from "react";
import {SensorDTO} from "@/app/dto/climatedata/SensorDTO";
import {newSensor} from "@/app/utils/functions";
import {LocationDTO} from "@/app/dto/climatedata/LocationDTO";
import {createSensor, fetchLocationById} from "@/app/dashboard/sensors/SensorService";

interface SensorFormProps {
    onSuccess: () => void;
}

const SensorForm: React.FC<SensorFormProps> = ({onSuccess}) => {
    const [formData, setFormData] = useState<SensorDTO>(newSensor());
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string>("");
    const [locationId, setLocationId] = useState<string>("");

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleLocationIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setLocationId(e.target.value);
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            const location: LocationDTO | null =
                await fetchLocationById(locationId);

            console.log("Location:",location);

            if (!location) return;

            console.log("made it past location null check");

            const parsedData: SensorDTO = {
                ...formData,
                location
            };

            console.log("parsed data set");

            await createSensor(parsedData);
            setFormData(newSensor());
            setLocationId("");
            onSuccess();
        } catch (err) {
            setError("Failed to save sensor");
            console.error(err);
        } finally {
            setLoading(false);
        }
    }

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
            <h2 className="text-xl font-semibold">Create a New Sensor</h2>

            <div className="space-y-2">
                <label htmlFor="passKey" className="block text-sm font-medium capitalize">
                    Sensor PassKey
                </label>
                <input
                    type="text"
                    id="passKey"
                    name="passKey"
                    value={formData.passKey}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded"
                    required
                />
            </div>
            <div className="space-y-2">
                <label htmlFor="deviceCode" className="block text-sm font-medium capitalize">
                    Sensor Device Code
                </label>
                <input
                    type="text"
                    id="deviceCode"
                    name="deviceCode"
                    value={formData.deviceCode}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded"
                    required
                />
            </div>
            <div className="space-y-2">
                <label htmlFor="tempUnit" className="block text-sm font-medium capitalize">
                    Sensor Temp Unit
                </label>
                <input
                    type="text"
                    id="tempUnit"
                    name="tempUnit"
                    value={formData.tempUnit}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded"
                    required
                />
            </div>
            <div className="space-y-2">
                <label htmlFor="location" className="block text-sm font-medium capitalize">
                    Sensor Location
                </label>
                <input
                    type="text"
                    id="location"
                    name="location"
                    value={locationId}
                    onChange={handleLocationIdChange}
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

export default SensorForm;