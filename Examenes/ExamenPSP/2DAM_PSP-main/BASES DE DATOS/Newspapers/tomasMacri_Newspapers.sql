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

INSERT INTO tomasMacri_Newspapers.newspapers (id, name_newspaper, release_date) VALUES (22, 'OSCAR NEWS!', '2011-12-01');
INSERT INTO tomasMacri_Newspapers.newspapers (id, name_newspaper, release_date) VALUES (4, 'Sky News', '2002-12-12');
INSERT INTO tomasMacri_Newspapers.newspapers (id, name_newspaper, release_date) VALUES (2, 'The Guardian', '2000-03-15');
INSERT INTO tomasMacri_Newspapers.newspapers (id, name_newspaper, release_date) VALUES (3, 'The New York Times', '2001-11-09');

INSERT INTO tomasMacri_Newspapers.types (id, name) VALUES (1, 'Sports');
INSERT INTO tomasMacri_Newspapers.types (id, name) VALUES (2, 'Politics');
INSERT INTO tomasMacri_Newspapers.types (id, name) VALUES (3, 'Science');

INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (2, 'art2', 2, 2, 'Desc art2');
INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (3, 'art3', 3, 2, 'Desc art3');
INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (4, 'art4', 1, 3, 'Desc art 4');
INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (6, 'art6', 1, 4, 'Desc art6');
INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (14, 'art11', 3, 2, '');
INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (27, 'new article', 3, 2, 'desc new article');
INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (29, 'art14', 2, 2, 'desc art14');
INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (40, 'other art', 3, 4, 'desc');
INSERT INTO tomasMacri_Newspapers.articles (id, name_article, id_type, id_newspaper, description) VALUES (41, 'other art', 3, 3, 'desc');

INSERT INTO tomasMacri_Newspapers.roles (id, nombre) VALUES (1, 'admin');
INSERT INTO tomasMacri_Newspapers.roles (id, nombre) VALUES (2, 'reader');

INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (-1, 'ADMIN', '1967-11-04');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (1, 'Aaron', '1970-01-09');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (2, 'Brian', '1985-09-18');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (3, 'Charles', '2014-12-14');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (16, 'Gustavo', '2014-05-17');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (17, 'Frank', '2014-05-17');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (19, 'Oscar', '2014-05-17');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (20, 'Matame', '2014-05-08');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (23, 'prueba', '2014-11-13');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (68, 'eme', '2022-12-01');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (69, 'r69', '2014-12-18');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (76, 'ssss', '2001-01-01');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (77, 'dddd', '2001-01-01');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (78, 'kalet69096@dmonies.com', '0001-01-01');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (79, 'oscar', '2017-12-07');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (80, 'weddddd', '2015-12-11');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (81, 'trtttt', '2010-12-14');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (84, 'eeee', '2016-12-10');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (97, 'sdsdsw', '2018-12-13');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (104, 'eeee', '2018-12-19');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (105, 'ewefwss', '2019-12-13');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (106, 'eewkk', '2020-12-24');
INSERT INTO tomasMacri_Newspapers.readers (id, name_reader, date_of_birth) VALUES (117, 'bxfcbgfdxg', '2017-01-06');

INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('eee', 'eee', 84);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('matame', 'matame', 20);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('oscar', 'oscar', 19);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('prueba', 'j', 23);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('r1', 'r1', 1);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('r117', 'r117', 117);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('r16', 'r16', 16);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('r17', 'r17', 17);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('r2', 'r2', 2);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('r3', 'r3', 3);
INSERT INTO tomasMacri_Newspapers.login (user, password, id_reader) VALUES ('root', 'root', -1);

INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('eeeeee', 'PBKDF2WithHmacSHA256:2048:sLvBqXqb3zH97ZT4tqlFNo4i9cPImqB1KEAzLxCYTVQ=:J8kwnZ+sbDCUeBiW3ow+WlSAAAyQU9F/V8+qyMBHsxc=', 0, '3m6uIwfEdDvhJpOj_lbgqxZY3K_Kap3v4u7bzw0I0aM=', '2022-12-29 17:46:40', 105, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('hawajem624@paxven.com', 'PBKDF2WithHmacSHA256:2048:bDYKHLxhzlY39MgJH2U0ZwjdNQIbqCkcMRXjqzKia+k=:j6QNrXAWvev+5I+t02Yh3rGrg4Uh2rfzBoTtCip5bJY=', 1, 'ww2WORu6emwHY9nZXPx3Q8uNIkxrGcgadaVgI8rocHQ=', '2022-12-15 12:09:47', 81, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('kalet69096@dmonies.com', 'PBKDF2WithHmacSHA256:2048:3RbaWdxkzuRSsfvKNBh4+7WuobtovrOlAwzESRVpyH0=:N4/rxXVm7kEhFrXdcLAW+azr5GLMfx2jIJPp5nck0fQ=', 1, 'ZDF8_SGWIpBhjp34Jvi1x3teSOGtuMucFq3Jmf6I-IA=', '2022-12-08 13:33:06', 78, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('lapike8792@prolug.com', 'PBKDF2WithHmacSHA256:2048:JI4RsWdcRYiIFuSH5oxqY6sVnKZoDZT6/FwXttH/tH4=:O6Ba0IzboTgsqJE8P9r4/Av9/xWt2TF2ww12U6S+q2g=', 1, 'N5L_7I3e6-FjxCoExCIehnyr2XBLYL5fEPJsCkcuUYc=', '2022-12-29 17:47:20', 106, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('matame@gmail.com', 'PBKDF2WithHmacSHA256:2048:+N2AvSrIF+kg8nVosgZN1Q9JBCrJwngW0jdbTpuU3+0=:E8qmNo6N6QHARZONLgUySKMshTqiqGAZjMCcc1kcWIw=', 1, 'token', '2022-01-01 00:00:00', 20, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('niwowap810@dmonies.com', 'PBKDF2WithHmacSHA256:2048:SfU0m2d4TrXmVX3k+2/+YZU4rn1I5XAm2TdKJrci55I=:peF3RFd2QmAw/WgYdrD7y4JbbutM6IeAbtXnyXG870Q=', 0, '1Jx7EB7yC6XNVcHdnlfJdnPkfjez63Su7Cq99A5sFUE=', '2022-12-10 11:32:51', 79, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('oscar@gmail.com', 'PBKDF2WithHmacSHA256:2048:Svf7ejLYIUw3uI/aP0Mn/We2KAxsiby/BLfk8hQj6+8=:8hgLrsATVoeH7unbXqQ2Zt7qxL/XSeL0cuIo+NRyI78=', 1, 'token', '2022-01-01 00:00:00', 19, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('prueba@gmail.com', 'PBKDF2WithHmacSHA256:2048:Mg0LFPMqX7c9rC2mLej12dA1/mG5tMxswzNmq+oFbqM=:cQVDLykO/k/W+1FZjVrsKA+1zYf0wklHCvKdghbt6tE=', 1, 'token', '2022-01-01 00:00:00', 23, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('r1@gmail.com', 'PBKDF2WithHmacSHA256:2048:/HQyXe7xrvjow+/1PCUPXYWtydbzC30XlfralaOXXDc=:/thJhQ0Ixe7IUwYZnWBXNd28Iicnx+AeSq3AUJfE4C4=', 1, 'token', '2022-01-01 00:00:00', 1, 4, '2023-01-12 13:14:38');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('r16@gmail.com', 'PBKDF2WithHmacSHA256:2048:eDyj700mZBJnNhxgRba2aUky5V2/H//42av/e12niVo=:P/nQalyMWWzy4dZNr/dngUX+K5pBQ08E10Z6vrxadr8=', 1, 'token', '2022-01-01 00:00:00', 16, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('r17@gmail.com', 'PBKDF2WithHmacSHA256:2048:cQ1eu46FTyGf1zymb2EKcB+yWv7++ZrZUzbuEit5MEg=:6VbCpSzu7+y9chVhH7rX37jU7+rsBw7vk1S0OIKkBUg=', 1, 'token', '2022-01-01 00:00:00', 17, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('r2@gmail.com', 'PBKDF2WithHmacSHA256:2048:WJnBzXUgUuL6XFsUCBYA9lEoYnT5G7/5vpPFR6H8HrE=:TNArBAlFY76AMSF/htZjqksQxTf/3ZlmDw5a9IZCUDI=', 1, 'token', '2022-01-01 00:00:00', 2, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('r3@gmail.com', 'PBKDF2WithHmacSHA256:2048:jnPB36CemiAhn/AgcR73vo5sVraBrVqwGZvirTucMiI=:/wjLxhtdUN9N2EM/7LYd6yUxT62AFz8vsph0vpUz2Gw=', 1, 'token', '2022-01-01 00:00:00', 3, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('rateyij875@nazyno.com', 'PBKDF2WithHmacSHA256:2048:XmlM+XJ9zPYfZv8muRzKpvmyMoQ1qwmMA9+9QPllEF4=:01TmmYYxD1uezVnPImLQ6d5tLSZgv9+OK22J/zQztQw=', 0, 'zbshaV63AkXdPaoX8nDoMfHOnSDDejusDe2OBi0zA5E=', '2022-12-19 19:58:40', 97, 0, '2022-01-01 00:00:00');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('root', 'PBKDF2WithHmacSHA256:2048:/HQyXe7xrvjow+/1PCUPXYWtydbzC30XlfralaOXXDc=:/thJhQ0Ixe7IUwYZnWBXNd28Iicnx+AeSq3AUJfE4C4=', 1, 'token', '2022-12-19 19:58:40', -1, 0, '2023-01-22 17:48:25');
INSERT INTO tomasMacri_Newspapers.login_oscar (email, password, activated, activation_token, fecha_registro, id_reader, petitions_left, time_first_petition) VALUES ('vehima3388@pro5g.com', 'PBKDF2WithHmacSHA256:2048:BDrAYqyVxv1Quw05pnkYsVwtPSDLvTSkyzxLB9WKXZ8=:rAyFTz6dU2OWUkUVLPMjTt4cXqJ8ZPyKJcpUquTotmI=', 1, '_vJxwaHuQ8Nd6L7_5_Js3YKm-8uAzQjeQgnJFWrqSig=', '2022-12-21 21:44:29', 104, 0, '2022-01-01 00:00:00');

INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (2, -1, 1);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (3, 1, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (4, 2, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (5, 3, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (6, 16, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (7, 17, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (8, 19, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (9, 20, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (10, 23, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (11, 68, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (12, 69, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (13, 76, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (14, 77, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (15, 78, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (16, 79, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (17, 80, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (18, 97, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (23, 104, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (24, 105, 2);
INSERT INTO tomasMacri_Newspapers.reader_roles (id, reader_id, role_id) VALUES (25, 106, 2)

INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (1, 2, '2022-11-15', null);
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (1, 3, '2022-12-18', '2023-01-04');
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (1, 4, '2021-03-20', null);
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (2, 2, '2023-01-18', null);
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (2, 3, '2023-01-18', null);
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (2, 4, '2023-01-18', null);
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (3, 2, '2023-01-09', null);
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (3, 4, '2005-08-22', null);
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (20, 2, '2022-11-14', null);
INSERT INTO tomasMacri_Newspapers.subscriptions (id_reader, id_newspaper, signing_date, cancellation_date) VALUES (117, 2, '2023-01-18', null);

INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (3, 3, 5, 3);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (3, 6, 2, 19);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (1, 6, 4, 28);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (3, 14, 4, 62);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (1, 14, 4, 71);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (1, 27, 2, 80);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (1, 29, 4, 84);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (3, 27, 5, 91);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (2, 4, 5, 103);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (2, 6, 5, 104);
INSERT INTO tomasMacri_Newspapers.readarticles (id_reader, id_article, rating, id) VALUES (117, 3, 5, 106);