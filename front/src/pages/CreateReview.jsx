import React, { useState } from 'react';

function CreateReview() {
    const [product, setProduct] = useState({
        productId: 1, productName: "[숭의가든] 소날 옛 불고기"},
    )

    const [content, setContent] = useState('');

    const [review, setReview] = useState({
        productId : '',
        productName : '',
        content : '',
        isSecret : ''
    });

    const handleSubmit = (e) => {
        e.preventDefault();

        setReview(product.productId,product.productName,content,false);
    };

    return (
        <div>
            <h1>리뷰 작성</h1>
            <hr></hr>
            <br></br>
            <form onSubmit={handleSubmit}>
                <fieldset>
                    <img src='' alt='상품 이미지'/>
                    <p>{product.productName}</p>
                    <label><b>리뷰 내용</b></label><br></br>
                    <textarea
                        cols="40" rows="3"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                    />
                </fieldset><br></br>
                <button type="submit">등록</button>
            </form>
        </div>
    );
}

export default CreateReview;
