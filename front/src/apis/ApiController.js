import axios from 'axios';

axios.defaults.withCredentials = true;
const BASE_END_POINT = process.env;

const instance = axios.create({
  baseURL: BASE_END_POINT,
});

export default instance;
