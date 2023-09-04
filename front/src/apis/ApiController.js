import axios from "axios";
import { useState } from "react";


axios.defaults.withCredentials= true;
const BASE_END_POINT = process.env

const JwtInterceptor = () => {
    const [token, setToken] = useState("")
    const instance = axios.create({
        baseURL: BASE_END_POINT,
    });
}