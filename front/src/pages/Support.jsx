import React, { useState } from 'react';

function Support() {
    const [supportType, setSupportType] = useState('');
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const [support, setSupport] = useState({
        orderId : '',
        orderNumber : '',
        type : '',
        title : '',
        content : ''
    });

    const handleSubmit = (e) => {
        e.preventDefault();

        // fetch("http://localhost:8080/orders",{
        //         method : "POST",
        //         headers : {
        //             "Content-Type":"application/json; charset=utf-8"
        //         },
        //         body: JSON.stringify()
        //     })
    };

    return (
        <div>
            <h1>1:1 문의 </h1>
            <hr></hr>
            <br></br>
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <legend>문의 유형 선택</legend>
                    <div>
                        <select
                            value={supportType}
                            onChange={(e) => setSupportType(e.target.value)}
                        >
                            <option value="문의 유형">문의 유형을 선택해주세요</option>
                            <option value="주문/결제/반품/교환 문의">주문/결제/반품/교환 문의</option>
                            <option value="이벤트/쿠폰/적립금 문의">이벤트/쿠폰/적립금 문의</option>
                            <option value="상품 문의">상품 문의</option>
                            <option value="배송 문의">배송 문의</option>
                            <option value="상품 누락 문의">상품 누락 문의</option>
                            <option value="기타 문의">기타문의</option>
                        </select>
                    </div>
                </fieldset><br></br>
                <fieldset>
                    <div>
                        <label><b>문의 제목</b></label><br></br>
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                    </div>
                </fieldset><br></br>
                <fieldset>
                    <div>
                        <label><b>문의 내용</b></label><br></br>
                        <textarea
                            cols="40" rows="3"
                            value={content}
                            onChange={(e) => setContent(e.target.value)}
                            required
                        />
                    </div>
                </fieldset><br></br>
                <button type="submit">등록</button>
            </form>
        </div>
    );
}

export default Support;
