import { BrowserRouter, Route, Routes } from 'react-router-dom';
import {
  Home,
  SignUp,
  Login,
  Cart,
  MyPage,
  Favorite,
  Receipt,
  Support,
  Products,
  Category,
} from './pages';

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/sign-up" element={<SignUp />} />
        <Route path="/login" element={<Login />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/my-page" element={<MyPage />} />
        <Route path="/favorites" element={<Favorite />} />
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
