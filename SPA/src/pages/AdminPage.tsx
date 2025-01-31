import LoginInfo from "@/components/LoginInfo";
import Sidebar from "@/components/Sidebar";

const AdminPage = () => {

    return (

        <div className="flex h-screen">
      <div className="w-40 h-full">
        <Sidebar />
      </div>
      <div className="overflow-auto flex-grow p-10">
        <LoginInfo />
        <h1> Admin Page </h1>  
      </div>
    </div>
       
    )
}

export default AdminPage;