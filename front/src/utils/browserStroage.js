const storage = localStorage;
const JWT = 'JWT';

export const getToken = () => {
  return localStorage.getItem(JWT);
};

export const setToken = (token) => {
  storage.setItem(JWT, token);
};

export const removeToken = (token) => {
  storage.removeItem(token);
};
