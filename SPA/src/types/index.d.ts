type UserRole = 'ADMIN' | 'MANAGER' | 'CLIENT';

type User = {
    id: string;
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    active: boolean;
    userRole: UserRole;
};

type Room = {
    roomNumber: number;
    basePrice: number;
    roomCapacity: number;
}