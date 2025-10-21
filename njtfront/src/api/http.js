import axios from "axios";

const http = axios.create({baseURL:"http://localhost:8080/api", headers: {"Content-Type": "application/json"}, });

//ako u lokalnoj mem imas token uzmi ga i dodaj u karticu za autorizaciju (kao kod postmana)
http.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
     //if (!config.url.includes("/auth")) {
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
});

export default http;