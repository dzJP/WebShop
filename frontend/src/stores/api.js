import axios from "axios";

const api = axios.create({
    baseURL: "https://localhost:8443/api/v1",  // Your backend URL
    headers: {
        "Content-Type": "application/json",
    },
});

// Attach JWT token to all requests
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");
        console.log("Sending Token:", token);  // ✅ Debug token before sending

        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
    },
    (error) => {
        console.error("Request Error:", error);
        return Promise.reject(error);
    }
);

export default api;
