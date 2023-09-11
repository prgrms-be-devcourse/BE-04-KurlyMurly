import { useNavigate } from "react-router-dom";
import { setStorage } from "../utils/browserStroage";
import JwtInterceptor from "./ApiController";

const Auth = () => {
    const { instance } = JwtInterceptor();
    const navigate = useNavigate();

    const checkValidateLoginId = (loginId) => {
        handlingAxiosError(async () => {
            const res = await instance.post('/users/login-id', loginId);
            res.data.success ? alert("중복된 아이디입니다!") : alert("사용 가능합니다.");
        });
    };

    const checkValidateEmail = (email) => {
        handlingAxiosError(async () => {
            const res = await instance.post('/users/login-id', email);
            res.data.success ? alert("중복된 이메일입니다!") : alert("사용 가능합니다.");
        });
    };

    const signUp = (joinData) => {
        handlingAxiosError(async () => {
            const res = await instance.post('/users', joinData);

            if (res.data.success) {
                navigate("/login", {state: joinData.loginId})
            }
        });
    };

    const login = (loginData) => {
        handlingAxiosError(async () => {
            const res = await instance.post('/login', loginData);
            setStorage("JWT", res.data);
            navigate("/");
        });
    };

    return {
        checkValidateLoginId,
        checkValidateEmail,
        signUp,
        login
    }
}

const handlingAxiosError = (callBack) => {
    try {
        return callBack();
    } catch (error) {
        console.log(error);
    }
}

export default Auth;
