import { useState } from "react";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink } from "@/components/ui/breadcrumb";
import Users from "./Users.tsx";

const Home = () => {
    const [activeView, setActiveView] = useState("home");

    const renderContent = () => {
        switch (activeView) {
            case "users":
                return <Users />;
            case "home":
            default:
                return <div>Welcome to Home Page!</div>;
        }
    };

    return (
        <div className="flex h-screen">
            <div className="w-40 p-6 bg-gray-800 text-white shadow-md">
                <Breadcrumb className="flex flex-col space-y-2">
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/" onClick={() => setActiveView("home")}>
                            Home
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="#" onClick={() => setActiveView("users")}>
                            User list
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="#" onClick={() => setActiveView("rooms")}>
                            Rent room
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                </Breadcrumb>
            </div>
            <div className="flex-1 p-6 bg-white">
                <div>
                    {renderContent()}
                </div>
            </div>
        </div>
    );
};

export default Home;
