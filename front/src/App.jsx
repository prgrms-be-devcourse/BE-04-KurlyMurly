import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {Home, SignUp} from './pages';
import Cart from "./pages/Cart";
import Favorites from "./pages/Favorites";
<<<<<<< HEAD
import Receipt from "./pages/Receipt";
=======
import Support from "./pages/Support";
>>>>>>> dcfa090 ([KM-185] 추가:1:1 문의 작성 페이지 구현)

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
