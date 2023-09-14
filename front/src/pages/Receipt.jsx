import React, {useState} from 'react';
import axios from "axios";

const Receipt = () => {

  const [orders, setOrders] = useState([
    {
      cartId: 1,
      productId: 1,
      name: '상품 1',
      price: 10000,
      quantity: 1,
      receiver: "오세한",
      phoneNumber: "01094828438",
      email: "kurly@murly.com",
      address: "서울특별시"
    },
    {
      cartId: 2,
      productId: 2,
      name: '상품 2',
      price: 15000,
      quantity: 2,
      receiver: "오세한",
      phoneNumber: "01094828438",
      email: "kurly@murly.com",
      address: "서울특별시"
    },
    {
      cartId: 3,
      productId: 3,
      name: '상품 3',
      price: 20000,
      quantity: 4,
      receiver: "오세한",
      phoneNumber: "01094828438",
      email: "kurly@murly.com",
      address: "서울특별시"
    },
  ]);

  const addTotalPrice = () => {
    let totalPrice = 0;

    orders.map((order) => {
      totalPrice += order.price * order.quantity;
    });

    return totalPrice;
  };

  const createOrder = () => {

    orders.map((order) => {
      // 여기에 주문 상품 리스트들을 담고 싶습니다 ..
    });

    axios({
      url: "http://localhost:8080/orders",
      method: 'POST',
      data: {
        productList: [],
        total: addTotalPrice(),
        discount: orders[0].name,
        receiver: orders[0].name,
        contact: orders[0].contact,
        address: orders[0].address,
        place: "문앞",
        password: "0122",
        packaging: "박스포장"
      }
    })
  };

  return (
      <div>
        <h1>주문서</h1>
        <table border="1" width="500" height="100">
          <thead>
          <tr>
            <th>주문 상품</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>{orders[0].name} 외 {orders.length - 1}개 상품을 주문하였습니다.</td>
          </tr>
          </tbody>
        </table>
        <p><b>주문자 정보</b></p>
        <table border="1" width="500" height="100">
          <thead>
          <tr>
            <th>보내는 분</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>이름 : {orders[0].receiver}</td>
          </tr>
          <tr>
            <td>연락처 : {orders[0].phoneNumber}</td>
          </tr>
          <tr>
            <td>이메일 주소 : {orders[0].email} </td>
          </tr>
          </tbody>
        </table>
        <p><b>배송 정보</b></p>
        <table border="1" width="500" height="100">
          <thead>
          <tr>
            <th>배송 정보</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>{orders[0].address}</td>
          </tr>
          </tbody>
        </table>
        <span>총 가격:</span>
        <span><b>{addTotalPrice()}원</b></span>
        <p></p>
        <button onClick={() => createOrder()}>주문</button>
      </div>
  )
}

export default Receipt;
