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
        {id: 1, name: '[KF365] 다다기오이 3입', deliveryType: '샛별배송', description: '믿고 먹을 수 있는 상품을 합리적인 가격에, KF365', price: 4490, likes: 9999},
        {id: 2, name: '깐대파 500g', deliveryType: '샛별배송', description: '시원한 국물 맛의 비밀', price: 2990, likes: 9999},
        {id: 3, name: '양파 1.5kg', deliveryType: '샛별배송', description: '"최대혜택가 : 3,432원"', price: 4290, likes: 9999},
        {id: 4, name: '흙당근 1kg', deliveryType: '샛별배송', description: '"최대 혜택가 : 3,912원"', price: 4890, likes: 9999},
    ]);


    return (
        <div>
            <h1>채소</h1>
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