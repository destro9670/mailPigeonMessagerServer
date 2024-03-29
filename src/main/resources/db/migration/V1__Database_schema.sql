CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create sequence roles_id_seq
    increment 1
    start 1
    no cycle;

create table roles
(
    id      bigint primary key default nextval('roles_id_seq'),
    name    varchar(20) not null,
    created timestamp          default current_timestamp not null ,
    updated timestamp          default current_timestamp not null
);

create sequence users_id_seq
    increment 1
    start 1
    no cycle;

create table users
(
    id        bigint primary key default nextval('users_id_seq'),
    uuid varchar(255)  default  uuid_generate_v1mc(),
    username  varchar(20)  not null,
    password  varchar(255) not null,
    is_online bool               default false,
    status    varchar(40)  not null
);

create sequence tokens_id_seq
    increment 1
    start 1
    no cycle;

create table tokens
(
    id      bigint primary key default nextval('tokens_id_seq'),
    access  varchar(255)                 not null,
    refresh varchar(255)                 not null,
    user_id bigint references users (id) not null
);

create sequence users_has_roles_id_seq
    increment 1
    start 1
    no cycle;

create table users_has_roles
(
    id      bigint primary key default nextval('users_has_roles_id_seq'),
    user_id bigint references users (id) not null,
    role_id bigint references roles (id) not null
);

create sequence rooms_id_seq
    increment 1
    start 1
    no cycle;

create table rooms
(
    id       bigint primary key default nextval('rooms_id_seq'),
    uuid     varchar(255)  default  uuid_generate_v1mc(),
    user1_id bigint references users (id) not null,
    user2_id bigint references users (id) not null
);

create sequence messages_id_seq
    increment 1
    start 1
    no cycle;

create table messages
(
    id             bigint primary key default nextval('messages_id_seq'),
    uuid           varchar(255)  default  uuid_generate_v1mc(),
    text           text                                 not null,
    read_status    bool               default false,
    sender_id      bigint references users (id)        not null,
    room_id bigint  references rooms (id) not null,
    created        timestamp          default current_timestamp not null
);

create unique index msg_idx on messages (uuid);
create unique index room_idx on rooms (uuid);
create unique index user_idx on users (username);