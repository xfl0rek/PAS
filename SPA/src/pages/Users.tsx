import UserTable from "@/components/UserTable"
import {useEffect, useState} from "react";
import {Breadcrumb, BreadcrumbItem, BreadcrumbLink} from "@/components/ui/breadcrumb.tsx";

const Users = () => {
    const [users, setUsers] = useState<User[]>([]);

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

    return (
        <div className="flex h-screen">
            <div className="w-60 p-4 bg-gray-800 text-white shadow-lg">
                <Breadcrumb className="flex flex-col space-y-2">
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/">
                            Home
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/users">
                            User list
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/rooms">
                            Rent room
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/rents">
                            Rent list
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                </Breadcrumb>
            </div>

            <div>
                <UserTable users={users}/>
            </div>
        </div>
    );
};

export default Users;