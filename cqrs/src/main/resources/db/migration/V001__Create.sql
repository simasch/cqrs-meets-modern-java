CREATE SEQUENCE customer_seq start with 100000 increment by 50;

CREATE TABLE customer
(
    id          bigint  not null default nextval('customer_seq') primary key,
    first_name  varchar not null,
    last_name   varchar not null,
    street      varchar not null,
    postal_code varchar not null,
    city        varchar not null
);

CREATE SEQUENCE product_seq start with 100000 increment by 50;

CREATE TABLE product
(
    id    bigint  not null default nextval('product_seq') primary key,
    name  varchar not null,
    price double precision
);

CREATE SEQUENCE purchase_order_seq start with 100000 increment by 50;

CREATE TABLE purchase_order
(
    id          bigint    not null default nextval('purchase_order_seq') primary key,
    order_date  timestamp not null,

    customer_id bigint    not null references customer (id)
);

CREATE SEQUENCE order_item_seq start with 100000 increment by 50;

CREATE TABLE order_item
(
    id                bigint not null default nextval('order_item_seq') primary key,
    quantity          int    not null,

    purchase_order_id bigint not null references purchase_order (id),
    product_id        bigint not null references product (id)
);
