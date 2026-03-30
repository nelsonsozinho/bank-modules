#!/bin/bash

docker run -d \
  --name redis \
  --network bank-network \
  -p 6379:6379 \
  redis:latest

