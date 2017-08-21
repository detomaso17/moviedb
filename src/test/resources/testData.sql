use moviedb;

insert into users(id, username, password)
    values (1, 'admin', '$2a$10$X70UGdCKbcaHB.SX0yO.6.mrLkkpRQ9dEHI6TAivBUDExtFN8nn1i');
insert into users(id, username, password)
    values (2, 'admin2', '$2a$10$X70UGdCKbcaHB.SX0yO.6.mrLkkpRQ9dEHI6TAivBUDExtFN8nn1i');

insert into movies(id, user_id, title, description, watched)
    values(1234561234567891, 1, 'movie one', 'sample description', 1);
insert into movies(id, user_id, title, description, watched)
    values(1234561234567892, 1, 'movie two', 'short description', 0);

insert into movies(id, user_id, title, description, watched)
    values(1234561234567893, 2, 'movie three', null, 1);
insert into movies(id, user_id, title, description, watched)
    values(1234561234567894, 2, 'movie four', null, 1);
insert into movies(id, user_id, title, description, watched)
    values(1234561234567895, 2, 'movie five', null, 0);
