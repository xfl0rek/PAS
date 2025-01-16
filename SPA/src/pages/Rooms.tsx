import RoomList from "@/components/RoomList";
import Sidebar from "@/components/Sidebar";

const Rooms = () => {
  return (
    <div className="flex w-screen h-screen">
      <div className="w-40 sm:w-40 h-full">
        <Sidebar />
      </div>
      <div className="flex-grow overflow-auto">
        <p className="text-center">
          {localStorage.getItem("username") === null
            ? "Not logged in"
            : "Logged in as " + localStorage.getItem("username")}
        </p>
        <div className="overflow-auto">
          <RoomList />
        </div>
      </div>
    </div>
  );
};

export default Rooms;
