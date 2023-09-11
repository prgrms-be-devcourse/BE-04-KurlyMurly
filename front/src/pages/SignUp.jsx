import { useState } from 'react';

const SignUp = () => {
  const [inputs, setInputs] = useState({
    loginId: '',
    password: '',
    rePassword: '',
    name: '',
    email: '',
    phoneNumber: '',
    sex: '',
    birth: '',
    recommender: '',
    roadAddress: '',
  });

  const [isValidated, setIsValidated] = useState({
    isLoginIdChecked: false,
    isEmailChecked: false,
  });

  const {
    loginId,
    password,
    rePassword,
    name,
    email,
    phoneNumber,
    birth,
    recommender,
    roadAddress,
  } = inputs;

  const onInputChange = (e) => {
    const { name, value } = e.target;
    setInputs((inputs) => ({
      ...inputs,
      [name]: value,
    }));
  };

  const validateLoginIdDuplication = (loginId) => {
    console.log(loginId + 'clicked');
  };

  const validateEmailDuplication = (email) => {
    console.log(email + 'clicked');
  };

  const onSignUp = () => {
    console.log(inputs);
  };

  return (
    <div>
      <h2>회원가입</h2>
      <form onSubmit={() => console.log('입력')}>
        <div>
          <h4>로그인 아이디</h4>
          <input
            name="loginId"
            value={loginId}
            type="text"
            onChange={onInputChange}
            placeholder="아이디를 입력해주세요"
            maxLength="20"
          />
          <button type="button" onClick={() => validateLoginIdDuplication(loginId)}>
            중복확인
          </button>
        </div>
        <div>
          <h4>비밀번호</h4>
          <input
            name="password"
            value={password}
            onChange={onInputChange}
            type="password"
            placeholder="비밀번호를 입력해주세요"
          />
        </div>
        <div>
          <h4>비밀번호 확인</h4>
          <input
            name="rePassword"
            value={rePassword}
            onChange={onInputChange}
            type="password"
            placeholder="비밀번호를 한번 더 입력해주세요"
          />
        </div>
        <div>
          <h4>이름</h4>
          <input
            name="name"
            value={name}
            onChange={onInputChange}
            type="text"
            placeholder="이름을 입력해 주세요"
          />
        </div>
        <div>
          <h4>이메일</h4>
          <input
            name="email"
            value={email}
            onChange={onInputChange}
            type="email"
            placeholder="예: marketkurly@kurly.com"
            maxLength="30"
          />
          <button type="button" onClick={() => validateEmailDuplication(email)}>
            중복확인
          </button>
        </div>
        <div>
          <h4>휴대폰</h4>
          <input
            name="phoneNumber"
            value={phoneNumber}
            onChange={onInputChange}
            type="tel"
            placeholder="숫자만 입력해주세요."
          />
        </div>
        <div>
          <h4>주소</h4>
          <input name="roadAddress" value={roadAddress} onChange={onInputChange} type="text" />
        </div>
        <div>
          <h4>성별</h4>
          <input name="sex" value="남성" onChange={onInputChange} type="radio" />
          <label>남성</label>
          <input name="sex" value="여성" onChange={onInputChange} type="radio" />
          <label>여성</label>
          <input name="sex" value="선택안함" onChange={onInputChange} type="radio" />
          <label>선택안함</label>
        </div>
        <div>
          <h4>생년월일</h4>
          <input name="birth" value={birth} onChange={onInputChange} type="date" />
        </div>
        <div>
          <h4>추천인</h4>
          <input
            name="recommender"
            value={recommender}
            onChange={onInputChange}
            type="text"
            placeholder="없으면 비워주세요"
          />
        </div>
      </form>
      <button onClick={onSignUp}>가입하기</button>
    </div>
  );
};

export default SignUp;
