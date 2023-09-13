import {useState} from 'react';

function Detail() {
    const [detail, setDetail] = useState({
        id: '',
        name: '',
        deliveryType: '',
        description: '',
        price: '',
        seller: '',
        storageType: '',
        saleUnit: '',
        weight: '',
        origin: '',
        expirationInformation: '',
        notification: '',
        likes: '',
        quantity: '',
    });

    const [items, setItems] = useState([
        {
            id: 1,
            name: '[제스프리] 뉴질랜드 골드키위 1.1kg (7~10입)',
            deliveryType: '샛별배송',
            description: '뉴질랜드에서 온 촉촉한 달콤함',
            price: 15900,
            seller: '컬리',
            storageType: '냉장',
            saleUnit: '1팩',
            weight: '1.1kg 내외',
            origin: '뉴질랜드',
            expirationInformation: '농산물이므로 별도의 유통기한은 없으나 가급적 빠르게 드시기 바랍니다.',
            notification: '수입 초기 원물로 너무 딱딱할 경우 실온에서 후숙 후 말랑해졌을 때 드시면 더욱 맛있게 즐기실 수 있습니다.',
            likes: 27930,
            quantity: 1,
        },
    ]);

    const [reviews, setReviews] = useState([
        {
            reviewId: 1,
            productId: 1,
            userName: '세한',
            content: '맛있어요',
            createdAt: '2023.09.11',
            updatedAt: '2023.09.15',
            like: 10,
            isSecret: "공개"
        },
        {
            reviewId: 2,
            productId: 1,
            userName: '수연',
            content: '맛있어요',
            createdAt: '2023.09.10',
            updatedAt: '2023.09.13',
            like: 12,
            isSecret: "비공개"
        },
        {
            reviewId: 3,
            productId: 1,
            userName: '히조',
            content: '맛있어요',
            createdAt: '2023.08.11',
            updatedAt: '2023.09.15',
            like: 3,
            isSecret: "공개"
        }
    ]);

    const [supports, setSupports] = useState([
        {
            supportId: 1,
            title: '해당 상품 배송이 얼마나 걸리나요?',
            content: '빨리 받아볼 수 있을까요??',
            userName: '장*연',
            createdAt: '2023-09-12',
            isAnswered: '답변대기',
            isSecret: false
        },
        {
            supportId: 2,
            title: '비건제품 인가요?',
            content: '동물 실험했나요??',
            userName: '문*조',
            createdAt: '2023-08-31',
            isAnswered: '답변완료',
            isSecret: false
        },
        {
            supportId: 3,
            title: '행사 기간이 언제까지 인가요??',
            content: '행사기간이 언제까지인지 알고싶습니다~!',
            userName: '오*한',
            createdAt: '2023-06-27',
            isAnswered: '답변완료',
            isSecret: false
        }
    ]);

    const increaseQuantity = (detailId) => {
        const updateDetail = items.map((detail) => {
            if (detail.id === detailId) {
                setDetail(detailId, true);
                return { ...detail, quantity: detail.quantity + 1 };
            }
            return detail;
        });
        setItems(updateDetail);
    };

    const decreaseQuantity = (detailId) => {
        const updatedProducts = items.map((detail) => {
            if (detail.id === detailId  && detail.quantity > 1) {
                setDetail(detailId, true);
                return { ...detail, quantity: detail.quantity - 1 };
            }
            return detail;
        });
        setItems(updatedProducts);
    };

    const increaseLike = (reviewId) => {
        const updateReview = reviews.map((reviews) => {
            if (reviews.reviewId === reviewId) {
                setReviews(reviewId, true);
                return { ...reviews, like: reviews.like + 1 };
            }
            return reviews;
        });
        setReviews(updateReview);
    };

    return (
        <div>
            <h1>Kurly-Murly</h1>
            {items.map((detail) => (
                <div key = {detail.id}>
                  <p><img src='' alt='상품 사진'/></p>
                  <p>{detail.deliveryType}</p>
                  <p><b>{detail.name}</b></p>
                  <p>{detail.description}</p>
                  <p>{detail.price}원</p>
                  <p>배송: {detail.deliveryType}</p>
                    <p>판매자: {detail.seller}</p>
                    <p>포장타입: {detail.storageType}</p>
                    <p>판매단위: {detail.saleUnit}</p>
                    <p>중량/용량: {detail.weight}</p>
                    <p>원산지: {detail.origin}</p>
                    <p>유통기한: {detail.expirationInformation}</p>
                    <p>안내사항: {detail.notification}</p>
                    <p>상품선택:
                        <button onClick={() => decreaseQuantity(detail.id)}>-</button>
                        &nbsp; {detail.quantity} &nbsp;
                        <button onClick={() => increaseQuantity(detail.id)}>+</button></p>
                    <p><button>찜</button></p>
                    <p><button>장바구니 담기</button></p>
                    <p><hr/></p>
        </div>
        ))}

            <div className='productInformation'>
                <h2>상품 설명</h2>
              <p><img src='' alt='상품 상세 설명'/></p>
            </div>
            <br/>

            <div className='moreInformation'>
            {items.map((detail) => (
                <div key={detail.id}>
                    <h2>상품고시정보</h2>
                    <table border="1">
                        <tr>
                            <td>품목 또는 명칭</td>
                            <td>{detail.name}</td>
                        </tr>

                        <tr>
                            <td>생산자, 수입폼의 경우 수입자를 함께 표기</td>
                            <td>{detail.seller}</td>
                        </tr>

                        <tr>
                            <td>제조연월일, 소비기한 또는 품질유지기한</td>
                            <td>상품설명 및 상품이미지 참조</td>
                        </tr>

                        <tr>
                            <td>축산물 - [축산법]에 따른 등급 표시(1++등급 국내산 쇠고기의 경우 [소.돼지 식육의 표시방법 및 부위 구분 기준]에 따라 근내지방도 정보를 포함하여 표시), [가축 및 축산물 이력관리에 관한 법률]에 따른 이력관리 대상 축산물 유무</td>
                            <td>상품설명 및 상품이미지 참조</td>
                        </tr>

                        <tr>
                            <td>상품구성</td>
                            <td>상품설명 및 상품이미지 참조</td>
                        </tr>

                        <tr>
                            <td>소비자 안전을 위한 주의사항 ([식품 등의 표시.광고에 관한 법률 시행규칙] 제5조 및 [발표 2]에 따른 표시사항을 말함)</td>
                            <td>상품설명 및 상품이미지 참조</td>
                        </tr>

                        <tr>
                            <td>포장단위별 내용물의 용량(중량), 수량, 크기</td>
                            <td>{detail.weight}</td>
                        </tr>

                        <tr>
                            <td>[농수산물의 원산지 표시 등에 관한 법률]에 따른 원산지</td>
                            <td>{detail.origin}</td>
                        </tr>

                        <tr>
                            <td>농수산물 - [농수산물 품질 관리법]에 따른 유전자변형 농수산물 표시, 지리적 표시</td>
                            <td>상품설명 및 상품이미지 참조</td>
                        </tr>

                        <tr>
                            <td>수입 농수축산물 - &quot;수입식품안전관리 특별법에 따른 수입신고를 필함&quot;의 문구</td>
                            <td>수입식품안전관리 특별법에 따른 수입신고를 필함</td>
                        </tr>

                        <tr>
                            <td>보관방법 또는 취급방법</td>
                            <td>상품설명 및 상품이미지 참조</td>
                        </tr>

                        <tr>
                            <td>소비자 상담 관련 전화번호</td>
                            <td>컬리멀리 고객행복센터(1234-1234)</td>
                        </tr>
                    </table>


                    <h2>판매자정보</h2>
                    <table border="1">
                        <tr>
                            <td>판매자</td>
                            <td>{detail.seller}</td>
                        </tr>
                    </table>
                </div>
                ))}
            </div>
            <br/>

            <div className='reviews'>
                {items.map((detail) => (
                    <div key={detail.id}>
                        <h2>상품 후기({detail.likes})</h2>
                    </div>
                    ))}
                <ul>
                    {reviews.map((review) => (
                        <li key={review.reviewId}>
                            <h3>작성자: {review.userName}</h3>
                            <p>{review.content}</p>
                            <table border="1" width ="500" height="100">
                                <tr>
                                    <td><img src='' alt='리뷰 이미지1'/></td>
                                    <td><img src='' alt='리뷰 이미지2'/></td>
                                    <td><img src='' alt='리뷰 이미지3'/></td>
                                </tr>
                            </table>
                            작성일: {review.createdAt} &nbsp;
                            <button onClick={() => increaseLike(review.reviewId)}>도움돼요 {review.like}</button>
                        </li>
                    ))}
                </ul>
            </div>
            <br/>

            <div className='supports'>
                <h2>상품 문의</h2>
                상품에 대한 문의를 남기는 공간입니다. 해당 게시판의 성격과 다른 글은 사전동의 없이 담당 게시판으로 이동될 수 있습니다.
                <p/>
                <div>
                    <table border='1'>
                        <tr>
                            <th>제목</th>
                            <th>내용</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>답변상태</th>
                        </tr>
                        <tbody>
                        {supports.map((support) => (
                        <tr key={support.supportId}>
                            <td>{support.title}</td>
                            <td>{support.content}</td>
                            <td>{support.userName}</td>
                            <td>{support.createdAt}</td>
                            <td>{support.isAnswered}</td>
                        </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}

export default Detail;