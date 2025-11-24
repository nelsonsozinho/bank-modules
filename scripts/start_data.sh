#!/bin/bash

export PGPASSWORD=postgres

psql -h localhost -p 5432 -U postgres -d availability -c "insert into client (id, name, cpf, email) values (1, 'Francis Drake', '77777777777', 'fc@gmail.com');"
psql -h localhost -p 5432 -U postgres -d availability -c "insert into financial_restriction(value, source, last_update, is_activated, client_id) values (40000, 'Casas Bahia', now(), false, 1)"

psql -h localhost -p 5432 -U postgres -d bank -c "insert into client (id, name, cpf, email) values (1, 'Francis Drake', '77777777777', 'fc@gmail.com');"
psql -h localhost -p 5432 -U postgres -d bank -c "insert into client (id, name, cpf, email) values (2, 'Ronald Reagan', '88888888888', 'rc@gmail.com');"
psql -h localhost -p 5432 -U postgres -d bank -c "insert into account(account_number, balance, client_id) values('123777', 5000, 1);"
psql -h localhost -p 5432 -U postgres -d bank -c "insert into account(account_number, balance, client_id) values('123888', 52000, 2);"

psql -h localhost -p 5432 -U postgres -d loan -c "insert into client (name, cpf, email) values ('Francis Drake', '77777777777', 'fc@gmail.com');"
psql -h localhost -p 5432 -U postgres -d loan -c "insert into product(id, description, min_value, max_value) values (1, 'Emprestimo Pessoal', 1000, 100000);"
psql -h localhost -p 5432 -U postgres -d loan -c "insert into product(id, description, min_value, max_value) values (2, 'Emprestimo Imobiliário', 10000, 500000);"
psql -h localhost -p 5432 -U postgres -d loan -c "insert into product(id, description, min_value, max_value) values (3, 'Emprestimo Automotivo', 10000, 200000);"
psql -h localhost -p 5432 -U postgres -d loan -c "insert into product(id, description, min_value, max_value) values (4, 'Crédito Educational', 10000, 200000);"

psql -h localhost -p 5432 -U postgres -d score -c "insert into client (name, cpf, email) values ('Francis Drake', '77777777777', 'fc@gmail.com');"
psql -h localhost -p 5432 -U postgres -d score -c "insert into score(found_score, last_update, client_id) values (50000, '2025-11-01', 1);"
