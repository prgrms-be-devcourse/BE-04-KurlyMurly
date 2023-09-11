import {useState} from 'react';

function NewProduct() {
    const [product, setProduct] = useState({
        id: '',
        name: '',
        deliveryType: '',
        description: '',
        price: '',
        likes: '',
    });

    const [items, setItems] = useState([
        {id: 1, name: '삼해 참좋은 조미김가루 60g', deliveryType: '샛별배송', description: '다양한 요리에 간편하게 활용', price: 2380, likes: 0},
        {id: 2, name: '후라이드 바삭 햇김 3종 (택1)', deliveryType: '샛별배송', description: '고소함이 그대로 담긴 간식', price: 5380, likes: 14},
        {id: 3, name: '[주전부리마켓] 추억의 기차역 전기구이오징어', deliveryType: '샛별배송', description: '집에서 만나는 추억의 간식', price: 4500, likes: 12},
        {id: 4, name: '[비비고] 초사리 곱창돌김 12봉', deliveryType: '샛별배송', description: '오독오독 매략적인 식감', price: 6480, likes: 11},
    ]);


    return (
        <div>
            <h1>수산/해산/건어물</h1>
            {items.map((product) => (
                <div key = {product.id}>
                  <p><img src='' alt='상품 사진'/></p>
                  <p><button>장바구니 담기</button></p>
                  <p>{product.name}</p>
                  <p>{product.description}</p>
                  <p>{product.price}원</p>
                  <p>reviews: {product.likes}</p>
                  <p><hr/></p>
        </div>
        ))}
        </div>
    );
}

export default NewProduct;