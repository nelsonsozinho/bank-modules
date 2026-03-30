#!/bin/bash

# Reset Kafka consumer group offset to earliest
# This allows the consumer to re-read all messages from the beginning

BOOTSTRAP_SERVER=${1:-localhost:9092}
GROUP_ID=${2:-contract-loans}
TOPIC=${3:-loan-contract-accept}

echo "Resetting consumer group: $GROUP_ID"
echo "Bootstrap server: $BOOTSTRAP_SERVER"
echo "Topic: $TOPIC"

docker exec kafka kafka-consumer-groups.sh \
  --bootstrap-server "$BOOTSTRAP_SERVER" \
  --group "$GROUP_ID" \
  --reset-offsets \
  --to-earliest \
  --execute \
  --topic "$TOPIC"

echo "✓ Consumer group offset reset successfully"

