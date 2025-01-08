import { useEffect, useState } from "react";
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";

const RentList = () => {
    const [rents, setRents] = useState<Rent[]>([]);
    const [endTime, setEndTime] = useState<string>("");
    const [rentId, setRentId] = useState<number | null>(null);
    const [username, setUsername] = useState<string | null>(null);

    useEffect(() => {
        const storedUsername = localStorage.getItem("username");
        if (storedUsername) {
            setUsername(storedUsername);
        }

        const fetchRents = async () => {
            try {
                const response = await fetch('/api/rents/');
                if (!response.ok) {
                    console.error("Failed to fetch rents");
                    return;
                }
                const data = await response.json();
                setRents(data);
            } catch (err) {
                console.log(err);
            }
        };

        fetchRents();
    }, []);

    const handleReturnRoom = async () => {
        try {
            const response = await fetch(`/api/rents/returnRoom/${rentId}?endTime=${encodeURIComponent(endTime)}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                console.error("Failed to fetch rents");
            }

            alert("Room returned successfully!");
            setEndTime("");
            setRentId(null);
            setRents((prevRents) => prevRents.filter((rent) => rent.id !== rentId));
        } catch (err) {
            console.log(err);
            alert("Failed to return the room");
        }
    };

    return (
        <div className="p-6 bg-white shadow-lg rounded-lg">
            <h2 className="text-2xl font-semibold text-gray-800 mb-4">Rent List</h2>

            <Table className="table-fixed w-full justify-self-center">
                <TableHeader>
                    <TableRow>
                        <TableHead className="px-6 py-4">ID</TableHead>
                        <TableHead className="px-6 py-4">Username</TableHead>
                        <TableHead className="px-6 py-4">Room Number</TableHead>
                        <TableHead className="px-6 py-4">Begin Time</TableHead>
                        <TableHead className="px-6 py-4">Actions</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {rents.length > 0 ? (
                        rents
                            .filter((rent) => !rent.endTime)
                            .map((rent) => (
                                <TableRow key={rent.id}>
                                    <TableCell className="px-6 py-4">{rent.id}</TableCell>
                                    <TableCell className="px-6 py-4">{rent.clientUsername}</TableCell>
                                    <TableCell className="px-6 py-4">{rent.roomNumber}</TableCell>
                                    <TableCell className="px-6 py-4">
                                        {new Date(rent.beginTime).toLocaleString()}
                                    </TableCell>
                                    <TableCell className="px-6 py-4">
                                        {username && rent.clientUsername === username ? (
                                            <Button
                                                onClick={() => {
                                                    setRentId(rent.id);
                                                }}
                                            >
                                                Select for Return
                                            </Button>
                                        ) : (
                                            <span className="text-gray-500">Not your rent</span>
                                        )}
                                    </TableCell>
                                </TableRow>
                            ))
                    ) : (
                        <TableRow>
                            <TableCell colSpan={5} className="text-center">No rents available</TableCell>
                        </TableRow>
                    )}
                </TableBody>
            </Table>

            {rentId && (
                <div className="mt-4">
                    <h3 className="text-xl font-semibold">Return Room</h3>
                    <div className="mt-2">
                        <label className="block text-gray-600">End Time</label>
                        <input
                            type="datetime-local"
                            value={endTime}
                            onChange={(e) => setEndTime(e.target.value)}
                            className="mt-1 p-2 border border-gray-300 rounded-md"
                        />
                    </div>
                    <div className="mt-4 w-1/3">
                        <Button onClick={handleReturnRoom} className="w-full">
                            Return Room
                        </Button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default RentList;
