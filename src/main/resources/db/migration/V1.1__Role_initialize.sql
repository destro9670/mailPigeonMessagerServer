insert into roles(name) VALUES ('ROLE_USER');
insert into roles(name) VALUES ('ROLE_ADMIN');

insert into users(username, first_name, last_name, password, status)
    values ('test', 'test', 'test', '$2b$10$OPaJGH1gSI0Kmr1.KbPPseBRbWiADS.hVe5UyISNqJVh3x/nOaXPG', 'ACTIVE');
insert into users(username, first_name, last_name, password, status)
    values ('test1', 'test', 'test', '$2b$10$OPaJGH1gSI0Kmr1.KbPPseBRbWiADS.hVe5UyISNqJVh3x/nOaXPG', 'ACTIVE');

insert into users_has_roles(user_id, role_id) values ( 1, 1);
insert into users_has_roles(user_id, role_id) values ( 1, 2);
insert into users_has_roles(user_id, role_id) values ( 2, 1);