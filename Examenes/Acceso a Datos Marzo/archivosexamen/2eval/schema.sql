create table clients
(
    id     int auto_increment
        primary key,
    name varchar(200) not null,
    balance  double       not null
);

INSERT INTO clients (id, name, balance) VALUES (1, 'John', 10000);
INSERT INTO clients (id, name, balance) VALUES (2, 'Sarah', 200);
INSERT INTO clients (id, name, balance) VALUES (3, 'Anne', 123);

create table items
(
    id     int auto_increment
        primary key,
    name varchar(200) not null,
    price double       not null
);

INSERT INTO items (id, name, price) VALUES (1, 'milk', 2);
INSERT INTO items (id, name, price) VALUES (2, 'bread', 1);
INSERT INTO items (id, name, price) VALUES (3, 'chocolate', 4);
INSERT INTO items (id, name, price) VALUES (4, 'tomato', 7);
INSERT INTO items (id, name, price) VALUES (5, 'fish', 25.98);

create table purchases
(
    id          int auto_increment
        primary key,
    id_client  int                  not null,
    p_date       date                 null,
    total_cost double               null,
    paid      tinyint(1) default 0 not null,
    constraint purchases_clients_id_fk
        foreign key (id_client) references clients (id)
);

INSERT INTO purchases (id, id_client, p_date, total_cost, paid) VALUES (2, 1, '2021-03-13', 204, 0);
INSERT INTO purchases (id, id_client, p_date, total_cost, paid) VALUES (3, 2, '2021-03-11', 10, 0);
INSERT INTO purchases (id, id_client, p_date, total_cost, paid) VALUES (4, 3, '2021-03-11', 2, 0);

create table purchases_items
(
    id          int auto_increment
        primary key,
    id_purchase   int not null,
    id_item int not null,
    amount    int null,
    constraint purchases_items_purchases_id_fk
        foreign key (id_purchase) references purchases (id),
    constraint purchases_items_items_id_fk
        foreign key (id_item) references items (id)
);

INSERT INTO purchases_items (id, id_purchase, id_item, amount) VALUES (1, 2, 2, 1);
INSERT INTO purchases_items (id, id_purchase, id_item, amount) VALUES (2, 2, 3, 2);
INSERT INTO purchases_items (id, id_purchase, id_item, amount) VALUES (3, 3, 1, 4);
INSERT INTO purchases_items (id, id_purchase, id_item, amount) VALUES (4, 3, 3, 3);
INSERT INTO purchases_items (id, id_purchase, id_item, amount) VALUES (5, 4, 1, 2);