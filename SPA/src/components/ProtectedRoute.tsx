// //mport jwt_decode from 'jwt-decode';
// import {useEffect, useState} from "react";
// import api from "@/lib/api.ts";
//
// const ProtectedRoute = ({allowedRoles} : {allowedRoles: UserRole[]}) => {
//     const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
//     const [role, setRole] = useState<UserRole | null>(null);
//
//     useEffect(() => {
//         const checkToken = async () => {
//             try {
//                 const token = await api.get("/auth/check");
//
//                 setRole(decodedToken.userRole);
//                 setIsAuthenticated(true);
//             } catch (err) {
//                 setIsAuthenticated(false);
//             }
//         }
//
//     }, []);
//
//
//
//
// }