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
        {id: 1, name: '[선물세트] 오설록 프리미엄 티 컬렉션 10종(40개입)', deliveryType: '샛별배송', description: '다채롭게 즐기는 맛과 향', price: 22900, likes: 0},
        {id: 2, name: '[바라기] 따듯따뜻 약쑥액 (30포)', deliveryType: '샛별배송', description: '몸 속을 채우는 약쑥의 온기', price: 37900, likes: 0},
        {id: 3, name: '[콜드] 베스트 주스 3종(오렌지, 포도, 제주감귤)(택1)', deliveryType: '샛별배송', description: '상큼한 가득 담은 과일 주스', price: 2740, likes: 0},
    ]);


    return (
        <div>
            <h1>생수/음료/우유/커피</h1>
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