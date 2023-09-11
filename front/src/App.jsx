import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {Home, SignUp} from './pages';
import Cart from "./pages/Cart";
import Favorite from "./pages/Favorite";
import Category from "./pages/Category";
import Fruit from "./pages/categories/Fruit";
import NewProduct from "./pages/NewProduct";
import BestProduct from "./pages/BestProduct";
import Vegetable from "./pages/categories/Vegetable";
import SeaFood from "./pages/categories/SeaFood";
import Beaf from "./pages/categories/Beaf";
import MainDish from "./pages/categories/MainDish";
import Salad from "./pages/categories/Salad";
import Noodle from "./pages/categories/Noodle";
import Beverage from "./pages/categories/Beverage";
import Alcohol from "./pages/categories/Alcohol";

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/sign-up" element={<SignUp/>}/>
                <Route path="/cart" element={<Cart/>}/>
                <Route path="/favorites" element={<Favorite/>}/>
                <Route path="/new-products" element={<NewProduct/>}/>
                <Route path="/best-products" element={<BestProduct/>}/>
                <Route path="/categories" element={<Category/>}/>
                <Route path="/categories/1" element={<Vegetable/>}/>
                <Route path="/categories/2" element={<Fruit/>}/>
                <Route path="/categories/3" element={<SeaFood/>}/>
                <Route path="/categories/4" element={<Beaf/>}/>
                <Route path="/categories/5" element={<MainDish/>}/>
                <Route path="/categories/6" element={<Salad/>}/>
                <Route path="/categories/7" element={<Noodle/>}/>
                <Route path="/categories/8" element={<Beverage/>}/>
                <Route path="/categories/9" element={<Alcohol/>}/>
            </Routes>
        </BrowserRouter>
    );
};

export default App;
