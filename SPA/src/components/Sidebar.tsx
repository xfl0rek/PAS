const Sidebar = () => {
  const handleLogout = () => {
    localStorage.removeItem("username");
  };

  return (
    <div className="w-40 h-screen p-6 bg-gray-800 text-white shadow-md">
      <ul className="flex flex-col space-y-2">
        <li>
          <a href="/" className="hover:underline">Home</a>
        </li>
        <br />
        {localStorage.getItem("username") === null ? (
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
      </ul>
    </div>
  );
};

export default Sidebar;