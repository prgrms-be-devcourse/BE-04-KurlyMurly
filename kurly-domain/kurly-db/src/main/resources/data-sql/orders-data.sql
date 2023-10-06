INSERT INTO orders(user_id, shipping_id, order_number, delivery_fee, total_price, total_discount, actual_pay_amount,
                   payment, receiver, phone_number, address, receive_area, entrance_info, packaging, delivered_at, status)
VALUES (1, 0, '230903101234', 0, 42000, 0, 42000, 'cache', '', '', '', '', '', '', NOW(), 'PROCESSING'),
       (1, 0, '230910431224', 4000, 22000, 0, 22000, 'cache', '', '', '', '', '', '', DATE_SUB(NOW(), INTERVAL 1 WEEK), 'DELIVERED'),
       (1, 0, '230827330011', 0, 66500, 0, 66500, 'cache', '', '', '', '', '', '', NOW(), 'CANCELED'),
       (2, 0, '231112520234', 4000, 31700, 0, 31700, 'cache', '', '', '', '', '', '', NOW(), 'PROCESSING'),
       (3, 0, '221203100414', 0, 76240, 0, 76240, 'cache', '', '', '', '', '', '', NOW(), 'DELIVERED'),
       (4, 0, '230901271852', 4000, 17200, 0, 17200, 'cache', '', '', '', '', '', '', NOW(), 'DELIVERING');
