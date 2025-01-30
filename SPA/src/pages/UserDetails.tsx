import RentList from "@/components/RentList";
import Sidebar from "@/components/Sidebar";
import UserDetailsTab from "@/components/UserDetailsTab";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import api from "@/lib/api.ts";
import {Button} from "@/components/ui/button.tsx";
import LoginInfo from "@/components/LoginInfo.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Rent, User} from "@/types";

const UserDetails = () => {
  const { id } = useParams<{ id: string }>();
  const [user, setUser] = useState<User>();
  const [rents, setRents] = useState<Rent[]>([]);
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");

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

  const changePassword = async () => {
    if (oldPassword == newPassword) {
      alert("New password cannot be the same.")
    }

    try {
      await api.put(`/users/changePassword/${id}`, {
        oldPassword,
        newPassword
      });
      alert("Password changed succesfully.");
    } catch (err) {
      console.error(err);
    }
  }

  return user ? (
    <div className="w-screen h-screen flex">
      <div className="w-40 h-full">
        <Sidebar />
      </div>
      <div className="flex-grow overflow-auto">
        <LoginInfo />
        <div className="flex overflow-y-auto flex-col items-center p-10">
          <UserDetailsTab user={user} />
          <RentList rents={rents} setRents={setRents} />
          <div>
            <Input
              type="password"
              placeholder="Enter old password"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
              className="p-2 mb-2 border rounded"
            />
            <Input
              type="password"
              placeholder="Enter new password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              className="p-2 mb-2 border rounded"
            />
          <Button
            onClick={changePassword}
            className="mt-4 bg-blue-500 text-white p-2 rounded"
          >
            Change password
          </Button>
          </div>
        </div>
      </div>
    </div>
  ) : (
    <div></div> 
  );
};

export default UserDetails;
