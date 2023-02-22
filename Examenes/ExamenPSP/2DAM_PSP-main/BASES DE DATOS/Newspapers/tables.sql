create table newspapers
(
    id             int auto_increment
        primary key,
    name_newspaper varchar(20) not null,
    release_date   date        not null,
    constraint constr_ID
        unique (name_newspaper, release_date),
    constraint name_newspaper
        unique (name_newspaper, release_date)
);

create table readers
(
    id            int auto_increment
        primary key,
    name_reader   varchar(30) not null,
    date_of_birth date        not null
);

create table login
(
    user      varchar(15) not null
        primary key,
    password  varchar(15) not null,
    id_reader int         not null,
    constraint login_readers_null_fk
        foreign key (id_reader) references readers (id)
);

create table login_oscar
(
    email               varchar(50)  not null
        primary key,
    password            varchar(200) not null,
    activated           tinyint(1)   not null,
    activation_token    varchar(100) not null,
    fecha_registro      datetime     not null,
    id_reader           int          not null,
    petitions_left      int          not null,
    time_first_petition datetime     not null,
    constraint login_oscar_readers_idreader_fk
        foreign key (id_reader) references readers (id)
)
    comment 'Login con pass hasheadas, de PSP';

create table roles
(
    id     int auto_increment
        primary key,
    nombre varchar(10) not null
);

create table reader_roles
(
    id        int auto_increment
        primary key,
    reader_id int not null,
    role_id   int not null,
    constraint reader_roles_pk
        unique (role_id, reader_id),
    constraint reader_roles_login_oscar_null_fk
        foreign key (reader_id) references readers (id),
    constraint reader_roles_roles_null_fk
        foreign key (role_id) references roles (id)
);

create table subscriptions
(
    id_reader         int  not null,
    id_newspaper      int  not null,
    signing_date      date not null,
    cancellation_date date null,
    primary key (id_reader, id_newspaper),
    constraint id_reader_subscribe_FK
        foreign key (id_reader) references readers (id),
    constraint id_subscribe_newspaper_FK
        foreign key (id_newspaper) references newspapers (id)
);

create table types
(
    id   int auto_increment
        primary key,
    name varchar(50) not null
);

create table articles
(
    id           int auto_increment
        primary key,
    name_article varchar(20) not null,
    id_type      int         not null,
    id_newspaper int         not null,
    description  varchar(50) null,
    constraint constr_ID
        unique (name_article, id_newspaper, id_type),
    constraint id_article_type_FK
        foreign key (id_type) references types (id),
    constraint id_newspaper_article_FK
        foreign key (id_newspaper) references newspapers (id)
);

create table readarticles
(
    id_reader  int not null,
    id_article int not null,
    rating     int not null,
    id         int auto_increment
        primary key,
    constraint id_reader
        unique (id_reader, id_article),
    constraint readarticles_articles_articles_fk
        foreign key (id_article) references articles (id),
    constraint readarticles_readers_null_fk
        foreign key (id_reader) references readers (id)
);

