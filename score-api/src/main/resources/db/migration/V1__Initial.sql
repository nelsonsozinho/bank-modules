CREATE TABLE public.client
(
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    name          character varying(255),
    cpf           character varying(11),
    email         character varying(255),
    score_id      BIGSERIAL
);

CREATE TABLE public.score
(
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    found_score     numeric(19,2) not null default 0,
    last_update     date not null,
    client_id       bigserial
);

ALTER TABLE ONLY public.client
    ADD CONSTRAINT fk_score_client FOREIGN KEY (score_id) REFERENCES public.score(id);

ALTER TABLE ONLY public.score
    ADD CONSTRAINT fk_client_score FOREIGN KEY (client_id) REFERENCES public.client(id);