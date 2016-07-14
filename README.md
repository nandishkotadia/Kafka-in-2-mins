# Kafka-in-2-mins

Step 1: Download the code

Download the 0.10.0.0 release and un-tar it.
> tar -xzf kafka_2.11-0.10.0.0.tgz
> cd kafka_2.11-0.10.0.0
Step 2: Start the server

Kafka uses ZooKeeper so you need to first start a ZooKeeper server if you don't already have one. You can use the convenience script packaged with kafka to get a quick-and-dirty single-node ZooKeeper instance.

> bin/zookeeper-server-start.sh config/zookeeper.properties

Now start the Kafka server:
> bin/kafka-server-start.sh config/server.properties

Kafka server is up (Wooohoooo….!!!) 

To perform basic test to check if it is working fine:

1) Send some messages 
Run the producer and then type a few messages into the console to send to the server.(in another tab)

> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
This is a message
This is another message

2) Start a consumer (in another tab)

> bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning
This is a message
This is another message

If you are able to see the produced messages in consumer. Kafka is working fine else configuration needs to be checked.


Step 3: In src/main/java/com/kafka/system/utility/ :
KafkaProducer.java and KafkaConsumer.java are the Producer & Consumer utility which provides abstraction to the developer from underlying Kafka implementation. 

KafkaProducer uses the following properties:

metadata.broker.list		
Public Ip of machine on which Kafka server is running.
Default port of Kafka is 9092.

serializer.class	kafka.serializer.DefaultEncoder	
The serializer class for messages. The default encoder takes a byte[] and returns the same byte[].


KafkaProducer has method send(String topic, String data):

This method can be passed with two parameters i.e.
1) topic - Kafka topic to which the message is associated with.
2) data - Data to be published/sent to the Kafka topic. 


KafkaConsumer uses the following properties:

zookeeper.connect
Public Ip of machine on which zookeeper server is running.
Default port of zookeeper is 2181.

group.id
A string that uniquely identifies the group of consumer processes to which this consumer belongs. By setting the same group id multiple processes indicate that they are all part of the same consumer group.

zookeeper.session.timeout.ms 	
Zookeeper session timeout. If the consumer fails to heartbeat to zookeeper for this period of time it is considered dead and a rebalance will occur.
Default - 6000	

auto.commit.enable
If true, periodically commit to zookeeper the offset of messages already fetched by the consumer. This committed offset will be used when the process fails as the position from which the new consumer will begin.
Default - true

auto.commit.interval.ms	
The frequency in ms that the consumer offsets are committed to zookeeper.
Default - 60 * 1000


auto.offset.reset
What to do when there is no initial offset in Zookeeper or if an offset is out of range:
* smallest : automatically reset the offset to the smallest offset
* largest : automatically reset the offset to the largest offset
* anything else: throw exception to the consumer. If this is set to largest, the consumer may lose some messages when the number of partitions, for the topics it subscribes to, changes on the broker. To prevent data loss during partition addition, set auto.offset.reset to smallest
Default - largest

To run the application, start KafkaApplication.

startProducer() in ProducerExample.java publishes the 10000 messages(This number can be changed to 1 million and even then it will produce and consume blazing fast) to the KAFKA_TOPIC provided in producer.properties file (‘test’).

startConsumer() in ConsumerExample.java consumes the messages from the associated KAFKA_TOPIC and consumer group provided in properties file.
It fetches the stream list for the given topic and start consuming the data. 



Note: create a kafka topic manually

Let's create a topic named "test" with a single partition and only one replica:
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

We can now see that topic if we run the list topic command:
> bin/kafka-topics.sh --list --zookeeper localhost:2181
test
Alternatively, instead of manually creating topics you can also configure your brokers to auto-create topics when a non-existent topic is published to.  

