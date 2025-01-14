import RoomList from "@/components/RoomList";
import Sidebar from "@/components/Sidebar";

const Rooms = () => {
  return (
    <div className="flex h-screen">
      <div className="w-40 h-full">
        <Sidebar />
      </div>
      <div>
        <p className="text-center">
          {localStorage.getItem("username") === null
            ? "Not logged in"
            : "Logged in as " + localStorage.getItem("username")}
        </p>
        <RoomList />
      </div>
    </div>
  );
};

export default Rooms;
