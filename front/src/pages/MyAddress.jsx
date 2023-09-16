import { useState } from 'react';
import { NewShipping } from '../components';

const MyAddress = () => {
  const [newAddress, setNewAddress] = useState(false);

  return (
    <div>
      <h2>배송지 관리</h2>
      <h5>배송지에 따라 상품정보 및 배송 유형이 달라질 수 있습니다.</h5>
      <button onClick={() => setNewAddress(true)}>+ 새 배송지 추가</button>
      {newAddress ? <NewShipping /> : <></>}
    </div>
  );
};

export default MyAddress;
