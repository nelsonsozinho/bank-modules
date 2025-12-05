alter table account drop column client_id;
drop table client;
alter table account add column client_id BIGSERIAL NOT NULL