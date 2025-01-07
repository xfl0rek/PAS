import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import { useEffect, useState } from "react";
import { Input } from "@/components/ui/input"
// import { Label } from "@/components/ui/label"


const UserTable = ({
    users,
} : {
    users: User[];
}) => {
    const [filteredUsers, setFilteredUsers] = useState<User[]>([]);
    const [search, setSearch] = useState<string>("");

    useEffect(() => {
        let filtered = users;

        if (search) {
            filtered = filtered.filter((user) => 
            user.username.toLowerCase().includes(search.toLowerCase())
            );
        }

        setFilteredUsers(filtered);
    }, [search, users] );

    return (
        <div className="flex flex-col  items-center ">
            <div>
                {/* <Label>
                    Wprowadz nazwe uzytkownika
                </Label> */}
                <Input type="text" placeholder="Nazwa uzytkownika" value={search} onChange={(e) => setSearch(e.target.value)} />
            </div>
            <Table>
                <TableCaption>Lista klientow</TableCaption>
                <TableHeader>
                    <TableRow>
                        <TableHead>Nazwa uzytkownika</TableHead>
                        <TableHead>Imie</TableHead>
                        <TableHead>Nazwisko</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {filteredUsers.length > 0 ? (
                        filteredUsers.map((user) => (
                            <TableRow key={user.username}>
                                <TableCell>{user.username}</TableCell>
                                <TableCell>{user.firstName}</TableCell>
                                <TableCell>{user.lastName}</TableCell>
                            </TableRow>
                            )
                        )
                    ) : (
                        <TableRow>
                            <TableCell colSpan={3}>Brak uzytkownikow</TableCell>
                        </TableRow>
                    )}
                </TableBody>
            </Table>
        </div>
    );
};

export default UserTable;