import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {Home, SignUp, Login, Cart, Favorites, Receipt} from './pages';

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/sign-up" element={<SignUp/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/cart" element={<Cart/>}/>
                <Route path="/favorites" element={<Favorite/>}/>
                <Route path="/new-products" element={<NewProduct/>}/>
                <Route path="/best-products" element={<BestProduct/>}/>
                <Route path="/favorites" element={<Favorites/>}/>
                <Route path="/receipt" element={<Receipt/>}/>
                <Route path="/support" element={<Support/>}/>
            </Routes>
        </BrowserRouter>
    );
};

export default App;
