import Sidebar from "@/components/Sidebar";

const Home = () => {
  return (
    <div className="flex h-screen w-screen">
      <Sidebar />
      <div className="flex-1 px-6 bg-white">
        <p className="text-center">
          Logged as {localStorage.getItem("username")}
        </p>
        <h1 className="text-3xl font-bold text-gray-800 mb-4">
          Welcome to the Home Page!
        </h1>
        <p className="text-gray-600">This is the main content of our hotel.</p>
        <div className="mt-6">
          <h2 className="text-2xl font-semibold text-gray-700">Quick Links</h2>
          <ul className="list-disc list-inside text-gray-600 mt-2">
            <li>
              <a href="/users" className="text-blue-500 hover:underline">
                View User List
              </a>
            </li>
            <li>
              <a href="/rooms" className="text-blue-500 hover:underline">
                Rent a Room
              </a>
            </li>
            <li>
              <a href="/rents" className="text-blue-500 hover:underline">
                Rent List
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Home;
