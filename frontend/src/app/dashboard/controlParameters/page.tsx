"use client";

import { useRouter } from "next/navigation";
import { useEffect, useState, useCallback } from "react";
import { getRequest, postRequest, deleteRequest } from "@/app/utils/api";
import { ControlParameterSetDTO } from "../../dto/climatedata/ControlParameterSetDTO";
import {
    CONTROL_PARAMS_ALL_QUERY_PATH,
    CONTROL_PARAMS_ADD_QUERY_PATH,
    CONTROL_PARAMS_DELETE_PATH,
    initialControlParameterData
} from "@/app/utils/constants";
import {
    deleteControlParameterSet,
    fetchControlParameterData
} from "@/app/dashboard/controlParameters/ControlParameterSetService";
import ControlParameterSetForm from "@/app/dashboard/controlParameters/ControlParameterSetForm";


export default function ControlParameters() {
    const [controlParameterDeleteId, setControlParameterDeleteId] = useState<number>();
    const [controlParameterData, setControlParameterData] = useState<ControlParameterSetDTO[]>([]);
    const [error, setError] = useState<string | null>(null);

    const fetchData = useCallback(async () => {
        try {
            const data = await fetchControlParameterData();
            setControlParameterData(data);
        } catch (err) {
            setError("Failed to fetch control parameter data.");
            console.error(err);
        }
    }, []);

    useEffect(() => {
        fetchData();
    }, [fetchData]);

    const handleDeleteSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!controlParameterDeleteId) return;

        const confirmed = window.confirm(
            `Are you sure you want to delete control parameter set with id: ${controlParameterDeleteId}?`
        );

        if (!confirmed) return;

        try {
            await deleteControlParameterSet(controlParameterDeleteId);
            setControlParameterDeleteId(undefined);
            fetchData();
        } catch (err) {
            console.error(`Error deleting control parameter set with id: ${controlParameterDeleteId}:`, err);
            setError("Failed to delete control parameter set.");
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-lg p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Control Parameters</h1>

                {error && <p className="text-red-500">{error}</p>}

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
                <ControlParameterSetForm onSuccess={fetchData} />

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
                            onChange={(e) => setControlParameterDeleteId(Number(e.target.value))}
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