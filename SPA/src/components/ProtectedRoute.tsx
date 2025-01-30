import { Navigate, Outlet } from "react-router";
import { useEffect, useState } from "react";
import { UserRole } from "@/types";

const ProtectedRoute = ({ requiredRoles }: { requiredRoles?: UserRole[] }) => {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);
    const [userRole, setUserRole] = useState<UserRole | null>(null);

    useEffect(() => {
        const checkAuth = async () => {
            const token = localStorage.getItem("token");
            if (token) {
                try {
                    const response = await fetch("/api/users/checkAuth", {
                        method: "GET",
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    });

                    if (!response.ok) {
                        throw new Error("Unauthorized");
                    }

                    const data = await response.json();
                    const role: UserRole = data.role; // Zakładamy, że backend zwraca rolę
                    setUserRole(role);
                    setIsAuthenticated(true);
                } catch (error) {
                    console.error("Authentication check failed:", error);
                    setIsAuthenticated(false);
                }
            } else {
                setIsAuthenticated(false);
            }
        };

        checkAuth();
    }, []);

    if (isAuthenticated === null) {
        return <div>Loading...</div>; // Opcjonalny spinner ładowania
    }

    // Sprawdź, czy użytkownik jest zalogowany i ma wymagane uprawnienia
    if (!isAuthenticated || (requiredRoles && !requiredRoles.includes(userRole!))) {
        return <Navigate to="/login" replace />;
    }

    return <Outlet />;
};

export default ProtectedRoute;
