import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {Home, SignUp} from './pages';
import Cart from "./pages/Cart";
import Favorites from "./pages/Favorites";
import Receipt from "./pages/Receipt";

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/sign-up" element={<SignUp/>}/>
                <Route path="/cart" element={<Cart/>}/>
                <Route path="/favorites" element={<Favorites/>}/>
                <Route path="/receipt" element={<Receipt/>}/>
            </Routes>
        </BrowserRouter>
    );
};

export default App;
