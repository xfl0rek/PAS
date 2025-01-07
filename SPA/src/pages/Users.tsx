import UserTable from "@/components/UserTable"
import {useEffect, useState} from "react";

const Users = () => {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        const fetchUsers = async () => {
            const response = await fetch("/api/users/");
            // if (!response.ok) {
            //
            // }
            const data = await response.json();
            setUsers(data)
        };
        fetchUsers();
        }, []
    );

    return <div>
        <UserTable users={users}/>
    </div>
};

export default Users;