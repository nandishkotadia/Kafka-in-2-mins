package com.kafka.system;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kafka.system.utility.KafkaConsumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

@Component
public class ConsumerExample {
	
	@Autowired
	private KafkaConsumer kafkaConsumer;
	
	@Value("${kafka.topic}")
	private String KAFKA_TOPIC;
	
	public void startConsumer() throws IOException {
		System.out.println("Start Consumer with topic : "+KAFKA_TOPIC);
		List<KafkaStream<byte[], byte[]>> streamList = kafkaConsumer.getKafkaConsumerStreamList(KAFKA_TOPIC);
		if (streamList != null) {
			for (final KafkaStream<byte[], byte[]> stream : streamList) {
				ConsumerIterator<byte[], byte[]> consumerIterator = stream.iterator();
				System.out.println("Reading started for PushNotification");
				while (consumerIterator.hasNext()) {
					String data = new String(consumerIterator.next().message());
					System.out.println("Consumer consumed data:" + data);
				}
			}
		}

	}

}
