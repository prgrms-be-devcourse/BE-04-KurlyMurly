const storage = localStorage;

export const isLoginedStorage = () => {
  const state = localStorage.login;
  return !!state;
};

export const getTokenFromStorage = (key) => {
  return withHandlingError(() => {
    const token = storage.getItem(key);
    return token || undefined;
  });
};

export const setStorage = (key, value) => {
  withHandlingError((key, value) => {
    storage.setItem(key, value);
  });
};

export const removeFromStorage = (key) => {
  withHandlingError((key) => {
    storage.removeItem(key);
  });
};

const withHandlingError = (callBack) => {
  try {
    return callBack();
  } catch (error) {
    console.error(error);
  }
};
