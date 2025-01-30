import RoomList from "@/components/RoomList";
import Sidebar from "@/components/Sidebar";
import LoginInfo from "@/components/LoginInfo.tsx";

const Rooms = () => {
  return (
    <div className="flex h-screen">
      <div className="w-40 h-full">
        <Sidebar />
      </div>
      <div>
        <LoginInfo />
        <RoomList />
      </div>
    </div>
  );
};

export default Rooms;
