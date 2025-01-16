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
import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router";

const UserTable = ({ users }: { users: User[] }) => {
  const [filteredUsers, setFilteredUsers] = useState<User[]>([]);
  const [search, setSearch] = useState<string>("");
  const navigate = useNavigate();

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
    const url = user.active
      ? `/api/users/deactivateAccount/${user.id}`
      : `/api/users/activateAccount/${user.id}`;
    const response = await fetch(url, {
      method: "POST",
    });
    if (response.ok) {
      setFilteredUsers((prevUsers) =>
        prevUsers.map((u) =>
          u.id === user.id ? { ...u, active: !u.active } : u
        )
      );
    }
  };

  const updateHandler = (user: User) => {
    navigate(`/users/update/${user.id}`);
  };

  const detailsHandler = (user: User) => {
    navigate(`/users/${user.id}`);
  };

  return (
    <div className="flex flex-col items-center overflow-auto w-full ">
      <div className="w-3/4 my-4">
        <Input
          type="text"
          placeholder="Username"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-1/4"
        />
      </div>
      <Table className="w-3/4 overflow-auto justify-self-center">
        <TableCaption>Client list</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead className="py=10">Username</TableHead>
            <TableHead className="py=10">Firstname</TableHead>
            <TableHead>Lastname</TableHead>
            <TableHead>Active</TableHead>
            <TableHead></TableHead>
            <TableHead></TableHead>
            <TableHead></TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {filteredUsers.length > 0 ? (
            filteredUsers.map((user) => (
              <TableRow key={user.username}>
                <TableCell>{user.username}</TableCell>
                <TableCell>{user.firstName}</TableCell>
                <TableCell>{user.lastName}</TableCell>
                <TableCell>{`${user.active}`}</TableCell>
                <TableCell>
                  <Button onClick={() => activeHandler(user)}>
                    {user.active ? "Deactivate" : "Activate"}
                  </Button>
                </TableCell>
                <TableCell>
                  <Button onClick={() => updateHandler(user)}>
                    Update data
                  </Button>
                </TableCell>
                <TableCell>
                  <Button onClick={() => detailsHandler(user)}>
                    User details
                  </Button>
                </TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={7}>No users</TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
};

export default UserTable;
