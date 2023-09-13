import { useNavigate } from 'react-router-dom';
import { setStorage } from '../utils/browserStroage';
import JwtInterceptor from './ApiController';

const Auth = () => {
  const { instance } = JwtInterceptor();
  const navigate = useNavigate();

  const checkValidateLoginId = (loginId) => {
    handlingAxiosError(async () => {
      const res = await instance.post('/users/login-id', {
        loginId: loginId,
      });
      res.data.success ? alert('중복된 아이디입니다!') : alert('사용 가능합니다.');
    });
  };

  const checkValidateEmail = (email) => {
    handlingAxiosError(async () => {
      const res = await instance.post('/users/check-email', {
        email: email,
      });
      res.data.success ? alert('중복된 이메일입니다!') : alert('사용 가능합니다.');
    });
  };

  const signUp = (joinData) => {
    handlingAxiosError(async () => {
      const res = await instance.post('/users', {
        loginId: joinData.loginId,
        password: joinData.password,
        checkPassword: joinData.checkPassword,
        name: joinData.name,
        email: joinData.email,
        phoneNumber: joinData.phoneNumber,
        sex: joinData.sex,
        birth: joinData.birth,
        recommender: joinData.recommender,
        roadAddress: joinData.roadAddress,
      });

      if (res.status === 200) {
        alert('회원가입에 성공했습니다! \n로그인 페이지로 이동합니다.');
        navigate('/login');
      }
    });
  };

  const login = (loginData) => {
    handlingAxiosError(async () => {
      const res = await instance.post('/users/login', {
        loginId: loginData.loginId,
        password: loginData.password,
      });
      setStorage('JWT', res.data);
      navigate('/');
    });
  };

  return {
    checkValidateLoginId,
    checkValidateEmail,
    signUp,
    login,
  };
};

const handlingAxiosError = (callBack) => {
  try {
    return callBack();
  } catch (error) {
    console.error(error);
  }
};

export default Auth;
