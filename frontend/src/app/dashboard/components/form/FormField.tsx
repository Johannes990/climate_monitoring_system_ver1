import React from "react";

interface FormFieldProps {
    id: string;
    label: string;
    value: string | number;
    type?: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    required? :boolean;
}

const FormField: React.FC<FormFieldProps> = ({ id, label, value, type = "text", onChange, required = true}) => (
    <div className="space-y-2">
        <label htmlFor={id} className="block text-sm font-medium capitalize">
            {label}
        </label>
        <input
            type={type}
            id={id}
            name={id}
            value={value}
            onChange={onChange}
            className="w-full px-3 py-2 border rounded"
            required={required}
        />
    </div>
);

export default FormField;