import { useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";

const RentList = ({
  rents,
  setRents,
}: {
  rents: Rent[];
  setRents: (rents: Rent[]) => void;
}) => {
  const username = localStorage.getItem("username");
  const [selectedRent, setSelectedRent] = useState<Rent | null>(null);

  const handleReturnRoom = async () => {
    if (!selectedRent?.endTime) {
      alert("Please select an end time.");
      return;
    }

    try {
      const response = await fetch(
        `/api/rents/returnRoom/${selectedRent?.id}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(selectedRent),
        }
      );

      if (!response.ok) {
        const data = await response.text();
        console.error("Failed to return room");
        alert(data);
        return;
      }

      alert("Room returned successfully!");
      setSelectedRent(null);

      const updatedRents = rents.map((rent) =>
        rent.id === selectedRent.id
          ? { ...rent, endTime: selectedRent.endTime }
          : rent
      );
      setRents(updatedRents);
    } catch (err) {
      console.error(err);
      alert("An error occurred while returning the room.");
    }
  };

  return (
    <div className="p-6 bg-white shadow-lg overflow-auto rounded-lg">
      <h2 className="text-2xl font-semibold text-gray-800 mb-4">Rent List</h2>

      <div className="w-full overflow-auto">
        <Table className="w-full overflow-auto justify-self-center">
          <TableHeader>
            <TableRow>
              <TableHead className="px-6 py-4">ID</TableHead>
              <TableHead className="px-6 py-4">Username</TableHead>
              <TableHead className="px-6 py-4">Room Number</TableHead>
              <TableHead className="px-6 py-4">Begin Time</TableHead>
              <TableHead className="px-6 py-4">End Time</TableHead>
              <TableHead className="px-6 py-4">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {rents.length > 0 ? (
              rents.map((rent) => (
                <TableRow key={rent.id}>
                  <TableCell className="px-6 py-4">{rent.id}</TableCell>
                  <TableCell className="px-6 py-4">
                    {rent.clientUsername}
                  </TableCell>
                  <TableCell className="px-6 py-4">{rent.roomNumber}</TableCell>
                  <TableCell className="px-6 py-4">
                    {new Date(rent.beginTime).toLocaleString()}
                  </TableCell>
                  <TableCell className="px-6 py-4">
                    {rent.endTime
                      ? new Date(rent.endTime).toLocaleString()
                      : ""}
                  </TableCell>
                  <TableCell className="px-6 py-4">
                    {username &&
                    rent.clientUsername === username &&
                    !rent.endTime ? (
                      <Button
                        onClick={() => {
                          setSelectedRent(rent);
                        }}
                      >
                        Select for Return
                      </Button>
                    ) : (
                      <TooltipProvider>
                        <Tooltip>
                          <TooltipTrigger asChild>
                            <Button className="opacity-50 cursor-not-allowed">
                              Select for Return
                            </Button>
                          </TooltipTrigger>
                          <TooltipContent>
                            <p>You can only return rented room.</p>
                          </TooltipContent>
                        </Tooltip>
                      </TooltipProvider>
                    )}
                  </TableCell>
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={6} className="text-center">
                  No rents available
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>

      {selectedRent && (
        <div className="mt-4">
          <h3 className="text-xl font-semibold">Return Room</h3>
          <div className="mt-2">
            <label className="block text-gray-600">End Time</label>
            <input
              type="datetime-local"
              value={selectedRent?.endTime || ""}
              onChange={(e) =>
                setSelectedRent((prev) =>
                  prev ? { ...prev, endTime: e.target.value } : null
                )
              }
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
