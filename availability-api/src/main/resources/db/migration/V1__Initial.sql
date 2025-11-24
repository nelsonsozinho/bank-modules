CREATE TABLE public.client
(
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    name          character varying(255),
    cpf           character varying(11),
    email         character varying(255)
);

CREATE TABLE public.financial_restriction
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    value           numeric(19,2) not null default 0,
    source          character varying(255),
    last_update     date not null,
    is_activated    boolean,
    client_id       BIGSERIAL
);

ALTER TABLE ONLY public.financial_restriction
    ADD CONSTRAINT fk_financial_restriction FOREIGN KEY (client_id) REFERENCES public.client(id);
