use moviedb;

drop table if exists users;
create table users (
    id binary(16) primary key,
    username varchar(60) not null,
    password varchar(60) not null,
    enabled boolean
);
