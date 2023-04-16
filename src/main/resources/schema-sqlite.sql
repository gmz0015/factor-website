CREATE TABLE IF NOT EXISTS factor
(
    id          INTEGER           not null
        primary key autoincrement
        unique,
    name        TEXT              not null
        unique,
    status      INTEGER default 1 not null,
    create_time date              not null,
    update_time date              not null
);

CREATE TABLE IF NOT EXISTS factor_log
(
    id      INTEGER not null
        primary key autoincrement
        unique,
    tag     TEXT,
    score real,
    online_time real,
    time    date    not null,
    user_id INTEGER not null
        constraint factor_log_user_id_fk
            references user
);


CREATE TABLE IF NOT EXISTS factor_data
(
    id      INTEGER not null
        primary key autoincrement
        unique,
    type    INTEGER not null,
    content TEXT,
    image   BLOB
);

CREATE TABLE IF NOT EXISTS factor_data_log
(
    id      INTEGER not null
        primary key autoincrement
        unique,
    log_id    INTEGER not null
        constraint factor_log_id_fk
            references factor_log,
    data_id INTEGER not null
        constraint factor_data_id_fk
            references factor_data
);

CREATE TABLE IF NOT EXISTS role
(
    id     INTEGER           not null
        primary key autoincrement
        unique,
    name   TEXT              not null
        unique,
    status INTEGER default 1 not null
);

create unique index IF NOT EXISTS role_id_uindex
    on role (id);

CREATE TABLE IF NOT EXISTS sys_log
(
    id      INTEGER  not null
        primary key autoincrement
        unique,
    type    INTEGER  not null,
    user_id INTEGER  not null
        constraint sys_log_user_id_fk
            references user,
    time    datetime not null
);

CREATE TABLE IF NOT EXISTS user
(
    id       INTEGER           not null
        primary key autoincrement
        unique,
    username TEXT              not null
        unique,
    password TEXT              not null,
    salt     TEXT              not null,
    status   INTEGER default 1 not null
);

create unique index IF NOT EXISTS user_id_uindex
    on user (id);

CREATE TABLE IF NOT EXISTS user_role
(
    id      INTEGER not null
        primary key autoincrement
        unique,
    user_id INTEGER not null
        constraint user_role_user_id_fk
            references user,
    role_id INTEGER not null
        constraint user_role_role_id_fk
            references role
);



