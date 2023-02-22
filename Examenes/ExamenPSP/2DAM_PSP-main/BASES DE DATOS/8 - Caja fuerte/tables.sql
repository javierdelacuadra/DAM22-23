create table user
(
    name     varchar(10)  not null
        primary key,
    password varchar(200) not null,
    role     varchar(5)   not null,
    check (`role` in ('ADMIN','USER'))
);

create table folder
(
    id          int auto_increment
        primary key,
    name        varchar(20)  not null,
    password    varchar(200) not null,
    `read_only` int          not null,
    name_user   varchar(20)  not null,
    constraint folder_pk
        unique (name, name_user),
    constraint folder___fk
        foreign key (name_user) references user (name)
);

create table message
(
    id        int auto_increment
        primary key,
    message   varchar(10000) not null,
    id_folder int            not null,
    constraint message_folder_id_fk
        foreign key (id_folder) references folder (id)
);

