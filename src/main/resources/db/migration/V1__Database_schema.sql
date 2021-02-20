create sequence users_id_seq
    increment 1
    start 1
    no cycle;

create table users(
    id              bigint primary key default nextval('users_id_seq'),
    username        varchar(20)not null ,
    password        varchar(255) not null ,
    is_online       bool not null,
    last_seen       timestamp not null
);

create sequence rooms_id_seq
    increment 1
    start 1
    no cycle;

create table rooms(
    id              bigint primary key default nextval('rooms_id_seq'),
    name            varchar(20) not null
);

create sequence users_in_rooms_id_seq
    increment 1
    start 1
    no cycle;

create table users_in_rooms(
    id              bigint primary key default nextval('users_in_rooms_id_seq'),
    user_id         bigint  references users(id) not null ,
    room_id         bigint  references rooms(id) not null
);

create sequence messages_id_seq
    increment 1
    start 1
    no cycle;

create table messages(
    id              bigint primary key default nextval('messages_id_seq'),
    type            smallint not null,
    data            oid not null,
    read_status     bool not null,
    send_status     bool not null,
    send_date       date not null,
    sender_id       bigint references users(id) not null,
    target_room_id  bigint unique references rooms(id) not null
);