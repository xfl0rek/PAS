import RentList from "@/components/RentList";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink } from "@/components/ui/breadcrumb";
import { useEffect, useState } from "react";

const Rents = () => {
    const [rents, setRents] = useState<Rent[]>([]);

    useEffect(() => {
        const fetchRents = async () => {
            try {
                const response = await fetch('/api/rents/');
                if (!response.ok) {
                    console.error("Failed to fetch rents");
                    return;
                }
                const data = await response.json();
                setRents(data);
                console.log(data);
            } catch (err) {
                console.log(err);
            }
        };

        fetchRents();
    }, []);

    return (
        <div className="flex h-screen">
            <div className="w-60 p-4 bg-gray-800 text-white shadow-lg">
                <Breadcrumb className="flex flex-col space-y-2">
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/">Home</BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/users">User list</BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/rooms">Rent room</BreadcrumbLink>
                    </BreadcrumbItem>
                    <BreadcrumbItem>
                        <BreadcrumbLink href="/rents">Rent list</BreadcrumbLink>
                    </BreadcrumbItem>
                </Breadcrumb>
            </div>

            <div>
                <RentList rents={rents} setRents={setRents} />
            </div>
        </div>
    );
};

export default Rents;
