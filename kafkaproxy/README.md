./Downloads/ngrok http 8080

Starta ZK
./bin/zookeeper-server-start ./etc/kafka/zookeeper.properties

Starta Kafka
./bin/kafka-server-start ./etc/kafka/server.properties

./kafka-console-producer --broker-list localhost:9092 --topic test

./bin/kafka-console-consumer --topic game.events --zookeeper localhost:2181
eller
./java/confluent/confluent-2.0.0/bin/kafka-console-consumer --topic test --zookeeper localhost:2181 --from-beginning


LIsta topics
bin/kafka-topics --list --zookeeper localhost:2181
