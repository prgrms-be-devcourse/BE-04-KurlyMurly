import axios from 'axios';
import { getToken } from '../utils/browserStroage';

const { REACT_APP_API_END_POINT } = process.env;
axios.defaults.withCredentials = true;

const JwtInterceptor = () => {
  const instance = axios.create({
    baseURL: REACT_APP_API_END_POINT,
  });

  instance.interceptors.request.use(
    (config) => {
      const accessToken = getToken();
      if (accessToken) {
        config.headers['Authorization'] = `Bearer ${accessToken}`;
      }

      config.headers['Content-Type'] = 'application/json';
      return config;
    },
    (error) => {
      alert('요청이 정상적으로 처리되지 않았습니다. \n 다시 시도해주세요.');
      return Promise.reject(error);
    },
  );

  // todo: response interceptor
  return { instance };
};

export default JwtInterceptor;
