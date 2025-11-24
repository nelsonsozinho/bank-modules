CREATE TABLE public.transaction
(
    id                      BIGSERIAL NOT NULL PRIMARY KEY,
    client_id               bigint,
    source_account_number   character varying(255),
    target_account_number   character varying(255),
    amount                  numeric(19,2) not null default 0,
    transaction_type        character varying(255) not null,
    transaction_data        date not null
);