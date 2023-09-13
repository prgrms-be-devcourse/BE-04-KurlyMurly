import { useNavigate } from 'react-router-dom';

const MyPage = () => {
  // todo: 인증된 유저만 접근할 수 있게
  const navigate = useNavigate();

  return (
    <div>
      <h1>My Page</h1>
      <button
        onClick={() => {
          navigate(`/my-page/review`);
        }}
      >
        리뷰 페이지
      </button>
      <button
        onClick={() => {
          navigate(`/support`);
        }}
      >
        1:1문의
      </button>
    </div>
  );
};

export default MyPage;
