CREATE TABLE public.client
(
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    name          character varying(255),
    email         character varying(255)
);

CREATE TABLE public.account
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    account_number  character varying(255),
    balance numeric(19,2) not null default 0,
    client_id       bigserial
);

ALTER TABLE ONLY public.account
    ADD CONSTRAINT fk_account_client FOREIGN KEY (client_id) REFERENCES public.client(id);
