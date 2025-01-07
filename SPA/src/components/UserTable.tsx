import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"

const UserTable = ({
    users,
} : {
    users: User[];
}) => {
    return (
        <div>
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
                    {users.length > 0 ? (
                        users.map((user) => (
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