import React, {useState} from 'react';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

function Cart() {
  const navigate = useNavigate();
  const [items, setItems] = useState([
    {cartId: 1, productId: 1, name: '상품 1', price: 10000, quantity: 1},
    {cartId: 2, productId: 2, name: '상품 2', price: 15000, quantity: 1},
    {cartId: 3, productId: 3, name: '상품 3', price: 20000, quantity: 1},
  ]);

  const increaseQuantity = (cartId) => {
    axios({
      url: "http://localhost:8080/users/carts",
      method: 'put',
      data: {
        cartId: cartId,
        isIncrease: true
      }
    })
  };

  const decreaseQuantity = (cartId) => {
    axios({
      url: "http://localhost:8080/users/carts",
      method: 'put',
      data: {
        cartId: cartId,
        isIncrease: false
      }
    })
  };

  const removeProduct = (cartId) => {
    axios({
      url: "http://localhost:8080/users/carts/${cartId}",
      method: 'delete'
    })
  };

  return (
      <div className="App">
        <h1>상품 목록</h1>
        <table border="1" width="500" height="300" align="center">
          <thead>
          <tr>
            <th>상품</th>
            <th>가격</th>
            <th>수량</th>
            <th>총 가격</th>
          </tr>
          </thead>
          <tbody>
          {items.map((cart) => (
              <tr key={cart.id}>
                <td>{cart.name}</td>
                <td>${cart.price}</td>
                <td>
                  <button onClick={() => decreaseQuantity(cart.cartId, cart.productId)}>-</button>
                  {cart.quantity}
                  <button onClick={() => increaseQuantity(cart.cartId, cart.productId)}>+</button>
                  <button onClick={() => removeProduct(cart.cartId)}><b>x</b></button>
                </td>
                <td>${cart.price * cart.quantity}</td>
              </tr>
          ))}
          </tbody>
          <button onClick={() => navigate("/receipt")}><b>주문</b></button>
        </table>

      </div>
  );
}

export default Cart;
