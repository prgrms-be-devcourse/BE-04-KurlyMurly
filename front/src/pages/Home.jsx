import {useNavigate} from 'react-router-dom';

const Home = () => {
    const navigate = useNavigate();

    return (
        <div>
            <h1>Kurly-Murly</h1>
            <button
                onClick={() => {
                    navigate(`/login`);
                }}
            >
                로그인
            </button>
            <button
                onClick={() => {
                    navigate(`/sign-up`);
                }}
            >
                회원가입
            </button>
            <div>
                <button
                    onClick={() => {
                        navigate(`/categories`);
                    }}
                >
                    카테고리
                </button>
                <button
                    onClick={() => {
                        navigate(`/new-products`);
                    }}
                >
                    신상품
                </button>
                <button
                    onClick={() => {
                        navigate(`/best-products`);
                    }}
                >
                    베스트
                </button>
                <button
                    onClick={() => {
                        navigate(`/cart`);
                    }}
                >
                    장바구니
                </button>
                <button
                    onClick={() => {
                        navigate(`/favorites`);
                    }}
                >
                    찜목록
                </button>
                <button
                    onClick={() => {
                        navigate(`/receipt`);
                    }}
                >
                    주문서
                </button>
                <button
                    onClick={() => {
                        navigate(`/support`);
                    }}
                >
                    1:1문의 작성
                </button>
                <br></br>
                <button
                    onClick={() => {
                        navigate(`/create-review`);
                    }}
                >
                    리뷰 작성
                </button>
                <button
                    onClick={() => {
                        navigate(`/review-list`);
                    }}
                >
                    작성한 리뷰 보기
                </button>
                <button
                    onClick={() => {
                        navigate(`/product-review`);
                    }}
                >
                    상품에 대한 리뷰 보기
                </button>

                <hr/>
                테스트
                <br/>
                <button onClick={() => {
                    navigate(`/detail`);
                }}
                        >
                    상품 상세보기
                </button>
            </div>
        </div>
    );
};

export default Home;
