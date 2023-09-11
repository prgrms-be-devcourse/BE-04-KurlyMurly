import { useState } from 'react';

function Favorites() {
  const [favorite, setFavorite] = useState({
    productId : '',
    productName : '',
    price : '',
  });

  const [items, setItems] = useState([
    {productId: 1, productName: '[숭의가든] 옛날 소 불고기', price: 14900 },
    {productId: 1, productName: '[제스프리] 뉴질랜드 골드키위 1.1kg(7~10)입', price: 15900 },
    {productId: 1, productName: '[KF365] 양념 소불고기 1kg(냉장)', price: 18990 },
  ]);

  const removeProduct = (productId) => {
    setFavorite(favorite.filter((el) => el !== productId));
  };

  return (
      <div className="App">
        {/*TODO: 갯수 넣기*/}
        <h1>찜한 상품({items.length})</h1>
        <table border="1" width ="500" height="300" align = "center">
          <thead>
          <tr>
            <th>상품</th>
            <th>상품 이름</th>
            <th>가격</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          {items.map((favorite) => (
              <tr key={favorite.productId}>
                <td>사진</td>
                <td>{favorite.productName}</td>
                <td>{favorite.price}원</td>
                <td>
                  <div>
                    <button onClick={() => removeProduct(favorite.productId)}>삭제</button>
                  </div>
                  <div>
                    <button>담기</button>
                  </div>
                </td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  );
}

export default Favorites;