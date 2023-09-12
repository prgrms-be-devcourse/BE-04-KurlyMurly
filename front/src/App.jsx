import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {Home, SignUp, Login, Cart, Favorite, Receipt, Support, Products, Category} from './pages';
import CreateReview from "./pages/CreateReview";
import ReviewList from "./pages/UserReview";
import ProductReview from "./pages/ProductReview";

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/sign-up" element={<SignUp/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/cart" element={<Cart/>}/>
                <Route path="/favorites" element={<Favorite/>}/>
                <Route path="/new-products" element={<Products/>}/>
                <Route path="/best-products" element={<Products/>}/>
                <Route path="/favorites" element={<Favorite/>}/>
                <Route path="/receipt" element={<Receipt/>}/>
                <Route path="/categories" element={<Category/>}/>
                <Route path="/support" element={<Support/>}/>
                <Route path="/create-review" element={<CreateReview/>}/>
                <Route path="/review-list" element={<ReviewList/>}/>
                <Route path="/product-review" element={<ProductReview/>}/>
            </Routes>
        </BrowserRouter>
    );
};

export default App;
