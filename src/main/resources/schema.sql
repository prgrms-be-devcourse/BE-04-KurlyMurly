DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_supports;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS review_likes;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_supports;

CREATE TABLE categories
(
    category_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(20) NOT NULL,
    sub_category VARCHAR(20) NOT NULL,
    created_at   TIMESTAMP(6),
    updated_at   TIMESTAMP(6)
);

CREATE TABLE products
(
    product_id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id            BIGINT       NOT NULL,
    name                   VARCHAR(50)  NOT NULL,
    description            VARCHAR(100) NOT NULL,
    price                  INT          NOT NULL,
    delivery               VARCHAR(15)  NOT NULL,
    seller                 VARCHAR(20)  NOT NULL,
    storage_type           VARCHAR(15)  NOT NULL,
    sale_unit              VARCHAR(20)  NOT NULL,
    weight                 VARCHAR(20)  NOT NULL,
    origin                 VARCHAR(50)  NOT NULL,
    allergy_info           VARCHAR(255) NOT NULL,
    expiration_information VARCHAR(50)  NOT NULL,
    status                 VARCHAR(15)  NOT NULL,
    is_kurly_only          BOOLEAN      NOT NULL,
    created_at             TIMESTAMP(6),
    updated_at             TIMESTAMP(6)
);

CREATE TABLE product_supports
(
    product_support_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id            BIGINT       NOT NULL,
    product_id         BIGINT       NOT NULL,
    title              VARCHAR(100) NOT NULL,
    content            TEXT         NOT NULL,
    is_secret          BOOLEAN      NOT NULL,
    status             VARCHAR(15)  NOT NULL,
    created_at         TIMESTAMP(6),
    updated_at         TIMESTAMP(6)
);

CREATE TABLE favorites
(
    favorite_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id     BIGINT      NOT NULL,
    product_id  BIGINT      NOT NULL,
    status      VARCHAR(15) NOT NULL,
    created_at  TIMESTAMP(6),
    updated_at  TIMESTAMP(6),
    FOREIGN KEY (product_id) REFERENCES products (product_id)
);

CREATE TABLE orders
(
    order_id     BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id      BIGINT              NOT NULL,
    shipping_id  BIGINT              NOT NULL,
    order_number VARCHAR(255) UNIQUE NOT NULL,
    delivery_fee INT                 NOT NULL,
    total_price  INT                 NOT NULL,
    payment      VARCHAR(10)         NOT NULL,
    status       VARCHAR(15)         NOT NULL,
    created_at   DATETIME(6),
    updated_at   DATETIME(6)
);

CREATE TABLE order_supports
(
    order_supports_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type              VARCHAR(15)         NOT NULL,
    user_id           BIGINT              NOT NULL,
    order_id          BIGINT              NOT NULL,
    order_number      VARCHAR(255) UNIQUE NOT NULL,
    title             VARCHAR(30)         NOT NULL,
    content           TEXT                NOT NULL,
    status            VARCHAR(15)         NOT NULL,
    created_at        DATETIME(6),
    updated_at        DATETIME(6)
);

CREATE TABLE reviews
(
    review_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT      NOT NULL,
    product_id BIGINT      NOT NULL,
    order_id   BIGINT      NOT NULL,
    likes      INT         NOT NULL DEFAULT 0,
    content    TEXT        NOT NULL,
    status     VARCHAR(15) NOT NULL,
    created_at DATETIME             DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME             DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review_likes
(
    review_like_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    like_user_id   BIGINT NOT NULL,
    review_id      BIGINT NOT NULL,
    is_deleted     BOOLEAN NOT NULL,
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);
