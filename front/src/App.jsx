import {BrowserRouter, Route, Routes} from 'react-router-dom';
import {Home, SignUp} from './pages';
import Cart from "./pages/Cart";

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/sign-up" element={<SignUp/>}/>
                <Route path="/Cart" element={<Cart/>}/>
            </Routes>
        </BrowserRouter>
    );
};

export default App;
