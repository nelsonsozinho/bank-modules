CREATE TABLE public.client
(
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    name          character varying(255),
    cpf           character varying(11),
    rg            character varying(8),
    email         character varying(255)
);

CREATE TABLE public.address
(
    id                  BIGSERIAL NOT NULL PRIMARY KEY,
    street              character varying(255),
    zip_code            character varying(11),
    neighborhood_name   character varying(8),
    state               character varying(2),
    country             character varying(100),
    is_official_address boolean,
    client_id           BIGSERIAL NOT NULL
);

ALTER TABLE ONLY public.address
    ADD CONSTRAINT fk_address_client FOREIGN KEY (client_id) REFERENCES public.client(id);
