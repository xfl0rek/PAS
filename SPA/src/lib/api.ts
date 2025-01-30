import axios from "axios";

const auth = axios.create({
    baseURL: "/api",
    headers: {
        "Content-Type": "application/json",
    },
});

const api = axios.create({
    baseURL: "/api",
    headers: {
        "Content-Type": "application/json",
    },
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
})

export default api;

export { auth };