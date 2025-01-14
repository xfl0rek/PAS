import UserTable from "@/components/UserTable";
import { useEffect, useState } from "react";
import Sidebar from "@/components/Sidebar";

const Users = () => {
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    const fetchUsers = async () => {
      const response = await fetch("/api/users/");
      // if (!response.ok) {
      //
      // }
      const data = await response.json();
      setUsers(data);
    };
    fetchUsers();
  }, []);

  return (
    <div className="h-screen w-screen flex">
      <div className="w-40 h-full">
        <Sidebar />
      </div>
      <div className="flex-grow overflow-auto">
        <p className="text-center">
          {localStorage.getItem("username") === null
            ? "Not logged in"
            : "Logged in as " + localStorage.getItem("username")}
        </p>
        <UserTable users={users} />
      </div>
    </div>
  );
};

export default Users;
