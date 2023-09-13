import { useEffect, useState } from 'react';
import { MyPage } from '../apis';

const ReviewList = () => {
  const [reviews, setReviews] = useState([]);
  const { loadAllMyReviews } = MyPage();

  useEffect(() => {
    loadAllMyReviews(setReviews);
  }, []);

  const dummies = [
    {
      productId: 1,
      productName: '[숭의가든] 소불고기',
      content: '맛있어요',
      createdAt: '2023.09.11',
      updatedAt: '2023.09.15',
      isSecret: '공개',
    },
    {
      productId: 2,
      productName: '국물 닭갈비 떡볶이',
      content: '맛있어요',
      createdAt: '2023.09.10',
      updatedAt: '2023.09.13',
      isSecret: '비공개',
    },
    {
      productId: 3,
      productName: '쌀 떡볶이',
      content: '맛있어요',
      createdAt: '2023.08.11',
      updatedAt: '2023.09.15',
      isSecret: '공개',
    },
  ];

  return (
    <div>
      <h3>작성한 후기</h3>
      <ul>
        {reviews.map((review) => (
          <li key={review.id}>
            <h3>상품이름: {review.productName}</h3>
            <table border="1" width="500" height="50">
              <tbody>
                <td>작성일: {review.createdAt}</td>
                <td>수정일: {review.updatedAt}</td>
                <td>
                  <button>
                    <b>{review.isSecret}</b>
                  </button>
                </td>
              </tbody>
            </table>
            <p>내용: {review.content}</p>
            <hr></hr>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ReviewList;
