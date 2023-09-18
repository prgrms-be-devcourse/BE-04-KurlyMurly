import { useState } from 'react';
import { User } from '../apis';

const MyInfo = () => {
  const [isEmailValidated, setIsEmailValidated] = useState(true);
  const [currentEmail, setCurrentEmail] = useState('hejow1234@naver.com');
  const [inputs, setInputs] = useState({
    loginId: 'hejow1234',
    currentPassword: '',
    password: '',
    checkPassword: '',
    name: '문희조',
    email: 'hejow1234@naver.com',
    phoneNumber: '01012341234',
    sex: '남성',
    birth: '1995-12-04',
  });

  const { loginId, currentPassword, password, checkPassword, name, email, phoneNumber, birth } =
    inputs;

  const onInputChange = (e) => {
    const { name, value } = e.target;

    if (name === 'email') {
      value !== currentEmail ? setIsEmailValidated(false) : setIsEmailValidated(true);
    }

    setInputs((inputs) => ({
      ...inputs,
      [name]: value,
    }));
  };

  return (
    <div>
      <h2>회원가입</h2>
      <form>
        <div>
          <h4>아이디</h4>
          {loginId}
        </div>
        <div>
          <h4>현재 비밀번호</h4>
          <input
            name="currentPassword"
            value={currentPassword}
            onChange={onInputChange}
            type="password"
            placeholder="비밀번호를 입력해 주세요"
          />
        </div>
        <div>
          <h4>새 비밀번호</h4>
          <input
            name="password"
            value={password}
            onChange={onInputChange}
            type="password"
            placeholder="새 비밀번호를 입력해 주세요"
          />
        </div>
        <div>
          <h4>새 비밀번호 확인</h4>
          <input
            name="checkPassword"
            value={checkPassword}
            onChange={onInputChange}
            type="password"
            placeholder="새 비밀번호를 한번 더 입력해 주세요"
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
            placeholder={email}
            maxLength="30"
          />
          <button
            disabled={isEmailValidated}
            type="button"
            onClick={() => checkDuplicationEmail(email, setIsEmailValidated)}
          >
            {isEmailValidated ? '확인완료' : '중복확인'}
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
      </form>
      <button>탈퇴하기</button>
      <button>회원정보수정</button>
    </div>
  );
};

export default MyInfo;
