import RentList from "@/components/RentList";
import Sidebar from "@/components/Sidebar";
import { useEffect, useState } from "react";
import api from "@/lib/api.ts";
import LoginInfo from "@/components/LoginInfo.tsx";

const Rents = () => {
  const [rents, setRents] = useState<Rent[]>([]);

  useEffect(() => {
    const fetchRents = async () => {
      try {
        const response = await api.get("/rents/");
        setRents(response.data);
      } catch (err) {
        console.log(err);
      }
    };

    fetchRents();
  }, []);

  return (
    <div className="flex h-screen">
      <div className="w-40 h-full">
        <Sidebar />
      </div>
      <div className="overflow-auto flex-grow p-10">
        <LoginInfo />
        <RentList rents={rents} setRents={setRents} />
      </div>
    </div>
  );
};

export default Rents;
