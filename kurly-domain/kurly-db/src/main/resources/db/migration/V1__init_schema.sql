CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(10)  NOT NULL,
    login_id     VARCHAR(50)  NOT NULL,
    password     VARCHAR(255) NOT NULL,
    tier         VARCHAR(15)  NOT NULL,
    reward       INTEGER      NOT NULL,
    email        VARCHAR(50)  NOT NULL,
    sex          VARCHAR(10)  NOT NULL,
    birth        DATE         NOT NULL,
    pay_password VARCHAR(255),
    recommender  VARCHAR(50),
    phone_number VARCHAR(15),
    role         VARCHAR(10),
    status       VARCHAR(10)  NOT NULL,
    created_at   DATETIME(6),
    updated_at   DATETIME(6)
);

CREATE TABLE IF NOT EXISTS carts
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity   INT    NOT NULL,
    created_at DATETIME(6),
    updated_at DATETIME(6)
);

CREATE TABLE IF NOT EXISTS shippings
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id            BIGINT       NOT NULL,
    road_address       VARCHAR(100) NOT NULL,
    description        VARCHAR(100),
    is_express         BOOLEAN      NOT NULL,
    receiver           VARCHAR(30)  NOT NULL,
    contact            VARCHAR(15)  NOT NULL,
    receive_area       VARCHAR(15),
    entrance_password  VARCHAR(10),
    message_alert_time VARCHAR(20),
    is_default         BOOLEAN      NOT NULL,
    created_at         DATETIME(6),
    updated_at         DATETIME(6)
);

CREATE TABLE IF NOT EXISTS payments
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id      BIGINT      NOT NULL,
    pay_info     VARCHAR(20) NOT NULL,
    type         VARCHAR(15) NOT NULL,
    password     VARCHAR(20),
    expired_date DATETIME(6),
    status       VARCHAR(10) NOT NULL,
    created_at   DATETIME(6),
    updated_at   DATETIME(6)
);

CREATE TABLE IF NOT EXISTS categories
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(20) NOT NULL,
    sub_category VARCHAR(20) NOT NULL,
    created_at   DATETIME(6),
    updated_at   DATETIME(6)
);

CREATE TABLE IF NOT EXISTS products
(
    id                     BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id            BIGINT       NOT NULL,
    name                   VARCHAR(50)  NOT NULL,
    description            VARCHAR(100) NOT NULL,
    price                  INT          NOT NULL,
    delivery               VARCHAR(15)  NOT NULL,
    image_url              VARCHAR(50)  NOT NULL,
    seller                 VARCHAR(20)  NOT NULL,
    storage_type           VARCHAR(15)  NOT NULL,
    sale_unit              VARCHAR(20)  NOT NULL,
    weight                 VARCHAR(20)  NOT NULL,
    origin                 VARCHAR(50)  NOT NULL,
    allergy_info           VARCHAR(255) NOT NULL,
    expiration_information VARCHAR(50)  NOT NULL,
    status                 VARCHAR(15)  NOT NULL,
    is_kurly_only          BOOLEAN      NOT NULL,
    created_at             DATETIME(6),
    updated_at             DATETIME(6)
);

CREATE TABLE IF NOT EXISTS product_supports
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id      BIGINT       NOT NULL,
    product_id   BIGINT       NOT NULL,
    product_name VARCHAR(50)  NOT NULL,
    title        VARCHAR(100) NOT NULL,
    content      TEXT         NOT NULL,
    is_secret    BOOLEAN      NOT NULL,
    status       VARCHAR(15)  NOT NULL,
    created_at   DATETIME(6),
    updated_at   DATETIME(6)
);

CREATE TABLE IF NOT EXISTS favorites
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id    BIGINT  NOT NULL,
    product_id BIGINT  NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    created_at DATETIME(6),
    updated_at DATETIME(6),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id      BIGINT      NOT NULL,
    product_id   BIGINT      NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    content      TEXT        NOT NULL,
    likes        INT         NOT NULL DEFAULT 0,
    status       VARCHAR(15) NOT NULL,
    is_secret    BOOLEAN     NOT NULL,
    created_at   DATETIME(6),
    updated_at   DATETIME(6)
);

CREATE TABLE IF NOT EXISTS review_likes
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    like_user_id BIGINT  NOT NULL,
    review_id    BIGINT  NOT NULL,
    is_deleted   BOOLEAN NOT NULL,
    created_at   DATETIME(6),
    updated_at   DATETIME(6)
);


CREATE TABLE IF NOT EXISTS orders
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id           BIGINT              NOT NULL,
    order_number      VARCHAR(255) UNIQUE NOT NULL,
    delivery_fee      INT                 NOT NULL,
    total_price       INT                 NOT NULL,
    total_discount    INT                 NOT NULL,
    actual_pay_amount INT                 NOT NULL,
    payment           VARCHAR(10)         NOT NULL,
    receiver          VARCHAR(10)         NOT NULL,
    phone_number      VARCHAR(30)         NOT NULL,
    address           VARCHAR(50)         NOT NULL,
    receive_area      VARCHAR(15)         NOT NULL,
    entrance_info     VARCHAR(30)         NOT NULL,
    packaging         VARCHAR(10)         NOT NULL,
    delivered_at      DATETIME(6),
    status            VARCHAR(15)         NOT NULL,
    created_at        DATETIME(6),
    updated_at        DATETIME(6)
);

CREATE TABLE IF NOT EXISTS order_lines
(
    order_id     BIGINT       NOT NULL,
    product_id   BIGINT       NOT NULL,
    line_index   INT          NOT NULL,
    product_name VARCHAR(50)  NOT NULL,
    image_url    VARCHAR(255) NOT NULL,
    total_price  INT          NOT NULL,
    quantity     INT          NOT NULL,
    is_reviewed  BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS order_supports
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    type           VARCHAR(15)         NOT NULL,
    user_id        BIGINT              NOT NULL,
    order_id       BIGINT              NOT NULL,
    order_number   VARCHAR(255) UNIQUE NOT NULL,
    title          VARCHAR(30)         NOT NULL,
    content        TEXT                NOT NULL,
    answer_content TEXT,
    status         VARCHAR(15)         NOT NULL,
    created_at     DATETIME(6),
    updated_at     DATETIME(6)
);
