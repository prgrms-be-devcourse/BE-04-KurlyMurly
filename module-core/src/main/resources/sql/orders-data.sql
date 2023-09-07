INSERT INTO orders(user_id, shipping_id, order_number, delivery_fee, total_price, payment, status)
VALUES (1, 1, '230903101234', 0, 42000, 'cache', 'PROCESSING'),
       (1, 1, '230910431224', 4000, 22000, 'cache', 'DELIVERING'),
       (1, 2, '230827330011', 0, 66500, 'cache', 'CANCELED'),
       (2, 1, '231112520234', 4000, 31700, 'cache', 'PROCESSING'),
       (3, 1, '221203100414', 0, 76240, 'cache', 'DELIVERED'),
       (4, 4, '230901271852', 4000, 17200, 'cache', 'DELIVERING');