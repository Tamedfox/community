alter table question modify id bigint NOT NULL;
alter table `user` modify id bigint NOT NULL;
alter table question modify creator bigint not null;
alter table `comment` modify commentator bigint not null