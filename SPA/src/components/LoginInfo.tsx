import {DecodedToken} from "@/types";
import {jwtDecode} from "jwt-decode";


const LoginInfo = () => {
    const token = localStorage.getItem('token');
    const decoded = jwtDecode<DecodedToken>(token!);
    const username = decoded.sub;
    const roles = decoded.roles;
    const role = roles[0].replace('ROLE_', '');

    return (
        <div className="text-center">
            <p>Username: {username} {role}</p>
        </div>
    )

}

export default LoginInfo;