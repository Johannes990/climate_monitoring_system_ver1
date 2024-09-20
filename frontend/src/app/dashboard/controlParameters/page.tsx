"use client";

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { getRequest, postRequest, deleteRequest } from "@/app/utils/api";
import { ControlParameterSetDTO } from "../../dto/climatedata/ControlParameterSetDTO";
import {
    CONTROL_PARAMS_ALL_QUERY_PATH,
    CONTROL_PARAMS_ADD_QUERY_PATH,
    CONTROL_PARAMS_DELETE_PATH,
} from "@/app/utils/constants";

const initialControlParameterData: ControlParameterSetDTO = {
    controlParameterSetId: undefined,
    tempNorm: 0.0,
    tempTolerance: 0.0,
    relHumidityNorm: 0.0,
    relHumidityTolerance: 0.0,
};

export default function ControlParameters() {
    const [controlParameterDeleteId, setControlParameterDeleteId] = useState<number>();
    const [controlParameterData, setControlParameterData] = useState<ControlParameterSetDTO[]>([]);
    const [newControlParameterSet, setNewControlParameterSet] =
        useState<ControlParameterSetDTO>(initialControlParameterData);
    const router = useRouter();

    console.log(JSON.stringify(newControlParameterSet));

    const fetchControlParameterData = async () => {
        const response = await getRequest(CONTROL_PARAMS_ALL_QUERY_PATH);
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

    const handleFormSubmit = async (e: React.FormEvent) => {
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
            await postRequest(CONTROL_PARAMS_ADD_QUERY_PATH, parsedParameterSetData);
            setNewControlParameterSet(initialControlParameterData);
            await fetchControlParameterData();
            router.refresh();
        } catch (error) {
            console.error("Error submitting form:", error);
        }
    }

    const handleDeleteSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const confirmed = window.confirm(`Are you sure you want to delete the " +
        "control parameter set with ID: ${controlParameterDeleteId}?`);
        if (!confirmed) {
            return;
        }

        console.log("Deletion of id:" + controlParameterDeleteId + " submitted");

        try {
            await deleteRequest(CONTROL_PARAMS_DELETE_PATH + controlParameterDeleteId);
            setControlParameterDeleteId(0);
            await fetchControlParameterData();
            router.refresh();
        } catch (error) {
            console.error(
                "Error deleting control parameter sed id " + controlParameterDeleteId + " :",
                error
            );
        }
    }

    const handleDeleteIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const val = e.target.value;
        setControlParameterDeleteId(parseInt(val));
    }

    const handleFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
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
                <form onSubmit={handleFormSubmit} className="space-y-4">
                    <h2 className="text-xl font-semibold">Create A New Control Parameter Set</h2>

                    <div className="space-y-2">
                        <label htmlFor="tempNorm" className="block text-sm font-medium">
                            Temp Norm
                        </label>
                        <input
                            type="number"
                            id="tempNorm"
                            name="tempNorm"
                            value={newControlParameterSet.tempNorm}
                            onChange={handleFormChange}
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
                            onChange={handleFormChange}
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
                            onChange={handleFormChange}
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
                            onChange={handleFormChange}
                            className="w-full px-3 py-2 border rounded"
                            required
                        />
                    </div>
                    <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-blue-500 rounded">
                        Save
                    </button>
                </form>

                <form onSubmit={handleDeleteSubmit} className="space-y-4">
                    <h2 className="text-xl font-semibold">Delete An Existing Control Parameter Set</h2>

                    <div className="space-y-2">
                        <label htmlFor="controlParameterDeleteId" className="block text-sm font-medium">
                            Delete ID
                        </label>
                        <input
                            type="number"
                            id="controlParameterDeleteId"
                            name="controlParameterDeleteId"
                            value={controlParameterDeleteId}
                            onChange={handleDeleteIdChange}
                            className="w-full px-3 py-2 border rounded"
                            required
                        />
                    </div>
                    <button type="submit" className="w-full px-4 py-2 font-bold text-white bg-red-500 rounded">
                        Delete
                    </button>
                </form>
            </div>
        </main>
    );
}