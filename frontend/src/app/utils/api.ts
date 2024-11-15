export const API_URL = process.env.NEXT_PUBLIC_API_URL;


export async function postRequest<T>(url: string, data: T): Promise<Response> {
    try {
        console.log(`${API_URL}${url}`)

        const response = await fetch(`${API_URL}${url}`, {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error("Network response was not OK.");
        }

        return response;
    } catch (error) {
        console.error("API post request error:", error);
        throw error;
    }
}

export async function postRequestWithParam<T>(url: string, param: number, data: T): Promise<Response> {
    try {
        const fullUrl = `${API_URL}${url}${param}`;
        console.log(fullUrl);

        const response = await fetch(fullUrl, {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error("Network response was not OK.");
        }

        return response;
    } catch (error) {
        console.error("API post request error:", error);
        throw error;
    }
}

export async function getRequest(url: string): Promise<Response> {
    try {
        console.log(`${API_URL}${url}`)

        return await fetch(`${API_URL}${url}`, {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
        });
    } catch (error) {
        console.error("API get request error:", error);
        throw error;
    }
}

export async function deleteRequest(url: string): Promise<Response> {
    try {
        console.log(`${API_URL}${url}`)

        return await fetch(`${API_URL}${url}`, {
            method: "DELETE",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
        });
    } catch (error) {
        console.error("API delete request error:", error);
        throw error;
    }
}