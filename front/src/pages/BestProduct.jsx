import {useState} from 'react';

function BestProduct() {
    const [product, setProduct] = useState({
        id: '',
        name: '',
        deliveryType: '',
        description: '',
        price: '',
        likes: '',
    });

    const [items, setItems] = useState([
        {id: 1, name: '[제스프리] 뉴질랜드 골드키위 1.1kg(7~10압)', deliveryType: '샛별배송', description: '뉴질랜드에서 온 촉촉한 달콤함', price: 15900, likes: 9999},
        {id: 2, name: '[압구정주꾸미] 주꾸미 볶음 300g', deliveryType: '샛별배송', description: '마늘의 감칠맛이 듬뿍', price: 6900, likes: 999},
        {id: 3, name: '[이연복의 목란] 짬뽕 2인분(맵기선택)', deliveryType: '샛별배송', description: '입맛에 맞게 고르는 인기 메뉴', price: 13500, likes: 9999},
        {id: 4, name: '[사미현] 갈비탕', deliveryType: '샛별배송', description: '진짜 갈비로 우려낸 전통 갈비탕', price: 12000, likes: 9999},
    ]);


    return (
        <div>
            <h1>베스트</h1>
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

export default BestProduct;