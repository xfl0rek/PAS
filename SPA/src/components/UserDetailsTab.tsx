const UserDetailsTab = ({ user }: { user: User }) => {
  return (
    <div>
      <table className=" table-auto order-collapse border border-gray-300 text-center">
        <caption className="text-xl font-semibold  mb-5">User details</caption>
        <tbody>
          <tr className="border border-gray-300">
            <td className="px-4 py-2">Username</td>
            <td className="px-4 py-2">{user.username}</td>
          </tr>
          <tr className="border border-gray-300">
            <td className="px-4 py-2">First name</td>
            <td className="px-4 py-2">{user.firstName}</td>
          </tr>
          <tr className="border border-gray-300">
            <td className="px-4 py-2">Last name</td>
            <td className="px-4 py-2">{user.lastName}</td>
          </tr>
          <tr className="border border-gray-300">
            <td className="px-4 py-2">Email</td>
            <td className="px-4 py-2">{user.email}</td>
          </tr>
          <tr className="border border-gray-300">
            <td className="px-4 py-2">Role</td>
            <td className="px-4 py-2">{user.userRole}</td>
          </tr>
          <tr className="border border-gray-300">
            <td className="px-4 py-2">Active</td>
            <td className="px-4 py-2">{user.active ? "Yes" : "No"}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default UserDetailsTab;
