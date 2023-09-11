import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {Home, SignUp} from './pages';
import Cart from "./pages/Cart";
import Favorites from "./pages/Favorites";

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/sign-up" element={<SignUp/>}/>
                <Route path="/cart" element={<Cart/>}/>
<<<<<<< HEAD
                <Route path="/favorites" element={<Favorites/>}/>
=======
                <Route path="/receipt" element={<Receipt/>}/>
>>>>>>> cf8e9c5 ([KM-183] 추가:주문서 페이지 구현)
            </Routes>
        </BrowserRouter>
    );
};

export default App;
