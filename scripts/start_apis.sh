#!/bin/bash


docker container run -d --name bank.api \
	--network=bank-network \
	-e SPRING_DATASOURCE_USERNAME=postgres \
	-e SPRING_DATASOURCE_PASSWORD=postgres \
	-e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/bank \
	-e BOOTSTRAP_SERVER=kafka \
	-e BOOTSTRAP_PORT=29092 \
	-p 8282:8080 nelsonsozinho/bank.api:latest

sleep 2

docker container run -d --name availability.api \
        --network=bank-network \
        -e SPRING_DATASOURCE_USERNAME=postgres \
        -e SPRING_DATASOURCE_PASSWORD=postgres \
        -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/availability \
        -p 8080:8080 nelsonsozinho/availability.api:latest

sleep 2

docker container run -d --name loan.api \
        --network=bank-network \
        -e SPRING_DATASOURCE_USERNAME=postgres \
        -e SPRING_DATASOURCE_PASSWORD=postgres \
        -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/loan \
	-e BOOTSTRAP_SERVER=kafka \
        -e BOOTSTRAP_PORT=29092 \
        -e AVAILABILITY_API=http://availability.api:8080/restriction \
        -e SCORE_API=http://score.api:8080/score \
	-p 8181:8080 nelsonsozinho/loan.api:latest


sleep 2

docker container run -d --name score.api \
        --network=bank-network \
        -e SPRING_DATASOURCE_USERNAME=postgres \
        -e SPRING_DATASOURCE_PASSWORD=postgres \
        -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/score \
        -p 8383:8080 nelsonsozinho/score.api:latest


