create table user(
    id int not null,
    username varchar(25) not null,
    password varchar(80) not null,
    enabled boolean not null,
    primary key(id)
);
