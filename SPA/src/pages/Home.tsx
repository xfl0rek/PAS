import { Breadcrumb, BreadcrumbItem, BreadcrumbLink } from "@/components/ui/breadcrumb";

const Home = () => {
    return (
        <div className="flex h-screen">
            <div className="w-40 p-6 bg-gray-800 text-white shadow-md">
                <Breadcrumb className="flex flex-col space-y-2">
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/">
                            Home
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/users">
                            User list
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/rooms">
                            Rent room
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/rents">
                            Rent list
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                </Breadcrumb>
            </div>

            <div className="flex-1 p-6 bg-white">
                <h1 className="text-3xl font-bold text-gray-800 mb-4">Welcome to the Home Page!</h1>
                <p className="text-gray-600">
                    This is the main content of our hotel.
                </p>
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
