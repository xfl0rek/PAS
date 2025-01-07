import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useEffect, useState } from "react";
import { Input } from "@/components/ui/input";
// import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button";

const UserTable = ({ users }: { users: User[] }) => {
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
  }, [search, users]);

  const activeHandler = async (user: User) => {
    const url = user.active ? `/api/users/deactivateAccount/${user.id}` : `/api/users/activateAccount/${user.id}`;
    const respone = await fetch(url, {
        method: 'POST',
    });
    if (respone.ok) {
        setFilteredUsers((prevUsers) =>
            prevUsers.map((u) =>
              u.id === user.id ? { ...u, active: !u.active } : u
            )
          );
    }
  };

  return (
    <div className="flex flex-col items-center  min-h-screen">
      <div className="w-3/4 mb-4 mt-4">
        {/* <Label>
                    Wprowadz nazwe uzytkownika
                </Label> */}
        <Input
          type="text"
          placeholder="Nazwa uzytkownika"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-1/4"
        />
      </div>
      {/* <div className="w-full flex items-center"> */}
      <Table className="table-fixed w-3/4 justify-self-center">
        <TableCaption>Lista klientow</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead className="px-10 py=10">Nazwa uzytkownika</TableHead>
            <TableHead className="px-20 py=10">Imie</TableHead>
            <TableHead className="px-20 py=10">Nazwisko</TableHead>
            <TableHead className="px-20 py=10">Aktywny</TableHead>
            <TableHead className="px-20 py=10"></TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {filteredUsers.length > 0 ? (
            filteredUsers.map((user) => (
              <TableRow key={user.username}>
                <TableCell className="px-20 py=10">{user.username}</TableCell>
                <TableCell className="px-20 py=10">{user.firstName}</TableCell>
                <TableCell className="px-20 py=10">{user.lastName}</TableCell>
                <TableCell className="px-20 py=10">{`${user.active}`}</TableCell>
                <TableCell className="px-20 py=10">
                  <Button onClick={() => activeHandler(user)}>
                    {user.active ? "Dezaktywuj" : "Aktywuj"}
                  </Button>
                </TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={5}>Brak uzytkownikow</TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
      {/* </div> */}
    </div>
  );
};

export default UserTable;
