import UserUpdateForm from "@/components/UserUpdateForm";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Card, CardContent, CardTitle } from "@/components/ui/card";
import api from "@/lib/api.ts";
import { User } from "@/types";

const UpdateUser = () => {
  const { id } = useParams<{ id: string }>();
  const [user, setUser] = useState<User>();
  const [signature, setSignature] = useState<string>("");
  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await api.get(`/users/${id}`);
        setUser(response.data);
        setSignature(response.headers.jws);
      } catch (err) {
        console.error(err);
      }
    };
    fetchUser();
  }, [id]);

  if (!user) {
    return <div>Failed to fetch user</div>;
  }

  return (
    <div className="flex items-center justify-center w-screen min-h-screen bg-gray-100">
      <Card className="max-w-md w-full shadow-lg">
        <CardTitle className="text-center p-10">User data update</CardTitle>
        <CardContent>
          <UserUpdateForm user={user} jws={signature}/>
        </CardContent>
      </Card>
    </div>
  );
};

export default UpdateUser;
