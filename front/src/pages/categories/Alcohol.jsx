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
        {id: 1, name: '[한정수량][커피빈픽업] 글렌피딕 12년', deliveryType: '셀프픽업', description: '싱글몰트 위스키 / 스코틀랜드', price: 85900, likes: 0},
        {id: 2, name: '[[한정수량][커피빈픽업] 글렌피딕 15년', deliveryType: '셀프픽업', description: '싱글몰트 위스키 / 스코틀랜드', price: 116900, likes: 0},
        {id: 3, name: '[커피빈 픽업] 마리부 라임', deliveryType: '셀프픽업', description: '리큐르 / 스페인', price: 26000, likes: 0},
        {id: 4, name: '[커피빈 픽업] 말피 진 콘 아란치아', deliveryType: '셀프픽업', description: '진 / 이탈리아', price: 50000, likes: 0},
    ]);


    return (
        <div>
            <h1>와인/위스키/전통주</h1>
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