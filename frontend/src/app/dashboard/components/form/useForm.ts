import {useState} from "react";

type UseFormReturnType<T> = {
    formData: T;
    handleInputChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleSubmit: (onSubmit: (parsedData: T) => Promise<void>) => (e: React.FormEvent) => Promise<void>;
    loading: boolean;
    error: string;
}

export function useForm<T>(initialState: T): UseFormReturnType<T> {
    const [formData, setFormData] = useState<T>(initialState);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string>("");

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = (onSubmit: (parsedData: T) => Promise<void>) => async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            await onSubmit(formData);
            setFormData(initialState);
        } catch (err) {
            setError("Failed to save.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return { formData, handleInputChange, handleSubmit, loading, error };
}