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

INSERT INTO TomasMacri_CajaFuerte.user (name, password, role) VALUES ('aa', 'PBKDF2WithHmacSHA256:2048:1UECmPOrZGL/Y6SoNoMpPZxtlAFqC2ok7d2KUI/JwuQ=:UuVBA07zflbeCLJy/MggULrN/x1gYzFl5xCd8AqFFSs=', 'USER');
INSERT INTO TomasMacri_CajaFuerte.user (name, password, role) VALUES ('root', 'PBKDF2WithHmacSHA256:2048:/HQyXe7xrvjow+/1PCUPXYWtydbzC30XlfralaOXXDc=:/thJhQ0Ixe7IUwYZnWBXNd28Iicnx+AeSq3AUJfE4C4=', 'ADMIN');
INSERT INTO TomasMacri_CajaFuerte.user (name, password, role) VALUES ('user1', 'PBKDF2WithHmacSHA256:2048:wN5mOZ7ELm3p6+B8aJ44R+0oTfURQ7/a7aO1YvnHi4I=:iSwHq5Mrd8Q406m7nFH1uB1mIz8T75ybG1r7eWmf5lo=', 'USER');
INSERT INTO TomasMacri_CajaFuerte.user (name, password, role) VALUES ('user2', 'PBKDF2WithHmacSHA256:2048:79odlUtkf0tIQ1ws7B68lsObpsOr87SEg0C3hJEa3cU=:/ZrFlbbOYgftIvSh5hMus8mzFn//eZ5FdAqyiTSkCak=', 'USER');
INSERT INTO TomasMacri_CajaFuerte.user (name, password, role) VALUES ('user3', 'PBKDF2WithHmacSHA256:2048:CyxMgJB456QKKDIazCX5JFWVSpGo1v+DgnAJmeZB2ig=:jdQ/1BajxRCVkw+iQXdp45eE32St5d0GXYZX40RdLw8=', 'USER');

INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (17, 'readOnly', 'PBKDF2WithHmacSHA256:2048:QLEkJyFGAvAarTDtJPFRDWB0EAbO5+q4IcE3vwm+YiQ=:hLNll5ShmGUQ6GEwY2uKh3B5YrOQtkBj6sT7wu1oAwY=', 0, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (19, 'readOnlySi', 'PBKDF2WithHmacSHA256:2048:1OSA0OcBY97KRtM0hhJcYWbtsusdpQa34xjMSWXP364=:fi0+uMcMv7Tq+WMyY7ntPM4Ym9DgHUcwXGPzjKOuXMU=', 1, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (20, 'user1f', 'PBKDF2WithHmacSHA256:2048:RI2rOWRUfoDbWXHa2+uKerY/ae8poCryWXHM19M6lXo=:BAKWsdl9ZlQt7JT+rEWUfsQsjQ1o8KokfTHaPCZDIvA=', 0, 'user1');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (21, 'user1fro', 'PBKDF2WithHmacSHA256:2048:s/O0LgF6eGhwjLCXeO7I+Z6pa3M/3oRjEKftUbF4yFA=:4IGHTly3WFV/ESDakYEz7jLpKS3W+H2+7kG40As/m+I=', 1, 'user1');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (22, 'hola a', 'PBKDF2WithHmacSHA256:2048:DcDaG2tXLSvCPmoPaoiYNUjJItjKrUqA8Y1Jhti5QPs=:56NJvxOwazNtD6u/TxvMdK4muYP1vp3pR8OeMjuoOYc=', 0, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (23, 'hola b', 'PBKDF2WithHmacSHA256:2048:95OY4FQo/icZP9mf7KhjXu5hcd3ww+xsjYW8aO8IkFE=:UGBitDiIf2KxpSXudCMCP7BXUpbNlCxRL1YZu2/A4eU=', 0, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (24, 'hola c', 'PBKDF2WithHmacSHA256:2048:MEbzC27fb9FXJqJ6ma4ko28Zx2cH1zhl004sgsviauM=:1RUUJeICkX44uONRV2cZT1sLPB452y1yPSep+tS3oY4=', 0, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (27, 'hola aa', 'PBKDF2WithHmacSHA256:2048:4gnWh51yUZufV963PpWB9He/dEYXvgpGz7BRL3beQss=:i3u/eGHxhpJqJ7P7XpcCqzQ4gLqbf35KEsbozzwngec=', 0, 'user1');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (28, 'fol56', 'PBKDF2WithHmacSHA256:2048:OXdpAt5Li1EdvhH5vUvehhoq1ZzAQyvyaBkbdm+p6AU=:amD8jgkwUe+g3nKSz5OgneH2ZCv33bTwigSy2f1dRgs=', 1, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (29, 'fol56', 'PBKDF2WithHmacSHA256:2048:TGa55LRTOJyev+P22JC0iWMvesA//fXfROs8eSV2VeQ=:lVzJu/AhuBYVj3eYxmqyS7bZnIl/6JQRe6geltq3BOQ=', 1, 'user1');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (31, 'sdafdf', 'PBKDF2WithHmacSHA256:2048:z6MyR9M4XLPQCU9FfbgRsIqqJIr5RChLqj3AfvG2biA=:dsm/pLbJQT4lp/Nyc7GEtin33uMKgWMo2kG2UpJiews=', 0, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (32, 'asfsadf', 'PBKDF2WithHmacSHA256:2048:/OolrmigKSQLKEfAXZU4iCYtgcBXcSfs9oG0GKlkfZk=:GmcVDulDaceksFkeTiltC5fRB0b8aEXf9kh1GnmgrhM=', 0, 'user2');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (33, 'test', 'PBKDF2WithHmacSHA256:2048:rpwHHdBRg54xdRSc9j5NOlvXKMNXjd1P3Ld3qzy/+uE=:rep6uKSZY7xIQXEV9vcRGhwVFcKaW7e+QktlMTXNyTE=', 0, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (35, 'cambiarrr', 'PBKDF2WithHmacSHA256:2048:scsVVcV45bFNSBKb+PH0CDgFsuJpmEUOLokv6Xs6x0o=:ICsSiGBBiiLpTm1tQelpg6UJjiymF2jWWDehdJ4LwnE=', 1, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (37, 'sss', 'PBKDF2WithHmacSHA256:2048:6U/glyly7XYzc6ZlpXDlIsZGeg2+Lv15EIZpchssBfg=:VbggCXv36oVowOooYkFK8CLTABXKmcpnL5mLibKYb4A=', 1, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (38, 'aw', 'PBKDF2WithHmacSHA256:2048:VVD5X5T0MBMXdOmA94PtC1aN1YIm9HS3vPM4/53XJoM=:i+geFSBCL1dsInrQvSWn45JwudN2lAodbNTyQO2o1LQ=', 1, 'aa');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (39, 'aw', 'PBKDF2WithHmacSHA256:2048:3Rg2ODLX6esFPUBe4mLgyyMVgRwPjAoa8ZCfnV/Vf/Q=:Nck0zFY0kHxscXLi5JaaxLOKx9yDPhvAlqXU2nOwN6w=', 0, 'root');
INSERT INTO TomasMacri_CajaFuerte.folder (id, name, password, `read_only`, name_user) VALUES (40, 'roto', 'PBKDF2WithHmacSHA256:2048:BKzdy/nBrGw91E8KTvFsEdtKXUIpC+UXvmYB5h6cREg=:tAorS41hTQrH8yZDmWqaDJkB5K3bbEEorAspyxvGT+M=', 0, 'root');

INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (19, 'e1kWjfS0fZqD7dFCHmEA8px_bk26KMeu8-UmA7P1E1SIu3cF573Jo7o7_llkzJUN', 19);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (20, 'RCiX6Q65XlAaubHBqCqRCwhc1ohFuPxiMJ2y9ZcE4w7hnmH3mKSQ1lzCYrNyI7tLHoK2qA==', 19);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (21, 'XLUw4IQOwadv0H4EkSAZYQHNb1tTFjTrNVKkPTYHSwXshsr88aKTHLVrMT9-6HBwRQ==', 19);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (22, 'GS4p1KVOHel63t19ES9M5bVB0Hg8WjG1y8gxDeDCPgzCcgq42nsCRUntcHyNGiRQ', 21);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (23, 'WaKwq9Z2iw4EEat43ylj6VBtr73sH_ouiI1GwzCVn4sVDUAbsc_Gyirih9-F9tZG', 22);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (24, 'o7Z5bD8T1mdw5g5d4YR_HiNU3yijBAU2j_wyj8T-ObB-6xLvacDPMN1B9WV8gAol', 22);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (27, 'gHj2uSFY_XgU3fsqM736iSobNPM_-9f5PNQ26dqPecEYX7gMnUsWnES-A7llzXv7FA==', 24);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (28, '-iya4uOrQu-i8c77k7U9uS1eImRseUG_uPp84yBhPRwNUGaQcsALTNhZVQTzwfUz', 24);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (29, 'RwRMp2OrUahS6Uz6HK9aoIdg_MppIoIbVgkjbfpCh1mG5Zz0z46G0b-eXjZHhI369ig=', 27);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (30, 'VgUS_Iq6nppTF-NYQZgYSVnwBvwHXtrMLI2lItJmqlRpsg_mp1c72fJ2DsghbYWBNEQX-Q==', 27);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (40, 'vifAUv3-N8pu12plWvKXo7xodUD_EpKkt012VtFxXAeFhjZnLdmgCibH6ruEsWUrexdN', 31);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (41, 'EFwb0qLo0bA9OWUPuMK_mzaMAX_twDzvvVoDcOSTLuasNek2sEltvbEIh-jDPBaF', 31);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (43, 'E6Y2tcLFuhpAmL0WHLwwLQio6CfX4F_djf9aSy9x1l6h-TtOU6R2sxvjsHPXpDqixw==', 17);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (44, '4m5K8lWjHCmVRiwPw4kaKeQLL5MQe7jkudZitjG4oheJi4a3tqUWTzLEn_FLuH6VG1f7rksjtc0=', 17);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (47, 'vpFrYYwHshgT4aFJvY8aRYpJLj6-CfcRh37NVNZJj2Suz3vpRr1LJVxOEhSK-cMflL-Wz7d0Tw==', 17);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (48, 'YgCS6bM8bDR8gRRk9sm7qCeP--SQMOAx7wqlhJYZebT2RznFCPpdbrRzuXfrVUl8IcMf', 32);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (50, 'n5A8LM7t7dQDz0jACN4SKoumSn74YbWSKDq6apYjFQ26nsUS1fKiY2PfaS_q6YdE', 32);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (51, 'ZgttWTIF0pHEzhtDwsazULhoq2t43agDUFL0fbeSIYjVN2MWojwUORGQCR8eS5sXxCqlVxU-iw==', 23);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (52, 'VnAOqZjJHFJlitdZIySWgMXYsdcsCZ_ptx6FbnvgWxv9O9ADYtvpjJE2XVEMka3J8w==', 23);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (53, 'md1_f45_3r4ilx87PkoJBe0CfY8c-LVjqoHktbSteRRpCfIi4zwiZ6rROO3YFQUN6d55r6n31duIxOQtEDbeDCw=', 23);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (55, 'eZQDwz-zYq7wCo25nCxAKUz8mq2xFyO6FOB1o4u3ATLWwuaF1jlBTSsuXZuobpNInsk9K6fwM9si', 33);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (56, 'xmp-oi6tJPX4tOcaRmEuwQBwvhzN-Q6VV5mlTgssnxnwun8j9T6eroUjyoGcgxQ=', 33);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (60, 'O-yUtyxxiNuzvc31eqYerWRzsRdy3s9aZZs19GnQ0jUZixtfoNMh89EmEcmw1t3VcvU=', 35);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (61, 'Rye1o1ESOZo7kID0CpktudY78mdNAwa1Sb5F4AYLajw1xwFTFVOerUsnxTE7v3OQ1SgkOT8=', 35);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (62, '8AtGE0ZZ690B7nJcwLwftUHT-UvRi7-MSGAHro_1m5Outl1DLmrAg08Fgk4kbTP_dNZ9Crg=', 17);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (63, 'NHmyI7M_5F0GY_wMxTPENuId2CXtbs3OLRWD4C2Xj8pkKyfEIPdImZ7XmpKvm2Tql50X', 37);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (64, 'HwfwovpyXTNZ8_exSa1wZdWxxhYWxk3_ISrK4Mcd4nRlPRY-B6GTe8arvyYqlZuwVYnwxtgwom9Tv01oCrMYcHU=', 37);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (65, 'uOwQ_QgaGBcR_XKNsXQegfO6F-PbTIhkiVbFvZmYVw0DVhlC7FtffvgkXOu8zEiq', 37);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (66, 'sB-YIFtJYHMiAO1ATsA5rOwkFfJeQM5-j1sA5ATXgputtZ1vrxIoEw9RFrgwX8ud', 38);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (69, 'Q6g4TOoR13Jp3PJIBBr267He-eXwl0n6XrufylC8zYheDSPkpqgw0wG20hZAVpZJtg==', 40);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (70, '4TTE1Y1A8IQz5DB1jnj3LvD4ajmoXlb3XXWyQuDVyRnH76PmqqesgxLpVSihcJRSVg==', 40);
INSERT INTO TomasMacri_CajaFuerte.message (id, message, id_folder) VALUES (71, 'Dv91By-Y5FxqjNZbIJ4OAHmKmxXZ8Z5VuGNDPRWvfUa64zXqlj9Bld5_TBghUzLE-Z_m58U=', 20);
