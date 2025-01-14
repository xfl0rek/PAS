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
      <div className="w-40 h-full">
        <Sidebar />
      </div>
      <div className="flex flex-col items-center w-full h-full p-10">
        <p className="text-center">
          Logged as {localStorage.getItem("username")}
        </p>
        <UserDetailsTab user={user} />
        <RentList rents={rents} setRents={setRents} />
      </div>
    </div>
  ) : (
    <div></div> //TODO DODAC
  );
};

export default UserDetails;
