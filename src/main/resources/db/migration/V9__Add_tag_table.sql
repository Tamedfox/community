create table tagCategory
(
  id bigint auto_increment primary key not null,
  category varchar(20) not null,
  remark varchar(30)
);
create table tags
(
  id bigint auto_increment primary key not null,
  name varchar(20) not null,
  parent_id bigint not null,
  remarik varchar(30)
);



