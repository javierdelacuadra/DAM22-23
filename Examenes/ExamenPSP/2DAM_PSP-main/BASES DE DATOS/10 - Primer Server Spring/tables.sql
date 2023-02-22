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

