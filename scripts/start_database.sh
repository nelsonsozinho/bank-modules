#!/bin/bash


docker run -d --name postgres \
	--network=bank-network \
	-e POSTGRES_USER=postgres \
	-e POSTGRES_PASSWORD=postgres \
	-v /var/lib/postgresql/data:/var/lib/postgresql/data \
	-p 5432:5432 postgres:16

sleep 5
docker exec postgres psql -U postgres -c "CREATE DATABASE score;"
docker exec postgres psql -U postgres -c "CREATE DATABASE loan;"
docker exec postgres psql -U postgres -c "CREATE DATABASE availability;"
docker exec postgres psql -U postgres -c "CREATE DATABASE bank;"
docker exec postgres psql -U postgres -c "CREATE DATABASE client;"
