import UserTable from "@/components/UserTable";
import { useEffect, useState } from "react";
import Sidebar from "@/components/Sidebar";
import api from "@/lib/api.ts";
import LoginInfo from "@/components/LoginInfo.tsx";

const Users = () => {
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    const fetchUsers = async () => {
        try {
            const response = await api.get("/users/");

            setUsers(response.data);
        } catch (err) {
            console.error(err);
        }

    };
    fetchUsers();
  }, []);

    return (
        <div className="h-screen w-screen flex">
            <div className="w-40 h-full">
                <Sidebar />
            </div>
            <div className="flex-grow overflow-auto">
                <LoginInfo/>
                <UserTable users={users} />
            </div>
        </div>
    );
};

export default Users;
