CREATE TABLE public.client
(
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    name          character varying(255),
    cpf           character varying(11),
    email         character varying(255)
);


create table public.product
(
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    description   character varying(255) not null,
    min_value     numeric(19,2) not null default 0 not null,
    max_value     numeric(19,2) not null default 0 not null
);

create table public.simulation
(
    id                 BIGSERIAL NOT NULL PRIMARY KEY,
    total              numeric(19,2) not null default 0 not null,
    instalment_size    integer not null,
    is_effective       boolean,
    product_id         bigserial not null,
    contract_id        bigserial not null
);

create table public.installment
(
    id                      BIGSERIAL NOT NULL PRIMARY KEY,
    number                  integer not null,
    value                   numeric(19,2) not null default 0 not null,
    discount                numeric(19,2) not null default 0 not null,
    total                   numeric(19,2) not null default 0 not null,
    total_with_discount     numeric(19,2) not null default 0 not null,
    simulation_id           bigserial not null
);

create table public.contract
(
    id                 BIGSERIAL NOT NULL PRIMARY KEY,
    text               text,
    is_assign          boolean,
    completed          boolean,
    account_number     character varying(15) not null,
    client_id          bigserial not null,
    product_id         bigserial not null
);


ALTER TABLE ONLY public.simulation
    ADD CONSTRAINT fk_simulation_product FOREIGN KEY (product_id) REFERENCES public.product(id);

ALTER TABLE ONLY public.simulation
    ADD CONSTRAINT fk_simulation_contract FOREIGN KEY (contract_id) REFERENCES public.contract(id);

ALTER TABLE ONLY public.installment
    ADD CONSTRAINT fk_installment_simulation FOREIGN KEY (simulation_id) REFERENCES public.simulation(id);

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT fk_contract_client FOREIGN KEY (client_id) REFERENCES public.client(id);

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT fk_contract_product FOREIGN KEY (product_id) REFERENCES public.product(id);