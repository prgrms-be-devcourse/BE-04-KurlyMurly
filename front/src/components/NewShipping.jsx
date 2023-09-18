import { useState } from 'react';

const SubAddress = ({ isExpress, mainAddress, subAddress, onChange, addNewShipping }) => {
  return (
    <div>
      <h3>{isExpress ? '샛별배송 지역입니다!' : '택배배송 지역입니다!'}</h3>
      {mainAddress + ' '}
      <input
        name="subAddress"
        value={subAddress}
        onChange={onChange}
        type="text"
        placeholder="나머지 주소를 입력해 주세요"
      />
      <div>
        <input name="isDefault" value={true} type="radio" onChange={onChange} />
        <label>기본 배송지로 저장</label>
      </div>
      <div>
        <button onClick={addNewShipping}>저장</button>
      </div>
    </div>
  );
};

const NewShipping = () => {
  const expressAddressPattern = /^[서울|경기|인천|충청|대구|부산|울산|양산|창원|김해].*/;
  const [isSearched, setIsSearched] = useState(false);
  const [isExpress, setIsExpress] = useState(false);
  const [shippingInputs, setShippingInputs] = useState({
    mainAddress: '',
    subAddress: '',
    isDefault: false,
  });

  const { mainAddress, subAddress } = shippingInputs;

  const onChange = (e) => {
    const { name, value } = e.target;
    setShippingInputs((inputs) => ({
      ...inputs,
      [name]: value,
    }));
  };

  const checkExpressAddress = () => {
    setIsExpress(expressAddressPattern.test(mainAddress));
    setIsSearched(true);
  };

  const addNewShipping = () => {
    console.log(shippingInputs);
  };

  return (
    <div>
      <h5 hidden={isSearched}>새로 추가할 배송지를 입력해주세요.</h5>
      <input
        hidden={isSearched}
        name="mainAddress"
        value={mainAddress}
        type="text"
        onChange={onChange}
      />
      <button hidden={isSearched} onClick={checkExpressAddress}>
        검색
      </button>
      {isSearched ? (
        <SubAddress
          isExpress={isExpress}
          mainAddress={mainAddress}
          subAddress={subAddress}
          onChange={onChange}
          addNewShipping={addNewShipping}
        />
      ) : (
        <></>
      )}
    </div>
  );
};

export default NewShipping;
