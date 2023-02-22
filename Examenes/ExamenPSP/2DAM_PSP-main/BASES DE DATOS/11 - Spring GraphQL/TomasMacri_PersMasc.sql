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


INSERT INTO TomasMacri_PersMasc.personas (id, name, surname) VALUES (1, 'tomás', 'macri');
INSERT INTO TomasMacri_PersMasc.personas (id, name, surname) VALUES (2, 'oscar', 'novillo');
INSERT INTO TomasMacri_PersMasc.personas (id, name, surname) VALUES (3, 'lionel', 'messi');
INSERT INTO TomasMacri_PersMasc.personas (id, name, surname) VALUES (16, 'regi', 'strado');
INSERT INTO TomasMacri_PersMasc.personas (id, name, surname) VALUES (44, 'marina', 'vega');

INSERT INTO TomasMacri_PersMasc.login (id_persona, username, password, role) VALUES (44, 'marina', '$2a$10$epV1SefjbY/RnyLZ/L1/l.Xdvbi46FbIWjGEeVy6A.Izze2BOioK.', 'USER');
INSERT INTO TomasMacri_PersMasc.login (id_persona, username, password, role) VALUES (3, 'messi', '$2a$10$uY4cMd9nPGKZqSCnCP5GfeLBuw0QjKrNac5mAkj/aqhCwJ78K9zia', 'ADMIN');
INSERT INTO TomasMacri_PersMasc.login (id_persona, username, password, role) VALUES (2, 'oscar', '$2a$10$uY4cMd9nPGKZqSCnCP5GfeLBuw0QjKrNac5mAkj/aqhCwJ78K9zia', 'USER');
INSERT INTO TomasMacri_PersMasc.login (id_persona, username, password, role) VALUES (1, 'u1', '$2a$10$uY4cMd9nPGKZqSCnCP5GfeLBuw0QjKrNac5mAkj/aqhCwJ78K9zia', 'USER');
INSERT INTO TomasMacri_PersMasc.login (id_persona, username, password, role) VALUES (16, 'ur', '$2a$10$uY4cMd9nPGKZqSCnCP5GfeLBuw0QjKrNac5mAkj/aqhCwJ78K9zia', 'USER');

INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (49, 'L0NkDC9_lYfMqSnM32KayKM0Gn3abbWoPAK42WF0FKqTQPI9uu5bcTgSH-bZ8gq7HIlmAr2qzsyyKXbQhA==', 38, 3, 'burro');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (50, 'kWOFqF95HJvDg6H7eVJJ_I3e-4BrhHxAqOkwcnfrEF0xp1ou6Dz7ef38GhGNm7cNeq5sC_QDZr2YMSsMH-o=', 34, 3, 'caballo');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (51, 'FibdPWrHfMIAy3u0QaD1cbs3ok9RRIWjiOTr184Tu2hmYLUzn158SPHN9OWkATUTdu8y-l9e8SsVzg==', 24, 3, 'tortuga');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (52, '2KqRhefsDxtl9TbIH7nhE6AWZ6i40fMmIWp8NKTPP1jObmPjRk50KT56O_Ne3i36zY6seaUK0Hz-aQ==', 34, 3, 'perro');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (53, 'R1d3_ibXvW9t1cj5az860pIA7cDGM7jBNZVyzlPIwQC14roJ-Rakh7i15-M__inj', 1, 1, 'tortuga');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (54, 'NAw6XsZpNFBE8axa7u5zJFD9Je-8lo0Yq50YktSE4gyqbMGyWVfUCM4VJbMBKXYG', 2, 1, 'canario');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (55, 'oapXwgVBUJqjgMbwhb5vCzPMfz-pQ9MO28AdTpkdUze-ggQH00Ml3XfuShDViwLC', 3, 1, 'loro');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (56, 'amA1RXbknuOayPyXQ0I3HfmTbsvfpK8xTH8lmhdlh4nLTxqrBOkZccp6TzDBbOfV', 4, 1, 'mierda');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (57, 'Nob_B7H2lRveZ_Y9eD513CVHaproLFG3xD7opveIxibHcSN9MOEu0N3Cygtrwvy8', 5, 1, 'cerdo');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (58, '1YiF1IOsfbv95wViS1FqlKPiLjBysMlc-y91rA0z4uzZDttUZaiOWZ4ywgAilUtu3xVO', 2, 2, 'gato');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (59, 'Lv3giaMR1FsQKdIKx3KtcWiIwtAZb2utpbpIX4nJVe6pMWgNGXgw8nP2NYD4uWxrcUM=', 5, 2, 'perro');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (60, 'YKMhZ_9s3kLNwBdkTiQL9jgQpvrxh1PArN7GfCUK2IKPzvmFG_mgQcm_EP5MzAqUr3Sm', 6, 2, 'vaca');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (61, 'SIbA_L5WOMKKYCiWg_MxDmzh5-AV-_LDeBHcI0g1Bpf22evsfRUjpg8XZVX56acN', 1, 44, 'patito');
INSERT INTO TomasMacri_PersMasc.mascotas (id, name, age, id_persona, type) VALUES (65, 'fZQK_daPMKbKkdwJOW1527kV8b3Nuf8S0ALRzyUmk7m3jAgQFJTmML-QE-npgI-FAcQ=', 2, 44, 'patita');
