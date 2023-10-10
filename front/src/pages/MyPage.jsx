import { useNavigate } from 'react-router-dom';

const MyPage = () => {
  const navigate = useNavigate();

  return (
    <div>
      <h1>My Page</h1>
      <button
        onClick={() => {
          navigate(`/mypage/order`);
        }}
      ></button>
      <button
        onClick={() => {
          navigate(`/mypage/review`);
        }}
      >
        리뷰 페이지
      </button>
      <button
        onClick={() => {
          navigate(`/mypage/pick/list`);
        }}
      >
        찜한 상품
      </button>
      <button
        onClick={() => {
          navigate(`/mypage/address`);
        }}
      >
        배송지 관리
      </button>
      <button
        onClick={() => {
          navigate(`/mypage/inquiry/products`);
        }}
      ></button>
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
