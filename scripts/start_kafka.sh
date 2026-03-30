#!/bin/bash

docker run -d \
  --name kafka \
  --network bank-network \
  -p 9092:9092 \
  -p 9093:9093 \
  -e KAFKA_PROCESS_ROLES=controller,broker \
  -e KAFKA_NODE_ID=1 \
  -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT \
  -e KAFKA_LISTENERS=INTERNAL://:29092,EXTERNAL://:9092,CONTROLLER://:9093 \
  -e KAFKA_ADVERTISED_LISTENERS=INTERNAL://kafka:29092,EXTERNAL://localhost:9092 \
  -e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER \
  -e KAFKA_CONTROLLER_QUORUM_VOTERS=1@localhost:9093 \
  -e KAFKA_INTER_BROKER_LISTENER_NAME=INTERNAL \
  apache/kafka:4.0.2

echo "Waiting for Kafka to be fully ready..."

# Health check: wait for Kafka to respond to broker API calls
RETRIES=0
MAX_RETRIES=5

while [ $RETRIES -lt $MAX_RETRIES ]; do
  docker exec kafka kafka-broker-api-versions.sh --bootstrap-server localhost:9092 > /dev/null 2>&1
  if [ $? -eq 0 ]; then
    echo "✓ Kafka is ready"
    break
  fi
  RETRIES=$((RETRIES + 1))
  echo "Waiting... ($RETRIES/$MAX_RETRIES)"
  sleep 2
done

if [ $RETRIES -eq $MAX_RETRIES ]; then
  echo "✗ Kafka failed to start within timeout"
  exit 1
fi

# Additional wait for __consumer_offsets topic to be created
sleep 5

# Reset consumer group offset to earliest with retry logic
echo "Resetting consumer group offset..."
RESET_RETRIES=0
RESET_MAX_RETRIES=5

while [ $RESET_RETRIES -lt $RESET_MAX_RETRIES ]; do
  docker exec kafka kafka-consumer-groups.sh \
    --bootstrap-server localhost:9092 \
    --group contract-loans \
    --reset-offsets \
    --to-earliest \
    --execute \
    --topic loan-contract-accept 2>/dev/null

  if [ $? -eq 0 ]; then
    echo "✓ Consumer group offset reset successfully"
    break
  fi

  RESET_RETRIES=$((RESET_RETRIES + 1))
  if [ $RESET_RETRIES -lt $RESET_MAX_RETRIES ]; then
    echo "Reset failed, retrying... ($RESET_RETRIES/$RESET_MAX_RETRIES)"
    sleep 3
  fi
done

if [ $RESET_RETRIES -eq $RESET_MAX_RETRIES ]; then
  echo "⚠ Could not reset offset, but Kafka is running. Try manual reset later."
else
  echo "✓ Kafka started and consumer group offset reset successfully"
fi

