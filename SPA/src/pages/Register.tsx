import RegisterForm from "@/components/RegisterForm"
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import {Link} from "react-router";

const Register = () => {
    return (
        <div className="flex items-center justify-center w-screen min-h-screen bg-gray-100">
            <Card className="max-w-md w-full shadow-lg">
                <CardHeader>
                    <CardTitle className="text-center">Create a new account</CardTitle>
                    <CardDescription className="text-center">Please provide your details to create a user account</CardDescription>
                </CardHeader>
                <CardContent>
                    <RegisterForm/>
                </CardContent>
                <CardFooter className="justify-center">
                    <Link to="/login">Already have an account? <span className="text-blue-700">Login</span></Link>
                </CardFooter>
            </Card>
        </div>
    );
};

export default Register;