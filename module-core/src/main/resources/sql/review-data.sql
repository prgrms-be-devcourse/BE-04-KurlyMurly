INSERT INTO reviews(user_id, product_id, order_id, content, status)
VALUES (1, 1, 1, '해당 물건은 너무 내구성이 좋아요!!', 'NORMAL'),
       (2, 1, 4, '너무 맛이 없어서 짜증나요!! 욕, 욕', 'BANNED'),
       (3, 2, 1, '정말 정성스러운 리뷰. 정말 놀라운 리뷰!!', 'BEST'),
       (1, 2, 4, '사용자 요청으로 삭제된 리뷰', 'DELETED'),
       (1, 2, 2, '그냥 저냥 평범한 맛이네요.', 'NORMAL');