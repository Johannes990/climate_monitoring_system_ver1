"use client";

import { useState } from "react";
import { RegisterDTO } from "../../dto/userauth/RegisterDTO";
import { postRequest } from "@/app/utils/api";
import {router} from "next/client";
import {LOGIN_URL_PATH} from "@/app/utils/constants";

export default function Register() {
    const [registerFormData, setRegisterFormData] = useState<RegisterDTO>({
        firstName: "",
        lastName: "",
        userName: "",
        email: "",
        password: "",
    });
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setRegisterFormData({
            ...registerFormData,
            [name]: value,
        });
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (registerFormData.password !== confirmPassword) {
            setErrorMessage("Passwords must match!");
            return;
        }

        console.log("Sending registration data to the backend: ", registerFormData);

        try {
            const response = await postRequest("/register", registerFormData);

            if (response.ok) {
                const responseBody = await response.text();
                console.log("Registration successful:", responseBody);
                router.push(LOGIN_URL_PATH)
            } else {
                const errorBody = await response.text();
                setErrorMessage(errorBody);
            }
        } catch (error) {
            setErrorMessage("An error occurred during registration.");
            console.error("Registration error:", error);
        }
        setErrorMessage("");
    };

    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 bg-white rounded shadow-md">
                <h1 className="text-3xl font-bold text-center">Register</h1>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="firstName" className="block text-sm font-medium text-gray-700">
                            First Name
                        </label>
                        <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            required
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            placeholder="Enter your first name"
                            value={registerFormData.firstName}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="lastName" className="block text-sm font-medium text-gray-700">
                            Last Name
                        </label>
                        <input
                            type="text"
                            id="lastName"
                            name="lastName"
                            required
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            placeholder="Enter your last name"
                            value={registerFormData.lastName}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="userName" className="block text-sm font-medium text-gray-700">
                            User name
                        </label>
                        <input
                            type="text"
                            id="userName"
                            name="userName"
                            required
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            placeholder="Enter your user name"
                            value={registerFormData.userName}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                            Email
                        </label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            required
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            placeholder="Enter your email address"
                            value={registerFormData.email}
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
                            value={registerFormData.password}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700">
                            Confirm password
                        </label>
                        <input
                            type="password"
                            id="confirmPassword"
                            name="confirmPassword"
                            required
                            className="w-full p-2 border border-gray-300 rounded mt-1"
                            placeholder="Re-enter your password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                    </div>

                    {errorMessage && (<p className="text-red-500 text-sm">{errorMessage}</p>)}

                    <div>
                        <button
                            type="submit"
                            className="w-full p-2 text-white bg-blue-600 rounded hover:bg-blue-700"
                        >
                            Register
                        </button>
                    </div>
                </form>
            </div>
        </main>
    );
}