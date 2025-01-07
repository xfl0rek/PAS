import LoginForm from '@/components/LoginForm'
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import {Link} from "react-router";

const Login = () => {
    return (
        <div className="w-screen">
            <Card>
                <CardHeader>
                    <CardTitle className="text-center">Log in to your account!</CardTitle>
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