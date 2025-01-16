import RentList from "@/components/RentList";
import Sidebar from "@/components/Sidebar";
import { useEffect, useState } from "react";

const Rents = () => {
  const [rents, setRents] = useState<Rent[]>([]);

  useEffect(() => {
    const fetchRents = async () => {
      try {
        const response = await fetch("/api/rents/");
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

  return (
    <div className="w-screen h-screen flex">
      <div className="w-20 sm:w-40 h-full">
        <Sidebar />
      </div>
      <div className="flex-grow overflow-auto">
        <p className="text-center">
          {localStorage.getItem("username") === null
            ? "Not logged in"
            : "Logged in as " + localStorage.getItem("username")}
        </p>
        <div className="overflow-auto">
          <RentList rents={rents} setRents={setRents} />
        </div>
      </div>
    </div>
  );
};

export default Rents;
