import JwtInterceptor from './ApiController';

const User = () => {
  const { instance } = JwtInterceptor();

  const loadAllReviewables = async () => {
    try {
      return await instance.get(`/users/reviews`);
    } catch (error) {
      console.error(error);
    }
  };

  const loadAllMyReviews = async (setReviews) => {
    try {
      const res = await instance.get(`/reviews`);
      return res.data;
    } catch (error) {
      console.error(error);
    }
  };

  return {
    loadAllReviewables,
    loadAllMyReviews,
  };
};

export default User;
