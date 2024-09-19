"use client";

import { useRouter } from "next/navigation";
import {useEffect, useState} from "react";
import { getRequest, postRequest } from "@/app/utils/api";
import { ControlParameterSetDTO } from "../../dto/climatedata/ControlParameterSetDTO";

const initialControlParameterData: ControlParameterSetDTO = {
    controlParameterSetId: undefined,
    tempNorm: 0.0,
    tempTolerance: 0.0,
    relHumidityNorm: 0.0,
    relHumidityTolerance: 0.0,
};

export default function ControlParameters() {
    const [controlParameterData, setControlParameterData] = useState<ControlParameterSetDTO[]>([]);
    const [newControlParameterSet, setNewControlParameterSet] =
        useState<ControlParameterSetDTO>(initialControlParameterData);
    const router = useRouter();

    console.log(JSON.stringify(newControlParameterSet));

    const fetchControlParameterData = async () => {
        const response = await getRequest("/controlparams/all");
        try {
            if (response.ok) {
                const data = await response.json();
                setControlParameterData(data);
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    };

    useEffect(() => {
        fetchControlParameterData();
    }, []);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        console.log("Parameter set form submitted");

        const parsedParameterSetData = {
            ...newControlParameterSet,
            tempNorm: parseFloat(newControlParameterSet.tempNorm as unknown as string),
            tempTolerance: parseFloat(newControlParameterSet.tempTolerance as unknown as string),
            relHumidityNorm: parseFloat(newControlParameterSet.relHumidityNorm as unknown as string),
            relHumidityTolerance: parseFloat(newControlParameterSet.relHumidityTolerance as unknown as string),
        };

        try {
            await postRequest("/controlparams/add", parsedParameterSetData);
            setNewControlParameterSet(initialControlParameterData);
            fetchControlParameterData();
            router.refresh();
        } catch (error) {
            console.error("Error submitting form:", error);
        }
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setNewControlParameterSet({
            ...newControlParameterSet,
            [name]: value,
        });
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-lg p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Control Parameters</h1>

                <table className="min-w-full bg-white border">
                    <thead>
                    <tr>
                        <th className="px-4 py-2 border">ID</th>
                        <th className="px-4 py-2 border">Temp Norm</th>
                        <th className="px-4 py-2 border">Temp Tolerance</th>
                        <th className="px-4 py-2 border">Relative Humidity Norm</th>
                        <th className="px-4 py-2 border">Relative Humidity Tolerance</th>
                    </tr>
                    </thead>
                    <tbody>
                    {controlParameterData.map((param) => (
                        <tr key={param.controlParameterSetId}>
                            <td className="px-4 py-2 border">{param.controlParameterSetId}</td>
                            <td className="px-4 py-2 border">{param.tempNorm}</td>
                            <td className="px-4 py-2 border">{param.tempTolerance}</td>
                            <td className="px-4 py-2 border">{param.relHumidityNorm}</td>
                            <td className="px-4 py-2 border">{param.relHumidityTolerance}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>

                {/* Form to create a new control parameter set to be saved in the system */}
                <form onSubmit={handleSubmit} className="space-y-4">
                    <h2 className="text-xl font-semibold">Create A New ControlParameter Set</h2>

                    <div className="space-y-2">
                        <label htmlFor="tempNorm" className="block text-sm font-medium">
                            Temp Norm
                        </label>
                        <input
                            type="number"
                            id="tempNorm"
                            name="tempNorm"
                            value={newControlParameterSet.tempNorm}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border rounded"
                            required
                        />
                    </div>
                    <div className="space-y-2">
                        <label htmlFor="tempNorm" className="block text-sm font-medium">
                            Temp Tolerance
                        </label>
                        <input
                            type="number"
                            id="tempTolerance"
                            name="tempTolerance"
                            value={newControlParameterSet.tempTolerance}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border rounded"
                            required
                        />
                    </div>
                    <div className="space-y-2">
                        <label htmlFor="tempNorm" className="block text-sm font-medium">
                            Relative Humidity Norm
                        </label>
                        <input
                            type="number"
                            id="relHumidityNorm"
                            name="relHumidityNorm"
                            value={newControlParameterSet.relHumidityNorm}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border rounded"
                            required
                        />
                    </div>
                    <div className="space-y-2">
                        <label htmlFor="tempNorm" className="block text-sm font-medium">
                            Relative Humidity Tolerance
                        </label>
                        <input
                            type="number"
                            id="relHumidityTolerance"
                            name="relHumidityTolerance"
                            value={newControlParameterSet.relHumidityTolerance}
                            onChange={handleChange}
                            className="w-full px-3 py-2 border rounded"
                            required
                        />
                    </div>
                    <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-blue-500 rounded">
                        Save
                    </button>
                </form>
            </div>
        </main>
    );
}