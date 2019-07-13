insert into user (id, username, password)
    value (1, 'admin', '$2a$08$pvjkFfl5ovtqBm1t5S7CPOriXzfzhv5ejHIqT.UgMRaKG7/m9BiaC');

 insert into user_role (user_id, roles)
    value (1, 'USER'), (1, 'ADMIN');