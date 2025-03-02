CREATE SEQUENCE customer_seq start with 100000 increment by 50;

CREATE TABLE customer
(
    id          bigint  not null primary key,
    first_name  varchar not null,
    last_name   varchar not null,
    street      varchar not null,
    postal_code varchar not null,
    city        varchar not null
);

CREATE SEQUENCE product_seq start with 100000 increment by 50;

CREATE TABLE product
(
    id    bigint  not null primary key,
    name  varchar not null,
    price double precision
);

CREATE SEQUENCE purchase_order_seq start with 100000 increment by 50;

CREATE TABLE purchase_order
(
    id          bigint    not null primary key,
    order_date  timestamp not null,

    customer_id bigint    not null references customer (id)
);

create index ux_purchase_order_customer_id on purchase_order (customer_id);


CREATE SEQUENCE order_item_seq start with 100000 increment by 50;

CREATE TABLE order_item
(
    id                bigint not null primary key,
    quantity          int    not null,

    purchase_order_id bigint not null references purchase_order (id),
    product_id        bigint not null references product (id)
);

create index ux_order_item_purchase_order_id on order_item (purchase_order_id);
create index ux_order_item_product_id on order_item (product_id);
