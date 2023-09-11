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
        {id: 1, name: '[햇반] 현미 귀리 곤약밥 150gX12입', deliveryType: '샛별배송', description: '부담 없이 맛보는 구수한 밥맛', price: 25300, likes: 999},
        {id: 2, name: '[레오나르디] 모데나산 콘디멘토 화이트 발사믹', deliveryType: '샛별배송', description: '부드러운 산미와 아름다운 색', price: 23200, likes: 999},
        {id: 3, name: '[동원참치] 살코기 참치 85g X 6캔', deliveryType: '샛별배송', description: '담백한 풍미가 매력적인 참치', price: 9880, likes: 999},
    ]);


    return (
        <div>
            <h1>면/양념/오일</h1>
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