import UserUpdateForm from "@/components/UserUpdateForm";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import {
    Card,
    CardContent,
    CardTitle,
  } from "@/components/ui/card"

const UpdateUser = () => {
    const { id } = useParams<{ id: string }>();
    const [user, setUser] = useState<User>();
    useEffect(() => {
        const fetchUser = async () => {
            const response = await fetch(`/api/users/${id}`);
            const data = await response.json();
            setUser(data);
        }
        fetchUser();
    }, [id]);

    if (!user) {
        return (
            <div>
{/*TODO uzupelnic jakims bledem ze nie ma  */}
            </div>
        );
    }

    return (
        <div className="flex items-center justify-center w-screen min-h-screen bg-gray-100">
            <Card className="max-w-md w-full shadow-lg">
                <CardTitle className="text-center p-10">User data update</CardTitle>
                <CardContent>
                    <UserUpdateForm user={user}/>
                </CardContent>
            </Card>
        </div>
    )
};

export default UpdateUser;