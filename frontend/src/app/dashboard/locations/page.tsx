"use client";

import React, { useEffect, useState, useCallback } from "react";
import { LocationDTO } from "@/app/dto/climatedata/LocationDTO";
import {deleteLocation, fetchAllLocations} from "@/app/dashboard/locations/LocationService";
import LocationForm from "@/app/dashboard/locations/LocationForm";

export default function Locations() {
    const [locations, setLocations] = useState<LocationDTO[]>([]);
    const [locationDeleteId, setLocationDeleteId] = useState<number | undefined>(undefined);
    const [error, setError] = useState<string | null>("");

    const fetchData = useCallback(async () => {
        try {
            const data = await fetchAllLocations();
            setLocations(data)
        } catch (err) {
            setError("Failed to fetch all locations.");
            console.error(err);
        }
    }, []);

    useEffect(() => {
        window.history.replaceState(null, "", window.location.href);
        fetchData();
    }, [fetchData]);

    const handleDeleteSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!locationDeleteId) return;

        const confirmed = window.confirm(
            `Are you sure you want to delete the location with id: ${locationDeleteId}?`
        );

        if (!confirmed) return;

        try {
            await deleteLocation(locationDeleteId);
            setLocationDeleteId(undefined);
            fetchData();
        } catch (err) {
            console.error(`Error deleting Location with id: ${locationDeleteId}`);
            setError("Failed to delete location.");
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-4xl p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Locations</h1>

                {error && <p className="text-red-500">{error}</p>}

                <div className="overflow-x-auto w-full">
                    <table className="min-w-full bg-white border">
                        <thead>
                            <tr>
                                <th className="px-4 py-2 border">ID</th>
                                <th className="px-4 py-2 border">Location Description</th>
                                <th className="px-4 py-2 border">Control Parameter Set ID</th>
                                <th className="px-4 py-2 border">Temp Norm</th>
                                <th className="px-4 py-2 border">Temp Tolerance</th>
                                <th className="px-4 py-2 border">Relative Humidity Norm</th>
                                <th className="px-4 py-2 border">Relative Humidity Tolerance</th>
                            </tr>
                        </thead>
                        <tbody>
                            {locations.map((location) => (
                                <tr key={location.locationId}>
                                    <th className="px-4 py-2 border">{location.locationId}</th>
                                    <th className="px-4 py-2 border">{location.locationDescription}</th>
                                    <th className="px-4 py-2 border">{location.controlParameterSet.controlParameterSetId}</th>
                                    <th className="px-4 py-2 border">{location.controlParameterSet.tempNorm}</th>
                                    <th className="px-4 py-2 border">{location.controlParameterSet.tempTolerance}</th>
                                    <th className="px-4 py-2 border">{location.controlParameterSet.relHumidityNorm}</th>
                                    <th className="px-4 py-2 border">{location.controlParameterSet.relHumidityTolerance}</th>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>


                <LocationForm onSuccess={fetchData} />

                <form onSubmit={handleDeleteSubmit} className="space-y-4">
                    <h2 className="text-xl font-semibold">Delete An Existing Location</h2>

                    <div className="space-y-2">
                        <label htmlFor="locationDeleteId" className="block text-sm font-medium">
                            Delete ID
                        </label>
                        <input
                            type="number"
                            id="locationDeleteId"
                            name="locationDeleteId"
                            value={locationDeleteId}
                            onChange={(e) => setLocationDeleteId(Number(e.target.value))}
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