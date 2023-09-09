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
            </div>
        </div>
    );
};

export default Home;
