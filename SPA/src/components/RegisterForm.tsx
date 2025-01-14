import { FormEvent, useState } from "react";
import {Button} from "@/components/ui/button"
import {Label} from "@/components/ui/label"
import {Input} from "@/components/ui/input"
import { useNavigate } from "react-router";

const RegisterForm = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState({ firstName: "", lastName: "", username: "", email: "", password: "", general: "" });
    const navigate = useNavigate();

    const validateInputs = () => {
        let isValid = true;
        const newErrors = { firstName: "", lastName: "", username: "", email: "", password: "", general: "" };

        if (!firstName || firstName.length < 3 || firstName.length > 30) {
            newErrors.firstName = "First name must be between 3 and 30 characters.";
            isValid = false;
        }

        if (!lastName || lastName.length < 3 || lastName.length > 30) {
            newErrors.lastName = "Last name must be between 3 and 30 characters.";
            isValid = false;
        }

        if (!username || username.length < 5 || username.length > 30) {
            newErrors.username = "Username must be between 5 and 30 characters.";
            isValid = false;
        }

        if (!email || email.length < 10 || email.length > 50) {
            newErrors.email = "Email must be between 10 and 50 characters.";
            isValid = false;
        }

        if (!password || password.length < 8 || password.length > 30) {
            newErrors.password = "Password must be between 8 and 30 characters.";
            isValid = false;
        }

        setErrors(newErrors);
        return isValid;
    }

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        if (!validateInputs()) return;

        const requestBody = {
            firstName,
            lastName,
            username,
            email,
            password,
            userRole: 'CLIENT',
        };

        try {
            const response = await fetch('/api/users/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestBody),
            });

            if (!response.ok) {
                const error = await response.json().catch(() => null);
                setErrors((prev) => ({
                    ...prev,
                    general: error?.message || "Username already exists.",
                }));
            } else {
                navigate('/login');
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
            <h2 className="text-3xl font-semibold text-center text-gray-800 mb-6">Register form</h2>
            <form onSubmit={handleSubmit} className="space-y-6">
                <div className="space-y-2">
                    <Label htmlFor="firstName">First name</Label>
                    <Input
                        id="firstName"
                        type="text"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                    {errors.firstName && (
                        <p className="text-red-600 text-sm">{errors.firstName}</p>
                    )}
                </div>
                <div className="space-y-2">
                    <Label htmlFor="lastName">Last name</Label>
                    <Input
                        id="lastName"
                        type="text"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                    {errors.lastName && (
                        <p className="text-red-600 text-sm">{errors.lastName}</p>
                    )}
                </div>
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
                    <Label htmlFor="email">E-mail</Label>
                    <Input
                        id="email"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    {errors.email && (
                        <p className="text-red-600 text-sm">{errors.email}</p>
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
                        Register
                    </Button>
                </div>
            </form>
        </div>
    );
};

export default RegisterForm;
