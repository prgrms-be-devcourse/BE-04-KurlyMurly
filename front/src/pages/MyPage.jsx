import { useNavigate } from 'react-router-dom';

const MyPage = () => {
  // todo: 인증된 유저만 접근할 수 있게
  const navigate = useNavigate();

  return (
    <div>
      <h1>My Page</h1>
      <button
        onClick={() => {
          navigate(`/mypage/order`);
        }}
      >
        주문 내역 {/* todo */}
      </button>
      <button
        onClick={() => {
          navigate(`/mypage/review`);
        }}
      >
        리뷰 페이지
      </button>
      <button
        onClick={() => {
          navigate(`/mypage/inquiry/products`);
        }}
      >
        상품 문의 {/* todo */}
      </button>
      <button
        onClick={() => {
          navigate(`/mypage/inquiry`);
        }}
      >
        1:1문의
      </button>
      <button
        onClick={() => {
          navigate(`/mypage/info/modify`);
        }}
      >
        개인 정보 수정
      </button>
    </div>
  );
};

export default MyPage;
