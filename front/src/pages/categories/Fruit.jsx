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
        {id: 1, name: '제수용 배 2kg (3입)', deliveryType: '샛별배송', description: '큼직하고 탐스러운 배', price: 15900, likes: 211},
        {id: 2, name: '[제스프리] 뉴질랜드 골드키위 1.1kg(7~10압)', deliveryType: '샛별배송', description: '뉴질랜드에서 온 촉촉한 달콤함', price: 15900, likes: 9999},
        {id: 3, name: '땅근 해남 쌀 10kg', deliveryType: '샛별배송', description: '해남에서 온 건강한 밥맛', price: 29900, likes: 9999},
        {id: 4, name: '저탄소 샤인머스캣 2종', deliveryType: '샛별배송', description: '상큼함으로 무장한 연둣빛 포도', price: 11900, likes: 9999},
    ]);


    return (
        <div>
            <h1>과일/견과/쌀</h1>
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