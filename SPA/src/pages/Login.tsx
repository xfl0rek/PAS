import LoginForm from '@/components/LoginForm';
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Link } from "react-router";

const Login = () => {
    return (
        <div className="flex items-center justify-center w-screen min-h-screen bg-gray-100">
            <Card className="max-w-md w-full shadow-lg">
                <CardHeader>
                    <CardTitle className="text-center p-10">Log in to your account!</CardTitle>
                    <CardDescription className="text-center">Enter your username and password to log in</CardDescription>
                </CardHeader>
                <CardContent>
                    <LoginForm />
                </CardContent>
                <CardFooter className="justify-center">
                    <Link to="/register">Don't have an account <span className="text-blue-700">Register</span></Link>
                </CardFooter>
            </Card>
        </div>
    );
};

export default Login;