import React from 'react';

function ProductReview() {
    const reviews = [
        {
            productId: 1,
            productName: '[숭의가든] 소불고기',
            userName: '세한',
            userTier: '퍼플',
            content: '맛있어요',
            likes: '3',
            createdAt: '2023.09.11',
            isSecret: false
        },
        {
            productId: 2,
            productName: '국물 닭갈비 떡볶이',
            userName: '수연',
            userTier: '퍼플',
            content: '맛있어요',
            likes: '5',
            createdAt: '2023.09.11',
            isSecret: false
        },
        {
            productId: 3,
            productName: '쌀 떡볶이',
            userName: '히조',
            userTier: '퍼플',
            content: '맛있어요',
            likes: '11',
            createdAt: '2023.09.11',
            isSecret: false
        }
    ];

    const increaseLike = () => {
        fetch("http://localhost:8080/reviews/${reviewId}/likes",{
            method : "PATCH",
            headers : {
                "Content-Type":"application/json; charset=utf-8"
            }
        })
    }


    return (
        <div>
            <h1>상품 후기</h1>
            <ul>
                {reviews.map((review) => (
                    <li key={review.id}>
                        <h3>상품이름: {review.productName}</h3>
                        <p>내용: {review.content}</p>
                        <table border="1" width ="500" height="100">
                            <tr>
                                <td><img src='' alt='리뷰 이미지1'/></td>
                                <td><img src='' alt='리뷰 이미지2'/></td>
                                <td><img src='' alt='리뷰 이미지3'/></td>
                            </tr>
                        </table>
                        작성일: {review.createdAt}
                        <button onClick={() => increaseLike()}>도움돼요 : {review.likes}</button>
                        <hr></hr>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ProductReview;
