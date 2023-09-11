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
        {id: 1, name: '[브룩클린688] 호주산 목초육 치마살 구이용 300g(냉장)', deliveryType: '샛별배송', description: '100g당 5,166원', price: 12400, likes: 9999},
        {id: 2, name: '[KF365] 양념 소불고기 1kg(냉장)', deliveryType: '샛별배송', description: '100g당 가격: 1,899', price: 18990, likes: 9999},
        {id: 3, name: '[델라치오] 호주산 목초육 안심 스테이크 250g(냉장)', deliveryType: '샛별배송', description: '', price: 17175, likes: 9999},
    ]);


    return (
        <div>
            <h1>정육/계란</h1>
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