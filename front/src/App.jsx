import { BrowserRouter, Route, Routes } from 'react-router-dom';
import {
  Home,
  SignUp,
  Login,
  Cart,
  MyPage,
  Favorite,
  Receipt,
  MyReview,
  Support,
  Products,
  Category,
  MyInfo,
  MyAddress,
} from './pages';

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/login" element={<Login />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/mypage/review" element={<MyReview />} />
        <Route path="/mypage/info/modify" element={<MyInfo />} />
        <Route path="/mypage/pick/list" element={<Favorite />} />
        <Route path="/mypage/address" element={<MyAddress />} />
        <Route path="/new-products" element={<Products />} />
        <Route path="/best-products" element={<Products />} />
        <Route path="/favorites" element={<Favorite />} />
        <Route path="/receipt" element={<Receipt />} />
        <Route path="/categories" element={<Category />} />
        <Route path="/support" element={<Support />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
