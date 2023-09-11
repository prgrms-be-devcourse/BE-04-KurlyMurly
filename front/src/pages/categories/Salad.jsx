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
        {id: 1, name: '춘천 국물 닭갈비 떡볶이', deliveryType: '샛별배송', description: '닭갈비와 떡볶이의 오묘한 조화', price: 12500, likes: 9999},
        {id: 2, name: '[이연복의 목란] 짬뽕 2인분 (맵기선택)', deliveryType: '샛별배송', description: '입맛에 맞게 고르는 인기 메뉴', price: 13500, likes: 9999},
        {id: 3, name: '[스윗밸런스] 오늘의 샐러드 10종(리뉴얼)(택1)', deliveryType: '샛별배송', description: '다채로운 토핑을 얹은 샐러드', price: 4900, likes: 9999},
    ]);


    return (
        <div>
            <h1>샐러드/간편식</h1>
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