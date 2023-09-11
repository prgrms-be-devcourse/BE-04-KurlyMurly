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
        {id: 2, name: '[사미현] 갈비탕', deliveryType: '샛별배송', description: '진짜 갈비로 우려낸 전통 갈비탕', price: 12000, likes: 9999},
        {id: 3, name: '[숭의가든] 한돈 목살 양념 구인', deliveryType: '샛별배송', description: '우리 집에 찾아온 인천 맛집', price: 14900, likes: 9999},
        {id: 4, name: '[마이하노이] 고기폭탄쌀국수', deliveryType: '샛별배송', description: '신사동 가로수길, 하노이식 정통 쌀국수 집', price: 6900, likes: 9999},
    ]);


    return (
        <div>
            <h1>국/반찬/메인요리</h1>
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