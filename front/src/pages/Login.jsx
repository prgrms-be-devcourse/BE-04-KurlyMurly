import { useState } from "react";
import { useNavigate } from 'react-router-dom';

const Login = () => {
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
            <button onClick={() => console.log("hello")}>로그인</button>
            <button onClick={() => {
                navigate(`/sign-up`);
            }}>
                회원가입
            </button>
        </div>
    );
};

export default Login;
