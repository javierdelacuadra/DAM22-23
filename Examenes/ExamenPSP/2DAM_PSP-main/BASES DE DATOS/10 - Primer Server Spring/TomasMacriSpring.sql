create table newspapers
(
    id           int auto_increment
        primary key,
    name         varchar(25) not null,
    release_date date        not null
);

create table articles
(
    id      int auto_increment
        primary key,
    name    varchar(30) not null,
    type    varchar(15) null,
    id_news int         not null,
    constraint articles_newspapers_id_fk
        foreign key (id_news) references newspapers (id)
);

INSERT INTO TomasMacriSpring.newspapers (id, name, release_date) VALUES (1, 'news1', '2023-02-08');
INSERT INTO TomasMacriSpring.newspapers (id, name, release_date) VALUES (4, 'string', '2023-02-08');
INSERT INTO TomasMacriSpring.newspapers (id, name, release_date) VALUES (5, 'POST', '2023-02-08');
INSERT INTO TomasMacriSpring.newspapers (id, name, release_date) VALUES (6, 'otro', '2023-02-08');
INSERT INTO TomasMacriSpring.newspapers (id, name, release_date) VALUES (7, 'basta', '2023-02-08');
INSERT INTO TomasMacriSpring.newspapers (id, name, release_date) VALUES (9, 'eeeee', '2023-08-08');

INSERT INTO TomasMacriSpring.articles (id, name, type, id_news) VALUES (2, 'string', 'string', 5);
INSERT INTO TomasMacriSpring.articles (id, name, type, id_news) VALUES (3, ':D', 'D:', 6);
INSERT INTO TomasMacriSpring.articles (id, name, type, id_news) VALUES (4, 'SI?', 'SIIIIII', 7);
INSERT INTO TomasMacriSpring.articles (id, name, type, id_news) VALUES (6, 'kufkeee', 'EEEE', 5);
