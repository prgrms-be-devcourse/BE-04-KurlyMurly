import { useState } from 'react';

function Cart() {
    const [cart, setCart] = useState({
        cartId : '',
        isIncrease : ''
    });

    const [items, setItems] = useState([
        {cartId: 1, productId: 1, name: '상품 1', price: 10000, quantity: 1 },
        {cartId: 1, productId: 2, name: '상품 2', price: 15000, quantity: 1 },
        {cartId: 1, productId: 3, name: '상품 3', price: 20000, quantity: 1 },
    ]);

    const increaseQuantity = (cartId,productId) => {
        const updatedProducts = items.map((cart) => {
            if (cart.cartId === cartId && cart.productId === productId) {
                setCart(cartId,true);
                fetch("http://localhost:8080/users/carts",{
                    method : "PUT",
                    headers : {
                        "Content-Type":"application/json; charset=utf-8"
                    },
                    body: JSON.stringify(cart)
                })
                return { ...cart, quantity: cart.quantity + 1 };
            }
            return cart;
        });
        setItems(updatedProducts);
    };

    const decreaseQuantity = (cartId,productId) => {
        const updatedCarts = items.map((cart) => {
            if (cart.cartId === cartId && cart.productId === productId && cart.quantity > 1) {
                setCart(productId,false);
                fetch("http://localhost:8080/users/carts",{
                    method : "PUT",
                    headers : {
                        "Content-Type":"application/json; charset=utf-8"
                    },
                    body: JSON.stringify(cart)
                })
                return { ...cart, quantity: cart.quantity - 1 };
            }
            return cart;
        });
        setItems(updatedCarts);
    };

    return (
        <div className="App">
            <h1>상품 목록</h1>
            <table border="1" width ="500" height="300" align = "center">
                <thead>
                <tr>
                    <th>상품</th>
                    <th>가격</th>
                    <th>수량</th>
                    <th>총 가격</th>
                </tr>
                </thead>
                <tbody>
                {items.map((cart) => (
                    <tr key={cart.id}>
                        <td>{cart.name}</td>
                        <td>${cart.price}</td>
                        <td>
                            <button onClick={() => decreaseQuantity(cart.cartId,cart.productId)}>-</button>
                            {cart.quantity}
                            <button onClick={() => increaseQuantity(cart.cartId,cart.productId)}>+</button>
                        </td>
                        <td>${cart.price * cart.quantity}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default Cart;
