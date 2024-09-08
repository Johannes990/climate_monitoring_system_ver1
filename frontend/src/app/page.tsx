'use client'

export default function Home() {
    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        // You can handle the form submission here
        console.log("Form submitted!");
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
                        />
                    </div>
                    <div>
                        <button
                            type="submit"
                            className="w-full p-2 text-white bg-blue-600 rounded hover:bg-blue-700"
                        >
                            Login
                        </button>
                    </div>
                </form>
            </div>
        </main>
    );
}