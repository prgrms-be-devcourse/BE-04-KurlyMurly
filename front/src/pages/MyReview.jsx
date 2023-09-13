import { useState } from 'react';
import { CreateReview, ReviewList } from '../components';

const MyReview = () => {
  const [isReview, setIsReview] = useState(false);

  return (
    <>
      <h1>내 리뷰</h1>
      <div>
        <input
          name="reiew"
          value={false}
          onClick={() => {
            setIsReview(false);
          }}
          type="radio"
        />
        <label>작성 가능한 리뷰</label>
        <input
          name="reiew"
          value={true}
          onClick={() => {
            setIsReview(true);
          }}
          type="radio"
        />
        <label>작성한 리뷰</label>
      </div>
      {isReview ? <ReviewList /> : <CreateReview />}
    </>
  );
};

export default MyReview;
