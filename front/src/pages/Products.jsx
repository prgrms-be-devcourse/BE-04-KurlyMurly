import {useState} from 'react';
import {useNavigate} from "react-router-dom";

function Products() {
    const [product, setProduct] = useState({
        id: '',
        name: '',
        deliveryType: '',
        description: '',
        price: '',
        likes: '',
    });

    const [items, setItems] = useState([
        {id: 1, name: '제수용 배 2kg (3입)', deliveryType: '샛별배송', description: '큼직하고 탐스러운 배', price: 15900, likes: 211},
        {id: 2, name: '[타쿠미야] 고급생식빵 하프', deliveryType: '샛별배송', description: '변함없는 부드러움', price: 7000, likes: 0},
        {id: 3, name: '[슈퍼키친] 유니짜장', deliveryType: '샛별배송', description: '온 가족 인기 메뉴', price: 5900, likes: 0},
        {id: 4, name: '[솔가원] 한돈 떡갈비', deliveryType: '샛별배송', description: '가정에서 즐기는 맛집 떡갈비', price: 7900, likes: 17},
    ]);

    const navigate =  useNavigate();

    const goToDetail = () => {
        navigate("/detail",
            {state: {
                id: 1,
                deliveryType: '샛별배송',
                name: '제수용 배 2kg (3입)',
                description: '큼직하고 탐스러운 배',
                price: 15900,
                likes: 211
            }
            });
    };

    return (
        <div>
            <h1>신상품</h1>
            {items.map((product) => (
                <div key = {product.id}>
                  <p><img src='' alt='상품 사진'/></p>
                  <p>{product.name}</p>
                  <p>{product.description}</p>
                  <p>{product.price}원</p>
                  <p>reviews: {product.likes}</p>
                  <p><button>장바구니 담기</button></p>
                  <p><button onClick={goToDetail}>상품 보러가기</button></p>
                  <p><hr/></p>
        </div>
        ))}
        </div>
    );
}

export default Products;
