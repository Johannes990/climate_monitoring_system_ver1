"use client";

import { useRouter } from "next/navigation";
import { LoginDTO } from "../../dto/userauth/loginDTO";
import { useState } from "react";
import { postRequest } from "@/app/utils/api";
import {
    DASHBOARD_URL_PATH,
    REGISTER_URL_PATH,
    LOGIN_QUERY_PATH
} from "@/app/utils/constants";

export default function Login() {
    const [loginFormData, setLoginFormData] = useState<LoginDTO>({
        email: "",
        password: "",
    });
    const router = useRouter();
    const [errorMessage, setErrorMessage] = useState("");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setLoginFormData({
            ...loginFormData,
            [name]: value,
        });
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log("Login form submitted");

        try {
            const response = await postRequest(LOGIN_QUERY_PATH, loginFormData);

            if (response.ok) {
                const responseBody = await response.text();
                console.log("Login successful:", responseBody);
                router.push(DASHBOARD_URL_PATH);
            } else {
                const errorBody = await response.text();
                setErrorMessage(errorBody);
            }
        } catch (error) {
            setErrorMessage("An error occurred during login. Please check your credentials.");
            console.error("Login error:", error);
        }
        //setErrorMessage("");
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Login</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                            Email address
                        </label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            required
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            placeholder="Enter your email"
                            value={loginFormData.email}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                            Password
                        </label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            required
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            placeholder="Enter your password"
                            value={loginFormData.password}
                            onChange={handleChange}
                        />
                    </div>

                    {errorMessage && (<p className="text-red-500 text-sm">{errorMessage}</p>)}

                    <div>
                        <button
                            type="submit"
                            className="w-full p-2 text-white bg-blue-600 rounded hover:bg-blue-700"
                        >
                            Login
                        </button>
                    </div>
                </form>

                <div className="text-center">
                    <button
                        onClick={() => router.push(REGISTER_URL_PATH)}
                        className="mt-4 text-blue-600 hover:underline"
                    >
                        Don't have an account? Register here
                    </button>
                </div>
            </div>
        </main>
    );
}
