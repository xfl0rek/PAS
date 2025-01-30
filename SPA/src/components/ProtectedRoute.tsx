import { Navigate, Outlet } from "react-router";
import { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import { DecodedToken, UserRole } from "@/types";

const ProtectedRoute = ({ requiredRoles }: { requiredRoles?: UserRole[] }) => {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);
    const [userRole, setUserRole] = useState<UserRole | null>(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            try {
                const decoded = jwtDecode<DecodedToken>(token);

                const role: UserRole | null = decoded?.role || null;
                console.log("User Role:", role);
                console.log("Required Roles:", requiredRoles);

                setUserRole(role);
                setIsAuthenticated(true);
            } catch (error) {
                console.error("Invalid token:", error);
                setIsAuthenticated(false);
            }
        } else {
            setIsAuthenticated(false);
        }
    }, []);

    if (isAuthenticated === null) {
        return <div>Loading...</div>; // Optional loading spinner
    }

    // Check if user is authenticated and has the required role
    if (!isAuthenticated || (requiredRoles && !requiredRoles.includes(userRole!))) {
        return <Navigate to="/login" replace />;
    }

    return <Outlet />;
};

export default ProtectedRoute;
