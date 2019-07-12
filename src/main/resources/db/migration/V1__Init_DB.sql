create table hibernate_sequence
  (next_val bigint);

insert into hibernate_sequence values ( 1 );

create table attachment (
  id bigint not null,
  generated_name varchar(255),
  generated_path varchar(255),
  original_name varchar(255),
  task_id bigint,
  primary key (id));

create table task (
  id bigint not null,
  complete bit not null,
  deadline datetime not null,
  deleted bit not null,
  implementation_date datetime,
  problem_statement_date datetime,
  tag bit not null,
  text_task varchar(2048) not null,
  topic_task varchar(255) not null,
  user_id bigint,
  primary key (id));

create table user (
  id bigint not null,
  password varchar(255) not null,
  username varchar(255) not null,
  primary key (id));

create table user_role (
  user_id bigint not null,
  roles varchar(255));

alter table attachment
 add constraint attachment_task_fk
  foreign key (task_id) references task (id);

alter table task
 add constraint task_user_fk
  foreign key (user_id) references user (id);

alter table user_role
 add constraint user_role_user_fk
  foreign key (user_id) references user (id);
