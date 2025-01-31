import api from "@/lib/api.ts";
import { jwtDecode } from "jwt-decode";

const Sidebar = () => {

  const token = localStorage.getItem("token");
  const role = jwtDecode(token).roles;

  const handleLogout = async () => {

    

    if (token) {
      try {
        await api.post(`/users/logout`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
      } catch (err) {
        console.error(err);
      }

      localStorage.removeItem("username");
      localStorage.removeItem("token");
    }
  };

  return (
    <div className="w-40 h-screen p-6 bg-gray-800 text-white shadow-md">
      <ul className="flex flex-col space-y-2">
        <li>
          <a href="/" className="hover:underline">Home</a>
        </li>
        <br />
        {localStorage.getItem("token") === null ? (
          <>
            <li>
              <a href="/login" className="hover:underline">Login</a>
            </li>
            <li>
              <a href="/register" className="hover:underline">Register</a>
            </li>
          </>
        ) : (
          <li>
            <a href="/" onClick={handleLogout} className="hover:underline">Logout</a>
          </li>
        )}
        <br />
        <li>
          <a href="/users" className="hover:underline">User list</a>
        </li>
        <li>
          <a href="/rooms" className="hover:underline">Rent room</a>
        </li>
        <li>
          <a href="/rents" className="hover:underline">Rent list</a>
        </li>
        {role == "ROLE_ADMIN"  ? (
          <li>
            <a href = "/adminPage" className="hover:underline">Admin page</a>
          </li>
        ) : null}
      </ul>
    </div>
  );
};

export default Sidebar;