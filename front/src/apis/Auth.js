import JwtInterceptor from "./ApiController";
import { useNavigate } from "react-router-dom";
import { setStorage } from "../utils/browserStroage";

const Auth = () => {
    const { instance } = JwtInterceptor();
    const navigate = useNavigate();

    const checkValidateLoginId = (loginId) => {
        handlingAxiosError(async () => {
            const res = await instance.post('/users/login-id', loginId);
            res.success ? alert("중복된 아이디입니다!") : alert("사용 가능합니다.");
        });
    };

    const checkValidateEmail = (email) => {
        handlingAxiosError(async () => {
            const res = await instance.post('/users/login-id', email);
            res.success ? alert("중복된 이메일입니다!") : alert("사용 가능합니다.");
        });
    };

    const signUp = (joinData) => {
        handlingAxiosError(async () => {
            const res = await instance.post('/users', {
                loginId: joinData.loginId,
                password: joinData.password,
                name: joinData.name,
                email: joinData.email,
                phoneNumber: joinData.phoneNumber,
                sex: joinData.sex,
                birth: joinData.birth,
                recommender: joinData.recommender,
                roadAddress: joinData.roadAddress,
            });

            if (res.success) {
                navigate("/login", {state: joinData.loginId})
            }
        });
    };

    const login = (loginData) => {
        handlingAxiosError(async () => {
            const res = await instance.post('/login', {
                loginId: loginData.loginId,
                password: loginData.password,
            });

            setStorage("JWT", res.data);
            navigate("/");
        });
    };
}

const handlingAxiosError = (callBack) => {
    try {
        return callBack();
    } catch (error) {
        console.log(error);
    }
}

export default Auth;
