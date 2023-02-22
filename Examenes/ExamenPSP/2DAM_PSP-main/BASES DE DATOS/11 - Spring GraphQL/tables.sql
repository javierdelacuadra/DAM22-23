create table personas
(
    id      int auto_increment
        primary key,
    name    varchar(30) null,
    surname varchar(20) null
);

create table login
(
    id_persona int          not null,
    username   varchar(25)  not null
        primary key,
    password   varchar(200) not null,
    role       varchar(15)  not null,
    constraint login__id_fk
        foreign key (id_persona) references personas (id)
);

create table mascotas
(
    id         int auto_increment
        primary key,
    name       varchar(30) not null,
    age        int         not null,
    id_persona int         not null,
    type       varchar(25) null,
    constraint mascotas_persona_id_fk
        foreign key (id_persona) references personas (id)
);

