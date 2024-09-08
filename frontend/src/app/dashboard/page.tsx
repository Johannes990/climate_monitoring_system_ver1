"use client";

import { API_URL } from "@/app/utils/api";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

export default function Dashboard() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const router = useRouter();

    useEffect(() => {
        const checkAuthentication = async () => {
            try {
                const response = await fetch(`${API_URL}/protected`, {
                    method: "GET",
                    credentials: "include", // Ensure cookies are included
                });

                console.log("Protected endpoint response:", response.status);

                if (response.ok) {
                    const message = await response.text();
                    if (message === "Access to protected resources granted!") {
                        setIsAuthenticated(true);
                    } else {
                        setErrorMessage("Access to protected resources denied!");
                        console.log("Access to protected resources denied:", response.status);
                        router.push("/login"); // Redirect to login if not authenticated
                    }
                } else {
                    setErrorMessage("An error occurred while checking authentication.");
                    console.log("An error occurred while checking authentication:", response.status);
                    router.push("/login"); // Redirect to login if there's an error
                }
            } catch (error) {
                setErrorMessage("An error occurred during authentication check.");
                console.error("Authentication check error:", error);
                router.push("/login"); // Redirect to login if there's an error
            }
        };

        checkAuthentication();
    }, [router]);

    if (errorMessage) {
        return (
            <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
                <div className="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-md">
                    <h1 className="text-3xl font-bold text-center">Error</h1>
                    <p className="text-red-500 text-center">{errorMessage}</p>
                </div>
            </main>
        );
    }

    if (!isAuthenticated) {
        return (
            <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
                <div className="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-md">
                    <h1 className="text-3xl font-bold text-center">Loading...</h1>
                </div>
            </main>
        );
    }

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Dashboard</h1>
                <p className="text-center">Welcome to your dashboard!</p>
                <button
                    onClick={async () => {
                        try {
                            const response = await fetch("/logout", {
                                method: "GET",
                                credentials: "include",
                            });

                            if (response.ok) {
                                router.push("/login");
                            } else {
                                setErrorMessage("Logout failed!");
                            }
                        } catch (error) {
                            setErrorMessage("An error occurred during logout.");
                            console.error("Logout error:", error);
                        }
                    }}
                    className="w-full p-2 text-white bg-red-600 rounded hover:bg-red-700"
                >
                    Logout
                </button>
            </div>
        </main>
    );
}
