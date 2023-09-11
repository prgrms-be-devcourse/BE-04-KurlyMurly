import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { Auth } from "../apis";

const Login = () => {
    const { login } = Auth();
    const navigate = useNavigate();
    const [loginInputs, setLoginInputs] = useState({
        loginId: "",
        password: "",
    });

    const { loginId, password } = loginInputs;

    const onInputChange = (e) => {
        const { name, value } = e.target;
        setLoginInputs((inputs) => ({
            ...inputs,
            [name]: value,
        }));
    };

    const attemptLogin = () => {
        login(loginInputs);
    }

    return (
        <div>
            <h2>로그인</h2>
            <div>
                <h4>아이디</h4>
                <input
                    name="loginId"
                    value={loginId}
                    type="text"
                    onChange={onInputChange}
                    maxLength="20"
                />
            </div>
            <div>
                <h4>비밀번호</h4>
                <input
                    name="password"
                    value={password}
                    onChange={onInputChange}
                    type="password"
                />
            </div>
            <button onClick={() => attemptLogin}>로그인</button>
            <button onClick={() => {
                navigate(`/sign-up`);
            }}>
                회원가입
            </button>
        </div>
    );
};

export default Login;
