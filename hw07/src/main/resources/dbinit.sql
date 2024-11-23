create table if not exists users (id bigserial primary key, login varchar(255), password varchar(255), name varchar(255));
create table if not exists some_table (id bigserial primary key, some_field varchar(255));
