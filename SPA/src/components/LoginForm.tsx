import { FormEvent, useState } from "react";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router";

const LoginForm = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState({ username: "", password: "", general: "" });
    const navigate = useNavigate();

    const validateInputs = () => {
        let isValid = true;
        const newErrors = { username: "", password: "", general: "" };

        if (!username || username.length < 5 || username.length > 30) {
            newErrors.username = "Username must be between 5 and 30 characters.";
            isValid = false;
        }

        if (!password || password.length < 8 || password.length > 30) {
            newErrors.password = "Password must be between 8 and 30 characters.";
            isValid = false;
        }

        setErrors(newErrors);
        return isValid;
    };

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault();
        if (!validateInputs()) return;

        try {
            const response = await fetch('/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });

            if (!response.ok) {
                const error = await response.json().catch(() => null);
                setErrors((prev) => ({
                    ...prev,
                    general: error?.message || "Invalid username or password.",
                }));
            } else {
                localStorage.setItem('username', username);
                navigate('/');
            }
        } catch (err) {
            setErrors((prev) => ({
                ...prev,
                general: "An error occurred. Please try again later.",
            }));
        }
    };

    return (
        <div className="p-6 bg-white shadow-lg rounded-lg max-w-xl mx-auto overflow-hidden">
            <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">Login Form</h2>
            <form onSubmit={handleSubmit} className="space-y-6">
                <div className="space-y-2">
                    <Label htmlFor="username">Username</Label>
                    <Input
                        id="username"
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                    {errors.username && (
                        <p className="text-red-600 text-sm">{errors.username}</p>
                    )}
                </div>
                <div className="space-y-2">
                    <Label htmlFor="password">Password</Label>
                    <Input
                        id="password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    {errors.password && (
                        <p className="text-red-600 text-sm">{errors.password}</p>
                    )}
                </div>
                {errors.general && (
                    <p className="text-red-600 text-center text-sm">{errors.general}</p>
                )}
                <div className="flex justify-center">
                    <Button type="submit" className="w-1/2">
                        Login
                    </Button>
                </div>
            </form>
        </div>
    );
};

export default LoginForm;
