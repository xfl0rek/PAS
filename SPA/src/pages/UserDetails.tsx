import RentList from "@/components/RentList";
import Sidebar from "@/components/Sidebar";
import UserDetailsTab from "@/components/UserDetailsTab";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import api from "@/lib/api.ts";

const UserDetails = () => {
  const { id } = useParams<{ id: string }>();
  const [user, setUser] = useState<User>();
  const [rents, setRents] = useState<Rent[]>([]);

  useEffect(() => {
    const fetchUser = async () => {
      console.log(id);
      try {
        const response = await api.get(`/users/${id}`)
        setUser(response.data)
      } catch (err) {
        console.error(err);
      }
    };
    const fetchRents = async () => {
      try {
        const response = await api.get(`/rents/getAllRentsForUser/${id}`);
        setRents(response.data)
      } catch (err) {
        console.error(err);
      }
    };
    fetchUser();
    fetchRents();
  }, [id]);

  return user ? (
    <div className="w-screen h-screen flex">
      <div className="w-40 h-full">
        <Sidebar />
      </div>
      <div className="flex-grow overflow-auto">
        <p className="text-center">
          {localStorage.getItem("username") === null
            ? "Not logged in"
            : "Logged in as " + localStorage.getItem("username")}
        </p>
        <div className="flex overflow-y-auto flex-col items-center p-10">
          <UserDetailsTab user={user} />
          <RentList rents={rents} setRents={setRents} />
        </div>
      </div>
    </div>
  ) : (
    <div></div> 
  );
};

export default UserDetails;
