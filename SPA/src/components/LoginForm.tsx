import { FormEvent, useState } from "react";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router";

const LoginForm = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault();
        try {
            const response = await fetch('/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username, password }),
            });
            if (!response.ok) {
                const error = await response.json();
                console.log(error);
            } else {
                alert('OK');
                navigate('/')
            }
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <div className="p-6 bg-white shadow-lg rounded-lg justify-self-center">
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
                        className="w-1/3"
                    />
                </div>
                <div className="space-y-2">
                    <Label htmlFor="password">Password</Label>
                    <Input
                        id="password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        className="w-1/3"
                    />
                </div>
                <div className="flex justify-center">
                    <Button type="submit" className="w-1/3">
                        Login
                    </Button>
                </div>
            </form>
        </div>
    );
};

export default LoginForm;
