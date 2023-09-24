import { useNavigate } from 'react-router-dom';
import { setToken } from '../utils/browserStroage';
import JwtInterceptor from './ApiController';

const Auth = () => {
  const { instance } = JwtInterceptor();
  const navigate = useNavigate();

  const checkDuplicationId = async (loginId, setIsIdValidated) => {
    try {
      const res = await instance.post(`/check-id`, {
        loginId: loginId,
      });
      setIsIdValidated(!res.data.success);
      if (!res.data.success) alert('사용가능한 아이디입니다.');
      else alert('이미 사용 중인 아이디입니다.');
    } catch (error) {
      console.error(error);
    }
  };

  const checkDuplicationEmail = async (email, setIsEmailValidated) => {
    try {
      const res = await instance.post('/check-email', {
        email: email,
      });
      setIsEmailValidated(!res.data.success);
      if (!res.data.success) alert('사용가능한 이메일입니다.');
      else alert('이미 사용 중인 이메일입니다.');
    } catch (error) {
      console.error(error);
    }
  };

  const signUp = async (joinData) => {
    try {
      const res = await instance.post(`/signUp`, {
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
    } catch (error) {
      console.error(error);
    }
  };

  const login = async (loginData) => {
    try {
      const res = await instance.post(`/login`, {
        loginId: loginData.loginId,
        password: loginData.password,
      });

      if (res.status === 200) {
        setToken(res.data.data);
        alert('로그인에 성공했습니다!\n홈으로 이동합니다.');
        navigate('/');
      }
    } catch (error) {
      alert('로그인에 실패했습니다!\n다시 시도해주세요.');
      console.error(error);
    }
  };

  return {
    checkDuplicationId,
    checkDuplicationEmail,
    signUp,
    login,
  };
};

export default Auth;
