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

const RoomList = () => {
  const [rooms, setRooms] = useState<Room[]>([]);
  const [beginTime, setBeginTime] = useState<string>("");
  const [selectedRoom, setSelectedRoom] = useState<number | null>(null);

  useEffect(() => {
    const fetchRooms = async () => {
      try {
        const response = await fetch("/api/rooms/");
        if (!response.ok) {
          throw new Error("Failed to fetch rooms");
        }
        const data = await response.json();
        setRooms(data);
      } catch (err) {
        console.log(err);
      }
    };

    fetchRooms();
  }, []);

  const handleRentRoom = async (roomNumber: number) => {
    const username = localStorage.getItem("username");
    if (!username) {
      alert("Please log in to rent a room");
      return;
    }

    if (!beginTime) {
      alert("Please select a start date");
      return;
    }

    const rentData = {
      clientUsername: username,
      roomNumber: roomNumber,
      beginTime: beginTime,
    };

    try {
      const response = await fetch("/api/rents/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(rentData),
      });

      setSelectedRoom(null);

      if (!response.ok) {
        const data = await response.text();
        alert(data);
        return;
      }

      alert("Room rented successfully!");

      setRooms((prevRooms) =>
        prevRooms.map((r) =>
          r.roomNumber === roomNumber ? { ...r, isRented: 1 } : r
        )
      );


    } catch (err) {
      console.log(err);
      alert("Failed to rent the room");
    }
  };

  return (
    <div className="p-6 bg-white shadow-lg rounded-lg">
      <h2 className="text-2xl font-semibold text-gray-800 mb-4">Room List</h2>

      <Table className="table-fixed w-full justify-self-center">
        <TableHeader>
          <TableRow>
            <TableHead className="px-6 py-4">Room number</TableHead>
            <TableHead className="px-6 py-4">Price</TableHead>
            <TableHead className="px-6 py-4">Room capacity</TableHead>
            <TableHead className="px-6 py-4">Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {rooms.length > 0 ? (
            rooms.map((room) => (
              <TableRow key={room.roomNumber}>
                <TableCell className="px-6 py-4">{room.roomNumber}</TableCell>
                <TableCell className="px-6 py-4">${room.basePrice}</TableCell>
                <TableCell className="px-6 py-4">{room.roomCapacity}</TableCell>
                <TableCell className="px-6 py-4">
                  {room.isRented === 0 ? (
                    <Button onClick={() => setSelectedRoom(room.roomNumber)}>
                      Rent Room
                    </Button>
                  ) : (
                    <p>Room is rented</p>
                  )}
                </TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={4} className="text-center">
                No rooms available
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>

      {selectedRoom && (
        <div className="mt-4">
          <h3 className="text-xl font-semibold">Rent Room {selectedRoom}</h3>
          <div className="mt-2">
            <label className="block text-gray-600">Start Date</label>
            <input
              type="datetime-local"
              value={beginTime}
              onChange={(e) => setBeginTime(e.target.value)}
              className="mt-1 p-2 border border-gray-300 rounded-md"
            />
          </div>
          <div className="mt-4 w-1/3">
            <Button
              onClick={() => handleRentRoom(selectedRoom)}
              className="w-full"
            >
              Rent Room
            </Button>
          </div>
        </div>
      )}
    </div>
  );
};

export default RoomList;
