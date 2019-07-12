insert into user (id, username, password)
    value (1, 'admin', 'admin');

 insert into user_role (user_id, roles)
    value (1, 'USER'), (1, 'ADMIN');