import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router";


const Unauthorized = () => {

    const navigate = useNavigate();

    const handleClick = () => {
        navigate("/");
    }


    return (
        <div className="flex flex-col items-center justify-center h-screen w-screen">
        <h1>Unauthorized</h1>
        <p>You are not authorized to view this page.</p>
        <Button onClick={handleClick} className="mt-4">
          Return to homepage
          </Button>
        </div>
    );
    }

    export default Unauthorized;