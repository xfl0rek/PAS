import RentList from "@/components/RentList";
import Sidebar from "@/components/Sidebar";
import UserDetailsTab from "@/components/UserDetailsTab";
import { useEffect, useState } from "react";
import { useParams } from "react-router";

const UserDetails = () => {
  const { id } = useParams<{ id: string }>();
  const [user, setUser] = useState<User>();
  const [rents, setRents] = useState<Rent[]>([]);

  useEffect(() => {
    const fetchUser = async () => {
      const response = await fetch(`/api/users/${id}`);
      const data = await response.json();
      setUser(data);
    };
    const fetchRents = async () => {
      const response = await fetch(`/api/rents/getAllRentsForUser/${id}`);
      const data = await response.json();
      setRents(data);
    };
    fetchUser();
    fetchRents();
  }, [id]);

  return user ? (
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
        <div className="flex overflow-auto flex-col items-center p-10">
          <UserDetailsTab user={user} />
        </div>
        <div className="overflow-auto">
          <RentList rents={rents} setRents={setRents} />
        </div>
      </div>
    </div>
  ) : (
    <div></div>
  );
};

export default UserDetails;
