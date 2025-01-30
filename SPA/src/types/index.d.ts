type UserRole = 'ROLE_ADMIN' | 'ROLE_MANAGER' | 'ROLE_CLIENT';

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
    isRented: number;
}

type Rent = {
    id: number;
    clientUsername: string;
    roomNumber: number;
    beginTime: string;
    endTime: string;
}

export interface DecodedToken {
    username: string;
    role: UserRole;
    iat: number;
    exp: number;
}